import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest'
import { flushPromises, mount } from '@vue/test-utils'
import { AxiosError } from 'axios'
import { createPinia, setActivePinia } from 'pinia'
import { createMemoryHistory } from 'vue-router'

import * as api from '@/api/auth'
import { createAppRouter } from '@/router'
import { useAuthStore } from '@/stores/auth'
import LoginView from '@/views/LoginView.vue'

vi.mock('@/api/auth', () => ({ login: vi.fn(), fetchMe: vi.fn(), logout: vi.fn() }))

const login = vi.mocked(api.login)

function erreurHttp(status: number, data: unknown = {}) {
  return new AxiosError('échec', 'ERR_BAD_REQUEST', undefined, undefined, {
    status,
    data,
    statusText: '',
    headers: {},
    config: {} as never,
  })
}

function reponseLogin(role: string) {
  return {
    token: 'jeton-de-test',
    userType: role === 'CLIENT' ? ('CLIENT' as const) : ('EMPLOYE' as const),
    userId: 1,
    role,
    nom: 'Rakoto',
    prenoms: 'Paul',
  }
}

async function monter(query: Record<string, string> = {}) {
  const router = createAppRouter(createMemoryHistory())
  await router.push({ name: 'connexion', query })
  const wrapper = mount(LoginView, { global: { plugins: [router] } })
  return { wrapper, router }
}

async function remplirEtEnvoyer(wrapper: Awaited<ReturnType<typeof monter>>['wrapper']) {
  await wrapper.get('#identifiant').setValue('paul')
  await wrapper.get('#mot-de-passe').setValue('secret')
  await wrapper.get('form').trigger('submit')
  await flushPromises()
}

