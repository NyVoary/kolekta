import { http } from './http'

export interface LoginPayload {
  /** Nom d'utilisateur (employé) ou e-mail (client). */
  login: string
  motDePasse: string
}

export interface MeResponse {
  userId: number
  userType: 'EMPLOYE' | 'CLIENT'
  /** « admin », « gerant », « rh », « collecteur » pour un employé ; « CLIENT » pour un client. */
  role: string
  nom: string
  prenoms: string
}

export interface LoginResponse extends MeResponse {
  token: string
}

export async function login(payload: LoginPayload): Promise<LoginResponse> {
  const { data } = await http.post<LoginResponse>('/auth/login', payload)
  return data
}

/** Identité de la personne connectée. Un 401 est géré par l'appelant (restauration de session). */
export async function fetchMe(): Promise<MeResponse> {
  const { data } = await http.get<MeResponse>('/auth/me', { skipAuthRedirect: true })
  return data
}

/** Révoque le jeton côté serveur : l'ancien jeton sera refusé (401) même avant son expiration. */
export async function logout(): Promise<void> {
  await http.post('/auth/logout', null, { skipAuthRedirect: true })
}
