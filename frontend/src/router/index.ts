import { createRouter, createWebHistory, type RouteRecordRaw, type RouterHistory } from 'vue-router'

import { authGuard } from './guard'

export const routes: RouteRecordRaw[] = [
  {
    // Espace client et pages publiques
    path: '/',
    component: () => import('@/layouts/PublicLayout.vue'),
    children: [
      { path: '', name: 'accueil', component: () => import('@/views/HomeView.vue') },
      { path: 'connexion', name: 'connexion', component: () => import('@/views/LoginView.vue') },
      {
        path: 'mot-de-passe-oublie',
        name: 'mot-de-passe-oublie',
        component: () => import('@/views/ForgotPasswordView.vue'),
      },
      {
        path: 'inscription',
        name: 'inscription',
        component: () => import('@/views/RegisterView.vue'),
      },
      {
        path: 'acces-refuse',
        name: 'acces-refuse',
        component: () => import('@/views/ForbiddenView.vue'),
      },
    ],
  },
  {
    // Espace équipe : réservé aux employés connectés
    path: '/admin',
    component: () => import('@/layouts/AdminLayout.vue'),
    meta: { requiresAuth: true, roles: ['admin', 'gerant', 'rh', 'collecteur'] },
    children: [
      {
        path: '',
        name: 'admin-tableau-de-bord',
        component: () => import('@/views/AdminDashboardView.vue'),
      },
      {
        // Page d'arrivée du rôle RH (US-001)
        path: 'employes',
        name: 'admin-employes',
        component: () => import('@/views/AdminEmployesView.vue'),
        meta: { roles: ['admin', 'gerant', 'rh'] },
      },
      {
        // Page d'arrivée du rôle collecteur (US-001)
        path: 'tournee',
        name: 'admin-tournee',
        component: () => import('@/views/AdminTourneeView.vue'),
        meta: { roles: ['collecteur'] },
      },
    ],
  },
  {
    path: '/:pathMatch(.*)*',
    component: () => import('@/layouts/PublicLayout.vue'),
    children: [
      {
        path: '',
        name: 'introuvable',
        component: () => import('@/views/NotFoundView.vue'),
      },
    ],
  },
]

export function createAppRouter(history: RouterHistory = createWebHistory()) {
  const router = createRouter({ history, routes })
  router.beforeEach(authGuard)
  return router
}

export default createAppRouter()
