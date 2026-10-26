import { beforeEach, describe, expect, it, vi } from 'vitest'
import { AxiosError, type InternalAxiosRequestConfig } from 'axios'
import { createPinia, setActivePinia } from 'pinia'

import { http } from '@/api/http'
import { useAuthStore } from '@/stores/auth'

const push = vi.hoisted(() => vi.fn())

// Faux routeur : on vérifie seulement vers où l'intercepteur redirige
vi.mock('@/router', () => ({
  default: { currentRoute: { value: { name: 'admin-tableau-de-bord', fullPath: '/admin' } }, push },
}))

function repondre401() {
  http.defaults.adapter = (config: InternalAxiosRequestConfig) =>
    Promise.reject(
      new AxiosError('401', 'ERR_BAD_REQUEST', config, null, {
        status: 401,
        data: {},
        statusText: '',
        headers: {},
        config,
      }),
    )
}

function connecter() {
  useAuthStore().setSession({
    token: 'jeton-de-test',
    role: 'admin',
    user: { id: 7, type: 'EMPLOYE' },
  })
}

describe('client HTTP et intercepteur 401', () => {
  beforeEach(() => {
    localStorage.clear()
    setActivePinia(createPinia())
    push.mockReset()
  })

  it('ajoute le jeton à chaque requête quand on est connecté', async () => {
    connecter()
    let entete: unknown
    http.defaults.adapter = (config: InternalAxiosRequestConfig) => {
      entete = config.headers.Authorization
      return Promise.resolve({ data: {}, status: 200, statusText: 'OK', headers: {}, config })
    }

    await http.get('/auth/me')

    expect(entete).toBe('Bearer jeton-de-test')
  })

  it('sur un 401 : efface la session et renvoie vers /connexion avec « session expirée »', async () => {
    connecter()
    repondre401()

    await expect(http.get('/employes')).rejects.toBeInstanceOf(AxiosError)

    expect(useAuthStore().isAuthenticated).toBe(false)
    expect(push).toHaveBeenCalledWith({
      name: 'connexion',
      query: { redirect: '/admin', expiree: '1' },
    })
  })

  it('sans session au départ : redirige sans le message « session expirée »', async () => {
    repondre401()

    await expect(http.get('/employes')).rejects.toBeInstanceOf(AxiosError)

    expect(push).toHaveBeenCalledWith({ name: 'connexion', query: { redirect: '/admin' } })
  })

  it('le 401 d’une tentative de connexion reste affiché sur la page (pas de redirection)', async () => {
    repondre401()

    await expect(http.post('/auth/login', {})).rejects.toBeInstanceOf(AxiosError)

    expect(push).not.toHaveBeenCalled()
  })

  it('respecte skipAuthRedirect (restauration de session, déconnexion)', async () => {
    connecter()
    repondre401()

    await expect(http.get('/auth/me', { skipAuthRedirect: true })).rejects.toBeInstanceOf(
      AxiosError,
    )

    expect(push).not.toHaveBeenCalled()
    expect(useAuthStore().isAuthenticated).toBe(true)
  })

  it('ne touche pas à la session pour une autre erreur (500)', async () => {
    connecter()
    http.defaults.adapter = (config: InternalAxiosRequestConfig) =>
      Promise.reject(
        new AxiosError('500', 'ERR_BAD_RESPONSE', config, null, {
          status: 500,
          data: {},
          statusText: '',
          headers: {},
          config,
        }),
      )

    await expect(http.get('/employes')).rejects.toBeInstanceOf(AxiosError)

    expect(useAuthStore().isAuthenticated).toBe(true)
    expect(push).not.toHaveBeenCalled()
  })
})
