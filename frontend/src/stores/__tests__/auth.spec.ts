import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest'
import { createPinia, setActivePinia } from 'pinia'

import { normalizeRole, STORAGE_KEY, useAuthStore, type Session } from '@/stores/auth'

const session: Session = {
  token: 'jeton-de-test',
  role: 'admin',
  user: { id: 7, type: 'EMPLOYE' },
}

describe('store auth', () => {
  beforeEach(() => {
    localStorage.clear()
    setActivePinia(createPinia())
  })

  afterEach(() => {
    vi.restoreAllMocks()
  })

  it('démarre déconnecté', () => {
    const auth = useAuthStore()

    expect(auth.isAuthenticated).toBe(false)
    expect(auth.token).toBeNull()
    expect(auth.role).toBeNull()
    expect(auth.user).toBeNull()
  })

  it('enregistre la session en mémoire et dans localStorage', () => {
    const auth = useAuthStore()
    auth.setSession(session)

    expect(auth.isAuthenticated).toBe(true)
    expect(auth.token).toBe('jeton-de-test')
    expect(auth.role).toBe('admin')
    expect(auth.user).toEqual({ id: 7, type: 'EMPLOYE' })
    expect(JSON.parse(localStorage.getItem(STORAGE_KEY) ?? 'null')).toEqual(session)
  })

  it('retrouve la session sauvegardée au rechargement', () => {
    useAuthStore().setSession(session)

    setActivePinia(createPinia()) // simule un rechargement de la page
    const auth = useAuthStore()

    expect(auth.isAuthenticated).toBe(true)
    expect(auth.role).toBe('admin')
  })

  it('efface la session avec clear()', () => {
    const auth = useAuthStore()
    auth.setSession(session)
    auth.clear()

    expect(auth.isAuthenticated).toBe(false)
    expect(auth.token).toBeNull()
    expect(localStorage.getItem(STORAGE_KEY)).toBeNull()
  })

  it('ignore un contenu de localStorage illisible ou invalide', () => {
    localStorage.setItem(STORAGE_KEY, '{pas du json')
    expect(useAuthStore().isAuthenticated).toBe(false)

    setActivePinia(createPinia())
    localStorage.setItem(STORAGE_KEY, JSON.stringify({ token: 42 }))
    expect(useAuthStore().isAuthenticated).toBe(false)
  })

  it('reste utilisable quand localStorage est indisponible', () => {
    vi.spyOn(Storage.prototype, 'getItem').mockImplementation(() => {
      throw new Error('stockage bloqué')
    })
    vi.spyOn(Storage.prototype, 'setItem').mockImplementation(() => {
      throw new Error('stockage bloqué')
    })

    const auth = useAuthStore()
    expect(() => auth.setSession(session)).not.toThrow()
    expect(auth.isAuthenticated).toBe(true)
  })

  it('normalise les rôles renvoyés par le backend', () => {
    expect(normalizeRole('CLIENT')).toBe('client')
    expect(normalizeRole('admin')).toBe('admin')
  })
})
