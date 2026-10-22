import { computed, ref } from 'vue'
import { defineStore } from 'pinia'

export type Role = 'admin' | 'gerant' | 'rh' | 'collecteur' | 'client'

export interface AuthUser {
  id: number
  type: 'EMPLOYE' | 'CLIENT'
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

  return { token, role, user, isAuthenticated, setSession, clear }
})
