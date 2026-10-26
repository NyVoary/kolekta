import { beforeEach, describe, expect, it } from 'vitest'
import { createPinia, setActivePinia } from 'pinia'
import { createMemoryHistory } from 'vue-router'

import { createAppRouter } from '@/router'
import { useAuthStore, type Role } from '@/stores/auth'

function connectAs(role: Role) {
  useAuthStore().setSession({
    token: 'jeton-de-test',
    role,
    user: { id: 1, type: role === 'client' ? 'CLIENT' : 'EMPLOYE' },
  })
}

describe('garde de navigation', () => {
  beforeEach(() => {
    localStorage.clear()
    setActivePinia(createPinia())
  })

  it('renvoie un visiteur non connecté vers /connexion en gardant la page voulue', async () => {
    const router = createAppRouter(createMemoryHistory())
    await router.push('/admin')

    expect(router.currentRoute.value.name).toBe('connexion')
    expect(router.currentRoute.value.query.redirect).toBe('/admin')
  })

  it('laisse passer un utilisateur connecté avec un rôle autorisé', async () => {
    connectAs('gerant')
    const router = createAppRouter(createMemoryHistory())
    await router.push('/admin')

    expect(router.currentRoute.value.name).toBe('admin-tableau-de-bord')
  })

  it('renvoie un rôle non autorisé vers la page « Accès refusé »', async () => {
    connectAs('client')
    const router = createAppRouter(createMemoryHistory())
    await router.push('/admin')

    expect(router.currentRoute.value.name).toBe('acces-refuse')
  })

  it('laisse un visiteur non connecté ouvrir les pages publiques', async () => {
    const router = createAppRouter(createMemoryHistory())

    await router.push('/')
    expect(router.currentRoute.value.name).toBe('accueil')

    await router.push('/connexion')
    expect(router.currentRoute.value.name).toBe('connexion')
  })

  it.each([
    ['admin', 'admin-tableau-de-bord'],
    ['rh', 'admin-employes'],
    ['collecteur', 'admin-tournee'],
    ['client', 'accueil'],
  ] as const)(
    'renvoie un %s déjà connecté qui ouvre /connexion vers %s (bouton « retour »)',
    async (role, page) => {
      connectAs(role)
      const router = createAppRouter(createMemoryHistory())
      await router.push('/connexion')

      expect(router.currentRoute.value.name).toBe(page)
    },
  )

  it.each([
    ['admin', 'admin-tableau-de-bord'],
    ['gerant', 'admin-tableau-de-bord'],
    ['rh', 'admin-employes'],
    ['collecteur', 'admin-tournee'],
  ] as const)(
    'un %s qui tape /admin à la main arrive sur %s (le tableau de bord est réservé au Gérant et à l’Admin)',
    async (role, page) => {
      connectAs(role)
      const router = createAppRouter(createMemoryHistory())
      await router.push('/admin')

      expect(router.currentRoute.value.name).toBe(page)
    },
  )

  it('renvoie un client qui tape /admin vers « Accès refusé »', async () => {
    connectAs('client')
    const router = createAppRouter(createMemoryHistory())
    await router.push('/admin')

    expect(router.currentRoute.value.name).toBe('acces-refuse')
  })

  it('un collecteur ne peut pas ouvrir la page des employés, un RH pas la tournée', async () => {
    connectAs('collecteur')
    const router = createAppRouter(createMemoryHistory())
    await router.push('/admin/employes')
    expect(router.currentRoute.value.name).toBe('acces-refuse')

    setActivePinia(createPinia())
    connectAs('rh')
    const routerRh = createAppRouter(createMemoryHistory())
    await routerRh.push('/admin/tournee')
    expect(routerRh.currentRoute.value.name).toBe('acces-refuse')
  })

  it('réserve la page de la tournée au collecteur et celle des employés au RH', async () => {
    connectAs('rh')
    const router = createAppRouter(createMemoryHistory())

    await router.push('/admin/tournee')
    expect(router.currentRoute.value.name).toBe('acces-refuse')

    await router.push('/admin/employes')
    expect(router.currentRoute.value.name).toBe('admin-employes')
  })

  it('affiche la page 404 pour une adresse inconnue', async () => {
    const router = createAppRouter(createMemoryHistory())
    await router.push('/cette-page-nexiste-pas')

    expect(router.currentRoute.value.name).toBe('introuvable')
  })
})
