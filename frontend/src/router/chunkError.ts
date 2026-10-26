import type { Router } from 'vue-router'

const CLE_ESSAI = 'kolekta.rechargement-page'

/**
 * Les pages sont chargées à la demande (fichiers « chunks » aux noms changeants). Après une mise à jour du
 * site, un onglet resté ouvert connaît les anciens noms : le navigateur obtient une 404 et la navigation échoue.
 */
export function estErreurDeChargement(error: unknown): boolean {
  const message = error instanceof Error ? error.message : String(error)
  return /dynamically imported module|importing a module script failed|failed to fetch/i.test(
    message,
  )
}

/**
 * En cas d'erreur de chargement, recharge la page voulue une seule fois (la nouvelle version du site
 * est alors récupérée). Le second échec n'est pas relancé, pour ne jamais boucler.
 */
export function installerRechargementSurErreurDeChargement(
  router: Router,
  recharger: (url: string) => void = (url) => window.location.assign(url),
): void {
  router.onError((error, to) => {
    if (!estErreurDeChargement(error)) return
    try {
      if (sessionStorage.getItem(CLE_ESSAI) === to.fullPath) return
      sessionStorage.setItem(CLE_ESSAI, to.fullPath)
    } catch {
      return // stockage indisponible : impossible de garantir l'absence de boucle
    }
    recharger(to.fullPath)
  })

  router.afterEach(() => {
    try {
      sessionStorage.removeItem(CLE_ESSAI)
    } catch {
      // stockage indisponible : on ignore
    }
  })
}
