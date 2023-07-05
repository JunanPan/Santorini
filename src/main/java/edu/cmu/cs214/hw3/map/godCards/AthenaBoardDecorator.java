package edu.cmu.cs214.hw3.map.godCards;

import java.util.List;

import edu.cmu.cs214.hw3.GameState;
import edu.cmu.cs214.hw3.map.Board;
import edu.cmu.cs214.hw3.map.Grid;
import edu.cmu.cs214.hw3.map.Position;

public class AthenaBoardDecorator extends BoardDecorator {

    public AthenaBoardDecorator(Board decoratedBoard, List<GameState> history) {
        super(decoratedBoard, history);
    }

    /**
     * Checks if the worker moved up in the last turn
     * @return true if the worker moved up in the last turn, false otherwise
     */
    public boolean didMoveUpInLastTurn() {
        int moveIndex = -1;
        int buildIndex = -1;
        for (int i = getHistory().size() - 1; i > 0; i--) {
            String currentPeriod = getHistory().get(i).getCurrentPeriod();
            String prevPeriod = getHistory().get(i - 1).getCurrentPeriod();
            if (currentPeriod.equals("build") && prevPeriod.equals("move")) {
                moveIndex = i - 1;
                buildIndex = i;
                break;
            }
        }
        if (moveIndex == -1 || buildIndex == -1) {
            return false;
        }
    
        GameState moveState = getHistory().get(moveIndex);
        GameState buildState = getHistory().get(buildIndex);
        Board moveBoard = moveState.getBoard();
        Board buildBoard = buildState.getBoard();
        Position prevWorkerPosition = null;
        Position lastWorkerPosition = null;
        for (int i = 0; i < moveBoard.getMyGrid().length; i++) {
            for (int j = 0; j < moveBoard.getMyGrid()[i].length; j++) {
                Grid moveGrid = moveBoard.getMyGrid()[i][j];
                Grid buildGrid = buildBoard.getMyGrid()[i][j];
                if (moveGrid.getPlayer() != buildGrid.getPlayer()) {
                    if (moveGrid.getPlayer() != 0) {
                        prevWorkerPosition = new Position(i, j);
                    }
                    if (buildGrid.getPlayer() != 0) {
                        lastWorkerPosition = new Position(i, j);
                    }
                }
            }
        }
        if (prevWorkerPosition != null && lastWorkerPosition != null) {
            Grid prevGrid = moveBoard.getGrid(prevWorkerPosition);
            Grid lastGrid = buildBoard.getGrid(lastWorkerPosition);
            return lastGrid.getBlockHeight() > prevGrid.getBlockHeight();
        }
        return false;
    }
    

    @Override
    public boolean isMoveLegal(Position workerPosition, String direction) {
        if (didMoveUpInLastTurn()) {
            Position nextPosition = Board.getNextPosition(workerPosition, direction);
            Grid currentGrid = getGrid(workerPosition);
            Grid nextGrid = getGrid(nextPosition);
            if (nextGrid.getBlockHeight() > currentGrid.getBlockHeight()) {
                return false;
            }
        }
        return super.isMoveLegal(workerPosition, direction);
    }
}

