import { beforeEach, describe, expect, it, vi } from 'vitest'
import { createMemoryHistory, createRouter } from 'vue-router'

import {
  estErreurDeChargement,
  installerRechargementSurErreurDeChargement,
} from '@/router/chunkError'

const erreurChunk = () =>
  new TypeError('Failed to fetch dynamically imported module: /assets/AdminTourneeView-ancien.js')

/** Routeur dont la page « tournee » ne se charge pas (fichier absent après une mise à jour du site). */
function routeurAvecPageIntrouvable() {
  const router = createRouter({
    history: createMemoryHistory(),
    routes: [
      { path: '/', component: { template: '<p>accueil</p>' } },
      {
        path: '/tournee',
        component: () => Promise.reject(erreurChunk()),
      },
    ],
  })
  return router
}

describe('erreur de chargement d’une page après une mise à jour du site', () => {
  beforeEach(() => {
    sessionStorage.clear()
  })

  it('reconnaît les messages des navigateurs', () => {
    expect(estErreurDeChargement(erreurChunk())).toBe(true)
    expect(estErreurDeChargement(new TypeError('Importing a module script failed.'))).toBe(true)
    expect(estErreurDeChargement(new Error('autre problème'))).toBe(false)
  })

  it('recharge la page voulue une seule fois', async () => {
    const recharger = vi.fn()
    const router = routeurAvecPageIntrouvable()
    installerRechargementSurErreurDeChargement(router, recharger)
    await router.push('/')

    await router.push('/tournee').catch(() => {})
    expect(recharger).toHaveBeenCalledExactlyOnceWith('/tournee')

    // Même échec une seconde fois (le fichier est vraiment absent) : pas de boucle de rechargement
    await router.push('/tournee').catch(() => {})
    expect(recharger).toHaveBeenCalledOnce()
  })

  it('ignore les autres erreurs de navigation', async () => {
    const recharger = vi.fn()
    const router = createRouter({
      history: createMemoryHistory(),
      routes: [
        { path: '/', component: { template: '<p>accueil</p>' } },
        { path: '/boom', component: () => Promise.reject(new Error('erreur sans rapport')) },
      ],
    })
    installerRechargementSurErreurDeChargement(router, recharger)
    await router.push('/')

    await router.push('/boom').catch(() => {})

    expect(recharger).not.toHaveBeenCalled()
  })
})
