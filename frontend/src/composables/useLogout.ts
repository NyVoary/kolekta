import { useRouter } from 'vue-router'

import { useAuthStore } from '@/stores/auth'

/** Déconnexion : révoque le jeton, vide la session, renvoie vers /connexion (US-082). */
export function useLogout() {
  const auth = useAuthStore()
  const router = useRouter()

  return async function logout(): Promise<void> {
    await auth.logout()
    await router.push({ name: 'connexion' })
  }
}
