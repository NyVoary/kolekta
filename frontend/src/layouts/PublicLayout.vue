<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'

import AppLogo from '@/components/AppLogo.vue'
</script>

<template>
  <div class="flex min-h-screen flex-col">
    <header class="border-b border-foret/20">
      <div class="mx-auto flex max-w-5xl items-center justify-between px-4 py-4">
        <RouterLink to="/" aria-label="Kolekta, retour à l'accueil">
          <AppLogo />
        </RouterLink>
        <nav aria-label="Navigation principale" class="flex gap-6 text-sm font-semibold">
          <RouterLink to="/" class="text-foret hover:underline">Accueil</RouterLink>
          <RouterLink to="/connexion" class="text-foret hover:underline">Connexion</RouterLink>
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
