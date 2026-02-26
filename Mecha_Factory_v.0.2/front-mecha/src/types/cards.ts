// Card types
export interface Card {
  name: string;
  description: string;
  effects: {
    attack?: number;
    defense?: number;
    removePart?: boolean;
  };
  image: string;
}

// Example card state for Arena
export interface ArenaCardState {
  deck: Card[];
  hand: Card[];
  played: Card[];
}
