import axios from 'axios'
import type { Part, Robot } from '../types'
import partsData from '../data/parts.json'

const API_BASE = import.meta.env.VITE_API_BASE || '/api'

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
      return partsData as Part[]
    } catch (error) {
      console.error('Error fetching parts:', error)
      throw error
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
  }
}

export default api
