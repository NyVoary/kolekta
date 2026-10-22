import axios from 'axios'

import router from '@/router'
import { useAuthStore } from '@/stores/auth'

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

// 401 : jeton absent, expiré ou invalide -> on efface la session et on renvoie vers /connexion
http.interceptors.response.use(
  (response) => response,
  async (error: unknown) => {
    if (axios.isAxiosError(error) && error.response?.status === 401) {
      useAuthStore().clear()
      const current = router.currentRoute.value
      if (current.name !== 'connexion') {
        await router.push({ name: 'connexion', query: { redirect: current.fullPath } })
      }
    }
    return Promise.reject(error)
  },
)
