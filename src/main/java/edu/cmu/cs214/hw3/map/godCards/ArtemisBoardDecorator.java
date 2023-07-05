package edu.cmu.cs214.hw3.map.godCards;

import java.util.List;

import edu.cmu.cs214.hw3.GameState;
import edu.cmu.cs214.hw3.map.Board;
import edu.cmu.cs214.hw3.map.Grid;
import edu.cmu.cs214.hw3.map.Position;

public class ArtemisBoardDecorator extends BoardDecorator {

    public ArtemisBoardDecorator(Board decoratedBoard, List<GameState> history) {
        super(decoratedBoard, history);
    }

    public boolean isFirstMove() {
        GameState prevState = getHistory().get(getHistory().size() - 2);
        GameState lastState = getHistory().get(getHistory().size() - 1);

        return lastState.getCurrentPeriod().equals("move") && prevState.getCurrentPeriod().equals("select");
    }

    public boolean isSecondMove() {
        GameState prevState = getHistory().get(getHistory().size() - 2);
        GameState lastState = getHistory().get(getHistory().size() - 1);

        return lastState.getCurrentPeriod().equals("move") && prevState.getCurrentPeriod().equals("move");
    }

    /**
     * Get the initial position of the worker
     * @return the initial position of the worker
     */
    public Position getInitialPosition() {
        if (getHistory().size() < 2) {
            return null;
        }
        GameState prevState = getHistory().get(getHistory().size() - 2);
        GameState lastState = getHistory().get(getHistory().size() - 1);
            Board prevBoard = prevState.getBoard();
            Board lastBoard = lastState.getBoard();
            Position prevWorkerPosition = null;
            for (int i = 0; i < lastBoard.getMyGrid().length; i++) {
                for (int j = 0; j < lastBoard.getMyGrid()[i].length; j++) {
                    Grid lastGrid = lastBoard.getMyGrid()[i][j];
                    Grid prevGrid = prevBoard.getMyGrid()[i][j];
                    if (lastGrid.getPlayer() != prevGrid.getPlayer()) {
                        if (prevGrid.getPlayer() != 0) {
                            prevWorkerPosition = new Position(i, j);
                        }
                    }
                }
            }
        return prevWorkerPosition;
    }
    @Override
    public void moveAndUpdateBoard(String playerName, Position workerPosition, String direction) {
        if (isSecondMove() && Board.getNextPosition(workerPosition, direction).equals(getInitialPosition())) {
            return;
        }


        super.moveAndUpdateBoard(playerName, workerPosition, direction);
    }

    @Override
    public boolean isMoveLegal(Position workerPosition, String direction) {
        if(isSecondMove()&&direction.equals("mid")){
            return true;
        }
        if (isSecondMove() && Board.getNextPosition(workerPosition, direction).equals(getInitialPosition())) {
            return false;
        }
        return super.isMoveLegal(workerPosition, direction);
    }
}
