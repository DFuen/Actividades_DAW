import type { Card } from '../types/cards';
import { defineStore } from 'pinia';
import { ref } from 'vue';
import axios from 'axios';
import { useGameStore } from './gameStore';

const API_BASE = import.meta.env.VITE_API_BASE || 'http://localhost:8080/api';

export const useCardStore = defineStore('card', () => {
  // State
  const deck = ref<Card[]>([]);
  const hand = ref<Card[]>([]);
  const played = ref<Card[]>([]);

  // Load cards from backend
  async function loadDeck() {
    const response = await axios.get(`${API_BASE}/cards`);
    deck.value = response.data;
    hand.value = [];
    played.value = [];
  }

  // Draw a card
  function drawCard() {
    if (deck.value.length === 0) return;
    const card = deck.value.shift();
    if (card) hand.value.push(card);
  }

  // Play a card and apply its effect
  function playCard(index: number) {
    const card = hand.value.splice(index, 1)[0];
    if (card) {
      played.value.push(card);
      applyCardEffect(card);
    }
  }

  // Apply card effect to battle state
  function applyCardEffect(card: Card) {
    const gameStore = useGameStore();
    // Furia: +25 ataque y +25 defensa
    if (card.effects.attack) {
      gameStore.robot.totalStats.attack += card.effects.attack;
    }
    // No defense stat in RobotStats, so skip defense update
    // Rotura: elimina una parte
    if (card.effects.removePart) {
      // Elimina una parte aleatoria del robot
      const parts: Array<'head' | 'body' | 'limbs' | 'weapon'> = ['head', 'body', 'limbs', 'weapon'];
      const partToRemove = parts[Math.floor(Math.random() * parts.length)];
      switch (partToRemove) {
        case 'head':
          if (gameStore.robot.head) {
            gameStore.robot.head = null;
            gameStore.updateRobotStats();
          }
          break;
        case 'body':
          if (gameStore.robot.body) {
            gameStore.robot.body = null;
            gameStore.updateRobotStats();
          }
          break;
        case 'limbs':
          if (gameStore.robot.limbs) {
            gameStore.robot.limbs = null;
            gameStore.updateRobotStats();
          }
          break;
        case 'weapon':
          if (gameStore.robot.weapon) {
            gameStore.robot.weapon = null;
            gameStore.updateRobotStats();
          }
          break;
      }
    }
    // Noqueo: +10 ataque, -2 defensa
    // Ya cubierto por attack/defense
  }

  // Discard a card
  function discardCard(index: number) {
    hand.value.splice(index, 1);
  }

  // Reset
  function resetCards() {
    hand.value = [];
    played.value = [];
    deck.value = [];
  }

  return {
    deck,
    hand,
    played,
    loadDeck,
    drawCard,
    playCard,
    discardCard,
    resetCards
  };
});
