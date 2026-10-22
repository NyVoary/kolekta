<script setup lang="ts">
import { RouterLink } from 'vue-router'
</script>

<template>
  <section>
    <h1 class="text-4xl text-foret">Accès refusé</h1>
    <p class="mt-4 text-gris">Votre rôle ne vous permet pas d'accéder à cette page.</p>
    <RouterLink to="/" class="mt-6 inline-block font-semibold text-foret underline">
      Retour à l'accueil
    </RouterLink>
  </section>
</template>
