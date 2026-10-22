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

  it('affiche la page 404 pour une adresse inconnue', async () => {
    const router = createAppRouter(createMemoryHistory())
    await router.push('/cette-page-nexiste-pas')

    expect(router.currentRoute.value.name).toBe('introuvable')
  })
})
