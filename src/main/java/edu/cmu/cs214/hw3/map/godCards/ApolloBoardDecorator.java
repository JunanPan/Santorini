package edu.cmu.cs214.hw3.map.godCards;

import java.util.List;

import edu.cmu.cs214.hw3.GameState;
import edu.cmu.cs214.hw3.Player;
import edu.cmu.cs214.hw3.map.Board;
import edu.cmu.cs214.hw3.map.Grid;
import edu.cmu.cs214.hw3.map.Position;

public class ApolloBoardDecorator extends BoardDecorator {
    private Player player1;
    private Player player2;

    public ApolloBoardDecorator(Board decoratedBoard, List<GameState> history) {
        super(decoratedBoard, history);
    }

    public ApolloBoardDecorator(Board decoratedBoard, List<GameState> history, Player player1, Player player2) {
        super(decoratedBoard, history);
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public boolean isMoveLegal(Position position, String direction) {
        return super.isMoveLegal(position, direction) || canSwapWithOpponent(position, direction);
    }

    /**
     * Check if the worker can swap with opponent's worker
     * @param position
     * @param direction
     * @return
     */
    private boolean canSwapWithOpponent(Position position, String direction) {
        Position nextPosition = Board.getNextPosition(position, direction);
        if (!outOfBoundary(nextPosition)) {
            Grid nextGrid = getGrid(nextPosition);
            if (nextGrid.getPlayer() != 0 && nextGrid.getPlayer() != getGrid(position).getPlayer()) {
                return true;
            }
        }
        return false;
    }

    public String getOppositeDirection(String direction) {
        switch (direction) {
            case "up":
                return "down";
            case "left":
                return "right";
            case "down":
                return "up";
            case "right":
                return "left";
            case "upleft":
                return "downright";
            case "upright":
                return "downleft";
            case "downleft":
                return "upright";
            case "downright":
                return "upleft";
            default:
                return "";
        }
    }

    @Override
    public void moveAndUpdateBoard(String playerName, Position workerPosition, String direction) {
        Player opponentPlayer = playerName.equals("player1") ? player2 : player1;
        Position nextPosition = Board.getNextPosition(workerPosition, direction);
        Grid nextGrid = getGrid(nextPosition);
    
        if (nextGrid.getPlayer() != 0 && nextGrid.getPlayer() != getGrid(workerPosition).getPlayer()) {
            if (opponentPlayer.getWorker1().getWorkerPosition().equals(nextPosition)) {
                opponentPlayer.getWorker1().setWorkerPosition(workerPosition);
                getGrid(workerPosition).workerCome(playerName.equals("player1") ? "player2" : "player1");
            } else if (opponentPlayer.getWorker2().getWorkerPosition().equals(nextPosition)) {
                opponentPlayer.getWorker2().setWorkerPosition(workerPosition);
                getGrid(workerPosition).workerCome(playerName.equals("player1") ? "player2" : "player1");
            }
            getGrid(nextPosition).workerCome(playerName);
        }
        else{
            super.moveAndUpdateBoard(playerName, workerPosition, direction);
        }
    }
    
}
