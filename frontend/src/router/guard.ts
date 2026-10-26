import type { RouteLocationNormalized, RouteLocationRaw } from 'vue-router'

import { useAuthStore, type Role } from '@/stores/auth'

import { homeForRole } from './roles'

declare module 'vue-router' {
  interface RouteMeta {
    /** La route exige un utilisateur connecté. */
    requiresAuth?: boolean
    /** Rôles autorisés ; absent ou vide = tout utilisateur connecté. */
    roles?: Role[]
    /**
     * Si l'employé connecté n'a pas le rôle requis, on le renvoie vers la page d'accueil de son rôle
     * (au lieu de « Accès refusé »). Un client reste renvoyé vers « Accès refusé ».
     */
    versAccueilDuRole?: boolean
  }
}

/**
 * Garde de navigation globale :
 * - visiteur non connecté sur une route protégée -> /connexion (en mémorisant la page voulue) ;
 * - utilisateur connecté mais d'un rôle non autorisé -> page « Accès refusé » ;
 * - utilisateur déjà connecté qui ouvre /connexion (ex. bouton « retour ») -> sa page d'accueil.
 */
export function authGuard(to: RouteLocationNormalized): true | RouteLocationRaw {
  const auth = useAuthStore()

  if (to.name === 'connexion' && auth.isAuthenticated && auth.role) {
    return homeForRole(auth.role)
  }

  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    return { name: 'connexion', query: { redirect: to.fullPath } }
  }

  const roles = to.meta.roles
  if (roles && roles.length > 0 && (auth.role === null || !roles.includes(auth.role))) {
    if (to.meta.versAccueilDuRole && auth.role !== null && auth.role !== 'client') {
      return homeForRole(auth.role)
    }
    return { name: 'acces-refuse' }
  }

  return true
}
