import { beforeEach, describe, expect, it } from 'vitest'
import { createPinia, setActivePinia } from 'pinia'

import { ROLE_LABELS, roleLabel } from '@/constants/roles'
import { useAuthStore, type Role } from '@/stores/auth'

describe('libellés des rôles', () => {
  it.each([
    ['admin', 'Administrateur'],
    ['gerant', 'Gérant'],
    ['rh', 'Ressources humaines'],
    ['collecteur', 'Collecteur'],
    ['client', 'Client'],
  ] as const)('le rôle %s s’affiche « %s »', (role, libelle) => {
    expect(roleLabel(role)).toBe(libelle)
  })

  it('ne laisse aucun rôle sans libellé et n’affiche jamais le code brut', () => {
    const roles: Role[] = ['admin', 'gerant', 'rh', 'collecteur', 'client']

    for (const role of roles) {
      expect(ROLE_LABELS[role]).toBeTruthy()
      expect(ROLE_LABELS[role]).not.toBe(role)
    }
  })
})

describe('libellé du rôle dans le store', () => {
  beforeEach(() => {
    localStorage.clear()
    setActivePinia(createPinia())
  })

  it('est null quand personne n’est connecté', () => {
    expect(useAuthStore().roleLibelle).toBeNull()
  })

  it('suit le rôle de la personne connectée', () => {
    const auth = useAuthStore()

    auth.setSession({ token: 't', role: 'rh', user: { id: 3, type: 'EMPLOYE' } })
    expect(auth.roleLibelle).toBe('Ressources humaines')

    auth.setSession({ token: 't', role: 'admin', user: { id: 1, type: 'EMPLOYE' } })
    expect(auth.roleLibelle).toBe('Administrateur')

    auth.clear()
    expect(auth.roleLibelle).toBeNull()
  })
})
