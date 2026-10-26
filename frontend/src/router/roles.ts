import type { RouteLocationRaw, Router } from 'vue-router'

import type { Role } from '@/stores/auth'

/** Page d'arrivée après la connexion, selon le rôle (US-001, US-003). */
export function homeForRole(role: Role): RouteLocationRaw {
  switch (role) {
    case 'admin':
    case 'gerant':
      return { name: 'admin-tableau-de-bord' }
    case 'rh':
      return { name: 'admin-employes' }
    case 'collecteur':
      return { name: 'admin-tournee' }
    default:
      return { name: 'accueil' }
  }
}

/**
 * Où aller après la connexion : la page que la personne voulait ouvrir (paramètre « redirect »)
 * si elle est interne au site et autorisée pour son rôle, sinon la page d'accueil de son rôle.
 */
export function destinationAfterLogin(
  role: Role,
  redirect: unknown,
  router: Router,
): RouteLocationRaw {
  // Seulement un chemin interne : « //site.com » ou « https://... » enverraient ailleurs (redirection ouverte)
  if (typeof redirect === 'string' && redirect.startsWith('/') && !redirect.startsWith('//')) {
    const resolved = router.resolve(redirect)
    const roles = resolved.meta.roles
    const autorise = !roles || roles.length === 0 || roles.includes(role)
    if (autorise && resolved.name !== 'connexion' && resolved.name !== 'introuvable') {
      return redirect
    }
  }
  return homeForRole(role)
}
