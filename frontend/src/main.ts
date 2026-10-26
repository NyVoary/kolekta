import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import { useAuthStore } from './stores/auth'

async function start() {
  const app = createApp(App)
  app.use(createPinia())

  // Avant d'afficher quoi que ce soit : si un jeton est enregistré, le serveur le valide (GET /api/auth/me).
  // On évite ainsi de montrer un instant un écran protégé avec un jeton expiré ou révoqué.
  const sessionValide = await useAuthStore().restoreSession()

  app.use(router)
  if (!sessionValide) {
    await router.replace({ name: 'connexion', query: { expiree: '1' } })
  }
  app.mount('#app')
}

void start()
