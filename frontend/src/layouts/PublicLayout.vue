<script setup lang="ts">
import { ref } from 'vue'
import { RouterLink, RouterView } from 'vue-router'

import AppLogo from '@/components/AppLogo.vue'
import { useLogout } from '@/composables/useLogout'
import { useAuthStore } from '@/stores/auth'

const auth = useAuthStore()
const logout = useLogout()
const enCours = ref(false)

async function seDeconnecter() {
  enCours.value = true
  try {
    await logout()
  } finally {
    enCours.value = false
  }
}
</script>

<template>
  <div class="flex min-h-screen flex-col">
    <header class="border-b border-foret/20">
      <div class="mx-auto flex max-w-5xl items-center justify-between px-4 py-4">
        <RouterLink to="/" aria-label="Kolekta, retour à l'accueil">
          <AppLogo />
        </RouterLink>
        <nav
          aria-label="Navigation principale"
          class="flex items-center gap-6 text-sm font-semibold"
        >
          <RouterLink to="/" class="text-foret hover:underline">Accueil</RouterLink>
          <template v-if="auth.isAuthenticated">
            <span v-if="auth.fullName" class="font-normal text-gris">{{ auth.fullName }}</span>
            <button
              type="button"
              class="rounded bg-vert px-3 py-1.5 font-semibold text-white hover:bg-foret disabled:cursor-not-allowed disabled:opacity-60"
              :disabled="enCours"
              @click="seDeconnecter"
            >
              Se déconnecter
            </button>
          </template>
          <RouterLink v-else to="/connexion" class="text-foret hover:underline"
            >Connexion</RouterLink
          >
        </nav>
      </div>
    </header>

    <main class="mx-auto w-full max-w-5xl flex-1 px-4 py-10">
      <RouterView />
    </main>

    <footer class="border-t border-foret/20">
      <p class="mx-auto max-w-5xl px-4 py-4 text-sm text-gris">© Kolekta</p>
    </footer>
  </div>
</template>
