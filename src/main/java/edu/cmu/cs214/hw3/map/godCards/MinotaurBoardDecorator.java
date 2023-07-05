package edu.cmu.cs214.hw3.map.godCards;

import java.util.List;

import edu.cmu.cs214.hw3.GameState;
import edu.cmu.cs214.hw3.Player;
import edu.cmu.cs214.hw3.map.Board;
import edu.cmu.cs214.hw3.map.Grid;
import edu.cmu.cs214.hw3.map.Position;

public class MinotaurBoardDecorator extends BoardDecorator {
    private Player player1;
    private Player player2;
    public MinotaurBoardDecorator(Board decoratedBoard,List<GameState> history) {
        super(decoratedBoard,history);
    }
    public MinotaurBoardDecorator(Board decoratedBoard,List<GameState> history,Player player1, Player player2) {
        super(decoratedBoard,history);
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public boolean isMoveLegal(Position position, String direction) {
        return super.isMoveLegal(position, direction) || canPushOpponent(position, direction);
    }

    /**
     * Check if the player can push the opponent
     * @param position
     * @param direction
     * @return true if the player can push the opponent, false otherwise
     */
    private boolean canPushOpponent(Position position, String direction) {
        Position nextPosition = Board.getNextPosition(position, direction);
        if (!outOfBoundary(nextPosition)) {
            Grid nextGrid = getGrid(nextPosition);
            if (nextGrid.getPlayer()!=0 && nextGrid.getPlayer() != getGrid(position).getPlayer()) {
                Position opponentNewPosition = Board.getNextPosition(nextPosition, direction);
                return !outOfBoundary(opponentNewPosition) && !getGrid(opponentNewPosition).getIsOccupied();
            }
        }
        return false;
    }

    @Override
    public void moveAndUpdateBoard(String playerName, Position workerPosition, String direction) {
        Player opponentPlayer = playerName.equals("player1")?player2:player1;
        Position nextPosition = Board.getNextPosition(workerPosition, direction);
        if(opponentPlayer.getWorker1().getWorkerPosition().equals(nextPosition)){
            opponentPlayer.getWorker1().setWorkerPosition(getNextPosition(nextPosition, direction));
        }
        else if(opponentPlayer.getWorker2().getWorkerPosition().equals(nextPosition)){
            opponentPlayer.getWorker2().setWorkerPosition(getNextPosition(nextPosition, direction));
        }
        Grid nextGrid = getGrid(nextPosition);
        if(nextGrid.getPlayer()!=0 && nextGrid.getPlayer() != getGrid(workerPosition).getPlayer()){
            super.moveAndUpdateBoard(playerName.equals("player1")?"player2":"player1", nextPosition, direction);
        }
        super.moveAndUpdateBoard(playerName, workerPosition, direction);
    }
}