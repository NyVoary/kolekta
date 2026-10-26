import axios from 'axios'
import { computed, ref } from 'vue'
import { defineStore } from 'pinia'

import { fetchMe, login as apiLogin, logout as apiLogout } from '@/api/auth'
import { roleLabel } from '@/constants/roles'

export type Role = 'admin' | 'gerant' | 'rh' | 'collecteur' | 'client'

export interface AuthUser {
  id: number
  type: 'EMPLOYE' | 'CLIENT'
  nom?: string
  prenoms?: string
}

export interface Session {
  token: string
  role: Role
  user: AuthUser
}

export const STORAGE_KEY = 'kolekta.auth'

/** Le backend renvoie « admin » pour un employé et « CLIENT » pour un client : on normalise. */
export function normalizeRole(role: string): Role {
  return role.toLowerCase() as Role
}

function isSession(value: unknown): value is Session {
  if (typeof value !== 'object' || value === null) return false
  const s = value as Partial<Session>
  return (
    typeof s.token === 'string' &&
    s.token.length > 0 &&
    typeof s.role === 'string' &&
    typeof s.user === 'object' &&
    s.user !== null &&
    typeof s.user.id === 'number'
  )
}

// Le stockage peut être indisponible (navigation privée, quota, cookies bloqués) :
// on n'en dépend jamais, la session reste alors simplement en mémoire.
function readSession(): Session | null {
  try {
    const raw = localStorage.getItem(STORAGE_KEY)
    if (!raw) return null
    const parsed: unknown = JSON.parse(raw)
    return isSession(parsed) ? parsed : null
  } catch {
    return null
  }
}

function writeSession(session: Session | null): void {
  try {
    if (session) localStorage.setItem(STORAGE_KEY, JSON.stringify(session))
    else localStorage.removeItem(STORAGE_KEY)
  } catch {
    // stockage indisponible : on ignore
  }
}

export const useAuthStore = defineStore('auth', () => {
  const initial = readSession()
  const token = ref<string | null>(initial?.token ?? null)
  const role = ref<Role | null>(initial?.role ?? null)
  const user = ref<AuthUser | null>(initial?.user ?? null)

  const isAuthenticated = computed(() => token.value !== null)

  /** « Prénoms Nom », ou null si l'identité n'est pas connue. */
  const fullName = computed(() => {
    const u = user.value
    if (!u || (!u.nom && !u.prenoms)) return null
    return [u.prenoms, u.nom].filter(Boolean).join(' ')
  })

  /** Libellé lisible du rôle (« Administrateur », « Ressources humaines »…), ou null si non connecté. */
  const roleLibelle = computed(() => (role.value ? roleLabel(role.value) : null))

  function setSession(session: Session): void {
    token.value = session.token
    role.value = session.role
    user.value = session.user
    writeSession(session)
  }

  function clear(): void {
    token.value = null
    role.value = null
    user.value = null
    writeSession(null)
  }

  /** Connexion : enregistre la session et retourne le rôle (pour choisir la page d'arrivée). */
  async function login(identifiant: string, motDePasse: string): Promise<Role> {
    const reponse = await apiLogin({ login: identifiant, motDePasse })
    const session: Session = {
      token: reponse.token,
      role: normalizeRole(reponse.role),
      user: {
        id: reponse.userId,
        type: reponse.userType,
        nom: reponse.nom,
        prenoms: reponse.prenoms,
      },
    }
    setSession(session)
    return session.role
  }

  /**
   * Déconnexion : demande au serveur de révoquer le jeton, puis vide la session.
   * La session locale est vidée même si le serveur est injoignable : la personne doit pouvoir partir.
   */
  async function logout(): Promise<void> {
    try {
      if (token.value) await apiLogout()
    } catch {
      // jeton déjà refusé ou réseau coupé : on se déconnecte quand même côté navigateur
    } finally {
      clear()
    }
  }

  /**
   * Au chargement de la page : vérifie auprès du serveur que le jeton enregistré est encore valable
   * et met à jour l'identité. Retourne false seulement si le serveur refuse le jeton (401) ;
   * une panne réseau garde la session (le collecteur travaille parfois hors-ligne).
   */
  async function restoreSession(): Promise<boolean> {
    if (!token.value) return true
    try {
      const me = await fetchMe()
      setSession({
        token: token.value,
        role: normalizeRole(me.role),
        user: { id: me.userId, type: me.userType, nom: me.nom, prenoms: me.prenoms },
      })
      return true
    } catch (error) {
      if (axios.isAxiosError(error) && error.response?.status === 401) {
        clear()
        return false
      }
      return true
    }
  }

  return {
    token,
    role,
    user,
    isAuthenticated,
    fullName,
    roleLibelle,
    setSession,
    clear,
    login,
    logout,
    restoreSession,
  }
})
