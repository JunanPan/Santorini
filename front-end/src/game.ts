interface GameState {
  grids: Grid[];
  currentPlayer:number;
  selectedCardPlayer1: string | null;
  selectedCardPlayer2: string | null;
  gameMode: string;
  Confirm:Boolean;
}

interface Grid {
  playable:boolean;
  isOccupied: boolean;
  blockHeight:number;
  player:number;
  x: number;
  y: number;
}

export type { GameState, Grid }