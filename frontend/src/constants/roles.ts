import type { Role } from '@/stores/auth'

/** Libellé lisible de chaque rôle : l'unique endroit où ces textes sont définis. */
export const ROLE_LABELS: Record<Role, string> = {
  admin: 'Administrateur',
  gerant: 'Gérant',
  rh: 'Ressources humaines',
  collecteur: 'Collecteur',
  client: 'Client',
}

export function roleLabel(role: Role): string {
  return ROLE_LABELS[role]
}
