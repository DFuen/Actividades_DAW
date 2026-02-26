// src/services/partService.ts
export interface PartsResponse {
  head: any[];
  limbs: any[];
  body: any[];
  weapon: any[];
}

export async function fetchParts(): Promise<PartsResponse> {
  const response = await fetch('/api/parts');
  if (!response.ok) throw new Error('Error al obtener partes');
  return await response.json();
}