describe('page de connexion', () => {
  beforeEach(() => {
    localStorage.clear()
    setActivePinia(createPinia())
    login.mockReset()
  })

  afterEach(() => {
    vi.restoreAllMocks()
  })

  it('affiche le formulaire accessible avec ses liens', async () => {
    const { wrapper } = await monter()

    expect(wrapper.get('label[for="identifiant"]').text()).toContain('Identifiant')
    expect(wrapper.get('label[for="mot-de-passe"]').text()).toContain('Mot de passe')
    expect(wrapper.get('button[type="submit"]').text()).toBe('Se connecter')
    expect(wrapper.get('a[href="/mot-de-passe-oublie"]').text()).toContain('Mot de passe oublié ?')
    expect(wrapper.get('a[href="/inscription"]').text()).toContain('Créer un compte')
  })

  it('affiche ou masque le mot de passe', async () => {
    const { wrapper } = await monter()
    const champ = wrapper.get('#mot-de-passe')
    const bouton = wrapper.get('button[aria-controls="mot-de-passe"]')

    expect(champ.attributes('type')).toBe('password')
    expect(bouton.attributes('aria-pressed')).toBe('false')

    await bouton.trigger('click')
    expect(champ.attributes('type')).toBe('text')
    expect(bouton.attributes('aria-pressed')).toBe('true')
    expect(bouton.text()).toContain('Masquer')

    await bouton.trigger('click')
    expect(champ.attributes('type')).toBe('password')
  })

  it('désactive le bouton pendant le chargement', async () => {
    let terminer: (valeur: ReturnType<typeof reponseLogin>) => void = () => {}
    login.mockReturnValue(new Promise((resolve) => (terminer = resolve)))
    const { wrapper } = await monter()

    await wrapper.get('#identifiant').setValue('paul')
    await wrapper.get('#mot-de-passe').setValue('secret')
    await wrapper.get('form').trigger('submit')

    const bouton = wrapper.get('button[type="submit"]')
    expect(bouton.attributes('disabled')).toBeDefined()
    expect(bouton.text()).toContain('Connexion en cours')

    terminer(reponseLogin('admin'))
    await flushPromises()
    expect(useAuthStore().isAuthenticated).toBe(true)
  })

  it('annonce un message générique pour des identifiants invalides', async () => {
    login.mockRejectedValue(erreurHttp(401, { detail: 'Identifiants invalides' }))
    const { wrapper } = await monter()

    await remplirEtEnvoyer(wrapper)

    const alerte = wrapper.get('[role="alert"]')
    expect(alerte.text()).toContain('Identifiant ou mot de passe incorrect')
    expect(useAuthStore().isAuthenticated).toBe(false)
    expect(wrapper.get('button[type="submit"]').attributes('disabled')).toBeUndefined()
  })

  it('affiche le message du serveur pour un compte désactivé', async () => {
    login.mockRejectedValue(
      erreurHttp(403, {
        code: 'COMPTE_DESACTIVE',
        detail: 'Votre compte est désactivé. Contactez Kolekta.',
      }),
    )
    const { wrapper } = await monter()

    await remplirEtEnvoyer(wrapper)

    expect(wrapper.get('[role="alert"]').text()).toContain('Contactez Kolekta')
  })

  it('un 403 qui n’est pas « compte désactivé » (ex. CORS) affiche une erreur générique', async () => {
    login.mockRejectedValue(erreurHttp(403, 'Invalid CORS request'))
    const { wrapper } = await monter()

    await remplirEtEnvoyer(wrapper)

    const texte = wrapper.get('[role="alert"]').text()
    expect(texte).toContain('Une erreur est survenue')
    expect(texte).not.toContain('désactivé')
  })

  it('affiche une erreur réseau quand le serveur est injoignable', async () => {
    login.mockRejectedValue(new AxiosError('Network Error', 'ERR_NETWORK'))
    const { wrapper } = await monter()

    await remplirEtEnvoyer(wrapper)

    expect(wrapper.get('[role="alert"]').text()).toContain('Impossible de joindre le serveur')
  })

  it('affiche « Votre session a expiré » quand on arrive avec ?expiree=1', async () => {
    const { wrapper } = await monter({ expiree: '1' })

    expect(wrapper.get('[role="status"]').text()).toContain('Votre session a expiré')
  })

  it.each([
    ['admin', 'admin-tableau-de-bord'],
    ['gerant', 'admin-tableau-de-bord'],
    ['rh', 'admin-employes'],
    ['collecteur', 'admin-tournee'],
    ['CLIENT', 'accueil'],
  ])('redirige le rôle %s vers %s', async (role, pageAttendue) => {
    login.mockResolvedValue(reponseLogin(role))
    const { wrapper, router } = await monter()

    await remplirEtEnvoyer(wrapper)

    // La page est chargée à la demande (import dynamique) : on attend la fin de la navigation
    await vi.waitFor(() => expect(router.currentRoute.value.name).toBe(pageAttendue))
    expect(login).toHaveBeenCalledWith({ login: 'paul', motDePasse: 'secret' })
  })

  it('revient à la page voulue si elle est autorisée pour le rôle', async () => {
    login.mockResolvedValue(reponseLogin('collecteur'))
    const { wrapper, router } = await monter({ redirect: '/admin/tournee' })

    await remplirEtEnvoyer(wrapper)

    await vi.waitFor(() => expect(router.currentRoute.value.fullPath).toBe('/admin/tournee'))
  })

  it.each([
    ['collecteur', 'admin-tournee'],
    ['rh', 'admin-employes'],
  ])(
    'un %s qui se connecte après avoir demandé /admin arrive sur %s, pas sur le tableau de bord',
    async (role, pageAttendue) => {
      // Cas réel : on ouvre /admin sans être connecté -> /connexion?redirect=/admin -> on se connecte
      login.mockResolvedValue(reponseLogin(role))
      const { wrapper, router } = await monter({ redirect: '/admin' })

      await remplirEtEnvoyer(wrapper)

      await vi.waitFor(() => expect(router.currentRoute.value.name).toBe(pageAttendue))
    },
  )

  it('ignore une page voulue interdite ou externe et va à la page de son rôle', async () => {
    login.mockResolvedValue(reponseLogin('collecteur'))
    const interdite = await monter({ redirect: '/admin/employes' })
    await remplirEtEnvoyer(interdite.wrapper)
    await vi.waitFor(() => expect(interdite.router.currentRoute.value.name).toBe('admin-tournee'))

    setActivePinia(createPinia())
    const externe = await monter({ redirect: '//site-pirate.example' })
    await remplirEtEnvoyer(externe.wrapper)
    await vi.waitFor(() => expect(externe.router.currentRoute.value.name).toBe('admin-tournee'))
  })
})
