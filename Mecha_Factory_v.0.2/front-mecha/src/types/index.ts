/**
 * Tipos e interfaces para el proyecto Mecha-Factory Arena
 */

export interface Part {
  id: string;
  name: string;
  category: 'head' | 'body' | 'limbs' | 'weapon';
  rarity: 'common' | 'uncommon' | 'rare' | 'epic' | 'legendary';
  cost: number;
  stats: {
    health: number;
    attack: number;
    speed: number;
  };
  color: string;
  description: string;
}

export interface Robot {
  id: string;
  name: string;
  head: Part | null;
  body: Part | null;
  limbs: Part | null;
  weapon: Part | null;
  totalStats: RobotStats;
  budget: number;
  spentBudget: number;
}

export interface RobotStats {
  health: number;
  attack: number;
  speed: number;
  defense?: number;
  evasion?: number;
  resistance?: number;
  damageReduction?: number;
  lifesteal?: number;
  critChance?: number;
  critMultiplier?: number;
}

export interface BattleState {
  playerHealth: number;
  playerMaxHealth: number;
  aiHealth: number;
  aiMaxHealth: number;
  currentTurn: 'player' | 'ai';
  battleLog: BattleLog[];
  isFinished: boolean;
  winner: 'player' | 'ai' | null;
}

export interface BattleLog {
  id: string;
  timestamp: number;
  action: string;
  damage: number;
  actorType: 'player' | 'ai';
}

export interface GameState {
  robot: Robot;
  battle: BattleState | null;
}

export interface Achievement {
  id: string;
  name: string;
  description: string;
  icon: string;
  unlocked: boolean;
  unlockedAt?: number;
}

export interface Combo {
  count: number;
  multiplier: number;
  lastHitTime: number;
}

export interface PowerUp {
  id: string;
  name: string;
  description: string;
  icon: string;
  duration: number;
  effect: 'damage' | 'defense' | 'speed' | 'heal';
  value: number;
}

export interface PlayerStats {
  totalBattles: number;
  wins: number;
  losses: number;
  totalDamageDealt: number;
  totalDamageTaken: number;
  criticalHits: number;
  perfectVictories: number;
  longestCombo: number;
  highestDamage: number;
  robotsBuilt: number;
  creditsSpent: number;
}

  enemies: GridEnemy[];
  projectiles: Projectile[];
  baseHealth: number;
  baseMaxHealth: number;
  gameOver: boolean;
  wave: number;
  score: number;
}
