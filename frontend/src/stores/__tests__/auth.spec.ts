import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest'
import { AxiosError } from 'axios'
import { createPinia, setActivePinia } from 'pinia'

import * as api from '@/api/auth'

import { normalizeRole, STORAGE_KEY, useAuthStore, type Session } from '@/stores/auth'

vi.mock('@/api/auth', () => ({ login: vi.fn(), fetchMe: vi.fn(), logout: vi.fn() }))

function erreurHttp(status: number) {
  return new AxiosError('échec', 'ERR_BAD_REQUEST', undefined, undefined, {
    status,
    data: {},
    statusText: '',
    headers: {},
    config: {} as never,
  })
}

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

  describe('connexion, déconnexion et restauration', () => {
    beforeEach(() => {
      vi.mocked(api.login).mockReset()
      vi.mocked(api.logout).mockReset()
      vi.mocked(api.fetchMe).mockReset()
    })

    it('login : enregistre la session et retourne le rôle normalisé', async () => {
      vi.mocked(api.login).mockResolvedValue({
        token: 'jeton-client',
        userType: 'CLIENT',
        userId: 3,
        role: 'CLIENT',
        nom: 'Rabe',
        prenoms: 'Alice',
      })
      const auth = useAuthStore()

      const role = await auth.login('alice@example.com', 'secret')

      expect(role).toBe('client')
      expect(api.login).toHaveBeenCalledWith({ login: 'alice@example.com', motDePasse: 'secret' })
      expect(auth.isAuthenticated).toBe(true)
      expect(auth.user).toEqual({ id: 3, type: 'CLIENT', nom: 'Rabe', prenoms: 'Alice' })
      expect(auth.fullName).toBe('Alice Rabe')
      expect(JSON.parse(localStorage.getItem(STORAGE_KEY) ?? 'null').token).toBe('jeton-client')
    })

    it('login refusé : la session reste vide et l’erreur remonte', async () => {
      vi.mocked(api.login).mockRejectedValue(erreurHttp(401))
      const auth = useAuthStore()

      await expect(auth.login('paul', 'faux')).rejects.toBeInstanceOf(AxiosError)
      expect(auth.isAuthenticated).toBe(false)
    })

    it('logout : appelle le serveur puis vide la session', async () => {
      vi.mocked(api.logout).mockResolvedValue()
      const auth = useAuthStore()
      auth.setSession(session)

      await auth.logout()

      expect(api.logout).toHaveBeenCalledOnce()
      expect(auth.isAuthenticated).toBe(false)
      expect(localStorage.getItem(STORAGE_KEY)).toBeNull()
    })

    it('logout : vide la session même si le serveur est injoignable', async () => {
      vi.mocked(api.logout).mockRejectedValue(new AxiosError('Network Error', 'ERR_NETWORK'))
      const auth = useAuthStore()
      auth.setSession(session)

      await expect(auth.logout()).resolves.toBeUndefined()
      expect(auth.isAuthenticated).toBe(false)
    })

    it('restoreSession : sans jeton, ne contacte pas le serveur', async () => {
      const auth = useAuthStore()

      expect(await auth.restoreSession()).toBe(true)
      expect(api.fetchMe).not.toHaveBeenCalled()
    })

    it('restoreSession : met à jour l’identité et le rôle depuis /me', async () => {
      vi.mocked(api.fetchMe).mockResolvedValue({
        userId: 7,
        userType: 'EMPLOYE',
        role: 'gerant',
        nom: 'Rakoto',
        prenoms: 'Paul',
      })
      const auth = useAuthStore()
      auth.setSession(session)

      expect(await auth.restoreSession()).toBe(true)
      expect(auth.role).toBe('gerant')
      expect(auth.token).toBe('jeton-de-test')
      expect(auth.fullName).toBe('Paul Rakoto')
    })

    it('restoreSession : un 401 efface la session et retourne false', async () => {
      vi.mocked(api.fetchMe).mockRejectedValue(erreurHttp(401))
      const auth = useAuthStore()
      auth.setSession(session)

      expect(await auth.restoreSession()).toBe(false)
      expect(auth.isAuthenticated).toBe(false)
      expect(localStorage.getItem(STORAGE_KEY)).toBeNull()
    })

    it('restoreSession : une panne réseau garde la session (travail hors-ligne)', async () => {
      vi.mocked(api.fetchMe).mockRejectedValue(new AxiosError('Network Error', 'ERR_NETWORK'))
      const auth = useAuthStore()
      auth.setSession(session)

      expect(await auth.restoreSession()).toBe(true)
      expect(auth.isAuthenticated).toBe(true)
    })
  })
})
