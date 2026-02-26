// src/services/cardService.ts
import axios from 'axios';

const API_BASE = import.meta.env.VITE_API_BASE || 'http://localhost:8080/api';
const api = axios.create({ baseURL: API_BASE, timeout: 5000 });

export interface Card {
  id: string;
  name: string;
  description: string;
  image: string;
  effects: {
    damage?: number;
    defense?: number;
    buff?: string;
    debuff?: string;
    [key: string]: any;
  };
  [key: string]: any;
}

export async function getAllCards(): Promise<Card[]> {
  const response = await api.get('/cards');
  return response.data as Card[];
}

export async function getRandomSpecialCard(): Promise<Card> {
  const cards = await getAllCards();
  // Excluir reversa
  const filtered = cards.filter(c => c.image !== 'reversa.png');
  // Elegir aleatoriamente
  return filtered[Math.floor(Math.random() * filtered.length)];
}
