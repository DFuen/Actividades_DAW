import axios from 'axios'
import type { Part, Robot } from '../types'
// import partsData from '../data/parts.json'

const API_BASE = import.meta.env.VITE_API_BASE || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE,
  timeout: 5000
})

export const robotService = {
  /**
   * Obtener lista de piezas disponibles
   */
  async getParts(): Promise<Part[]> {
    try {
      const response = await api.get('/parts');
      console.log('[DEBUG] axios response:', response);
      // Si el backend responde con { value: [...] }
      if (response.data && Array.isArray(response.data.value)) {
        return response.data.value as Part[];
      }
      // Si responde con un array directamente
      if (Array.isArray(response.data)) {
        return response.data as Part[];
      }
      return [];
    } catch (error) {
      console.error('Error fetching parts:', error);
      throw error;
    }
  },

  /**
   * Obtener piezas por categoría
   */
  async getPartsByCategory(category: string): Promise<Part[]> {
    const parts = await this.getParts()
    return parts.filter(p => p.category === category)
  },

  /**
   * Guardar robot en localStorage
   */
  saveRobot(robot: Robot): void {
    try {
      localStorage.setItem('mecha_robot', JSON.stringify(robot))
    } catch (error) {
      console.error('Error saving robot:', error)
    }
  },

  /**
   * Cargar robot desde localStorage
   */
  loadRobot(): Robot | null {
    try {
      const data = localStorage.getItem('mecha_robot')
      return data ? JSON.parse(data) : null
    } catch (error) {
      console.error('Error loading robot:', error)
      return null
    }
  },

  /**
   * Eliminar robot guardado
   */
  deleteRobot(): void {
    try {
      localStorage.removeItem('mecha_robot')
    } catch (error) {
      console.error('Error deleting robot:', error)
    }
  },

  /**
   * Resolver batalla en el backend
   */
  async resolveBattle(player: any, enemy: any): Promise<any> {
    try {
      const response = await api.post('/battles/resolve', { player, enemy });
      return response.data;
    } catch (error) {
      console.error('Error resolving battle:', error);
      throw error;
    }
  }
}

export default api
