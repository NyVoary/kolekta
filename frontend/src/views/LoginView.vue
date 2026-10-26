<script setup lang="ts">
import axios from 'axios'
import { computed, ref } from 'vue'
import { RouterLink, useRoute, useRouter } from 'vue-router'

import AppLogo from '@/components/AppLogo.vue'
import { destinationAfterLogin } from '@/router/roles'
import { useAuthStore } from '@/stores/auth'

type Erreur = 'invalides' | 'desactive' | 'reseau' | 'autre'

const auth = useAuthStore()
const route = useRoute()
const router = useRouter()

const identifiant = ref('')
const motDePasse = ref('')
const motDePasseVisible = ref(false)
const chargement = ref(false)
const erreur = ref<Erreur | null>(null)
const messageDesactive = ref('')

// Arrivée ici après l'expiration ou la révocation du jeton (voir l'intercepteur HTTP et main.ts)
const sessionExpiree = computed(() => route.query.expiree === '1')

const messageErreur = computed(() => {
  switch (erreur.value) {
    case 'invalides':
      return 'Identifiant ou mot de passe incorrect.'
    case 'desactive':
      return messageDesactive.value
    case 'reseau':
      return 'Impossible de joindre le serveur. Vérifiez votre connexion internet puis réessayez.'
    case 'autre':
      return 'Une erreur est survenue. Réessayez dans un instant.'
    default:
      return ''
  }
})

async function seConnecter() {
  if (chargement.value) return
  erreur.value = null
  chargement.value = true
  try {
    const role = await auth.login(identifiant.value.trim(), motDePasse.value)
    await router.push(destinationAfterLogin(role, route.query.redirect, router))
  } catch (e) {
    const donnees = axios.isAxiosError(e)
      ? (e.response?.data as { code?: string; detail?: string } | undefined)
      : undefined
    if (!axios.isAxiosError(e)) {
      erreur.value = 'autre'
    } else if (!e.response) {
      erreur.value = 'reseau'
    } else if (e.response.status === 401) {
      erreur.value = 'invalides'
    } else if (e.response.status === 403 && donnees?.code === 'COMPTE_DESACTIVE') {
      // Le serveur fournit le message adapté : employé (« accès refusé ») ou client (« contactez Kolekta »)
      erreur.value = 'desactive'
      messageDesactive.value = donnees.detail ?? 'Votre compte est désactivé. Contactez Kolekta.'
    } else {
      erreur.value = 'autre'
    }
  } finally {
    chargement.value = false
  }
}
</script>

<template>
  <section class="mx-auto w-full max-w-md">
    <div class="rounded-lg border border-foret/20 bg-white p-8 shadow-sm">
      <div class="flex justify-center">
        <AppLogo :size="44" />
      </div>
      <h1 class="mt-6 text-center text-3xl text-foret">Connexion</h1>

      <p
        v-if="sessionExpiree"
        role="status"
        class="mt-6 border-l-4 border-ocre bg-creme p-3 text-sm text-encre"
      >
        Votre session a expiré. Reconnectez-vous pour continuer.
      </p>

      <div
        v-if="messageErreur"
        id="erreur-connexion"
        role="alert"
        class="mt-6 flex gap-2 border-l-4 border-rouge bg-creme p-3 text-sm text-encre"
      >
        <svg
          class="mt-0.5 h-4 w-4 shrink-0 text-rouge"
          viewBox="0 0 20 20"
          fill="currentColor"
          aria-hidden="true"
          focusable="false"
        >
          <path d="M10 1.5 19 17.5H1L10 1.5Zm-1 5.5v5h2V7H9Zm0 6.5v2h2v-2H9Z" fill-rule="evenodd" />
        </svg>
        <span>{{ messageErreur }}</span>
      </div>

      <form class="mt-6 space-y-5" novalidate @submit.prevent="seConnecter">
        <div>
          <label for="identifiant" class="block text-sm font-semibold text-encre">
            Identifiant
          </label>
          <p id="aide-identifiant" class="text-xs text-gris">
            Nom d'utilisateur (équipe) ou adresse e-mail (client)
          </p>
          <input
            id="identifiant"
            v-model="identifiant"
            type="text"
            name="identifiant"
            autocomplete="username"
            autocapitalize="none"
            spellcheck="false"
            required
            aria-describedby="aide-identifiant"
            :aria-invalid="erreur === 'invalides' ? 'true' : undefined"
            class="mt-1 block w-full rounded border border-gris bg-white px-3 py-2 text-encre"
          />
        </div>

        <div>
          <label for="mot-de-passe" class="block text-sm font-semibold text-encre">
            Mot de passe
          </label>
          <div class="relative mt-1">
            <input
              id="mot-de-passe"
              v-model="motDePasse"
              :type="motDePasseVisible ? 'text' : 'password'"
              name="mot-de-passe"
              autocomplete="current-password"
              required
              :aria-invalid="erreur === 'invalides' ? 'true' : undefined"
              class="block w-full rounded border border-gris bg-white py-2 pl-3 pr-24 text-encre"
            />
            <button
              type="button"
              class="absolute inset-y-0 right-0 px-3 text-sm font-semibold text-foret underline"
              :aria-pressed="motDePasseVisible"
              aria-controls="mot-de-passe"
              @click="motDePasseVisible = !motDePasseVisible"
            >
              {{ motDePasseVisible ? 'Masquer' : 'Afficher' }}
              <span class="sr-only"> le mot de passe</span>
            </button>
          </div>
        </div>

        <button
          type="submit"
          class="w-full rounded bg-vert px-4 py-2.5 font-semibold text-white hover:bg-foret disabled:cursor-not-allowed disabled:opacity-60"
          :disabled="chargement"
          :aria-busy="chargement"
        >
          {{ chargement ? 'Connexion en cours…' : 'Se connecter' }}
        </button>
      </form>

      <div class="mt-6 flex flex-col items-center gap-2 text-sm">
        <RouterLink to="/mot-de-passe-oublie" class="font-semibold text-foret underline">
          Mot de passe oublié ?
        </RouterLink>
        <RouterLink to="/inscription" class="font-semibold text-foret underline">
          Créer un compte
        </RouterLink>
      </div>
    </div>
  </section>
</template>
