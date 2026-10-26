<script setup lang="ts">
import { RouterLink } from 'vue-router'
</script>

<template>
  <section>
    <h1 class="text-4xl text-foret">Mot de passe oublié</h1>
    <p class="mt-4 text-gris">Page de réinitialisation du mot de passe provisoire (US-004).</p>
    <RouterLink to="/connexion" class="mt-6 inline-block font-semibold text-foret underline">
      Retour à la connexion
    </RouterLink>
  </section>
</template>
