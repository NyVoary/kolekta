import axios from 'axios'

import { useAuthStore } from '@/stores/auth'

declare module 'axios' {
  interface AxiosRequestConfig {
    /** Le 401 de cette requête est géré par l'appelant : pas de déconnexion ni de redirection automatiques. */
    skipAuthRedirect?: boolean
  }
}

/** Client HTTP de l'application. /api est servi par le backend (proxy Vite en dev, nginx en Docker). */
export const http = axios.create({ baseURL: '/api' })

// Ajoute le jeton à chaque requête quand l'utilisateur est connecté
http.interceptors.request.use((config) => {
  const auth = useAuthStore()
  if (auth.token) {
    config.headers.Authorization = `Bearer ${auth.token}`
  }
  return config
})

/** Le 401 d'une tentative de connexion veut dire « identifiants invalides », pas « session expirée ». */
function isLoginRequest(url: string | undefined): boolean {
  return url === '/auth/login'
}

// 401 : jeton absent, expiré, révoqué ou invalide -> on efface la session et on renvoie vers /connexion
http.interceptors.response.use(
  (response) => response,
  async (error: unknown) => {
    if (
      axios.isAxiosError(error) &&
      error.response?.status === 401 &&
      !error.config?.skipAuthRedirect &&
      !isLoginRequest(error.config?.url)
    ) {
      const auth = useAuthStore()
      const avaitUneSession = auth.isAuthenticated
      auth.clear()

      // Import différé : le routeur dépend du store, qui dépend de ce fichier (évite une boucle d'imports)
      const { default: router } = await import('@/router')
      const current = router.currentRoute.value
      if (current.name !== 'connexion') {
        await router.push({
          name: 'connexion',
          query: { redirect: current.fullPath, ...(avaitUneSession ? { expiree: '1' } : {}) },
        })
      }
    }
    return Promise.reject(error)
  },
)
