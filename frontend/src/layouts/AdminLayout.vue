<script setup lang="ts">
import { RouterLink, RouterView, useRouter } from 'vue-router'

import AppLogo from '@/components/AppLogo.vue'
import { useAuthStore } from '@/stores/auth'

const auth = useAuthStore()
const router = useRouter()

async function logout() {
  auth.clear()
  await router.push({ name: 'connexion' })
}
</script>

<template>
  <div class="flex min-h-screen flex-col">
    <header class="border-b border-foret/20">
      <div class="mx-auto flex max-w-6xl items-center justify-between px-4 py-4">
        <div class="flex items-center gap-4">
          <RouterLink to="/admin" aria-label="Kolekta, tableau de bord">
            <AppLogo />
          </RouterLink>
          <span class="rounded bg-ocre px-2 py-0.5 text-xs font-semibold text-encre">
            Espace équipe
          </span>
        </div>
        <div class="flex items-center gap-4 text-sm">
          <span v-if="auth.role" class="text-gris">Rôle : {{ auth.role }}</span>
          <button
            type="button"
            class="rounded bg-vert px-3 py-1.5 font-semibold text-white hover:bg-foret"
            @click="logout"
          >
            Se déconnecter
          </button>
        </div>
      </div>
    </header>

    <main class="mx-auto w-full max-w-6xl flex-1 px-4 py-10">
      <RouterView />
    </main>
  </div>
</template>
