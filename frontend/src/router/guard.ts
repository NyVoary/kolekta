import type { RouteLocationNormalized, RouteLocationRaw } from 'vue-router'

import { useAuthStore, type Role } from '@/stores/auth'

declare module 'vue-router' {
  interface RouteMeta {
    /** La route exige un utilisateur connecté. */
    requiresAuth?: boolean
    /** Rôles autorisés ; absent ou vide = tout utilisateur connecté. */
    roles?: Role[]
  }
}

/**
 * Garde de navigation globale :
 * - visiteur non connecté sur une route protégée -> /connexion (en mémorisant la page voulue) ;
 * - utilisateur connecté mais d'un rôle non autorisé -> page « Accès refusé ».
 */
export function authGuard(to: RouteLocationNormalized): true | RouteLocationRaw {
  const auth = useAuthStore()

  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    return { name: 'connexion', query: { redirect: to.fullPath } }
  }

  const roles = to.meta.roles
  if (roles && roles.length > 0 && (auth.role === null || !roles.includes(auth.role))) {
    return { name: 'acces-refuse' }
  }

  return true
}
