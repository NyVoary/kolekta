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
        <div class="flex items-center gap-4">
          <RouterLink to="/admin" aria-label="Kolekta, tableau de bord">
            <AppLogo />
          </RouterLink>
          <span class="rounded bg-ocre px-2 py-0.5 text-xs font-semibold text-encre">
            Espace équipe
          </span>
        </div>
        <div class="flex items-center gap-4 text-sm">
          <span v-if="auth.fullName" class="text-encre">{{ auth.fullName }}</span>
          <span v-if="auth.roleLibelle" class="text-gris">{{ auth.roleLibelle }}</span>
          <button
            type="button"
            class="rounded bg-vert px-3 py-1.5 font-semibold text-white hover:bg-foret disabled:cursor-not-allowed disabled:opacity-60"
            :disabled="enCours"
            @click="seDeconnecter"
          >
            Se déconnecter
          </button>
        </div>
      </div>
    </header>

    <main class="mx-auto w-full max-w-5xl flex-1 px-4 py-10">
      <RouterView />
    </main>
  </div>
</template>
