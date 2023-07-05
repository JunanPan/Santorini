package edu.cmu.cs214.hw3.map.godCards;

import java.util.List;

import edu.cmu.cs214.hw3.GameState;
import edu.cmu.cs214.hw3.map.Board;
import edu.cmu.cs214.hw3.map.Grid;
import edu.cmu.cs214.hw3.map.Position;

public class AtlasBoardDecorator extends BoardDecorator {
    private static final int DOME_HEIGHT = 4;
    public AtlasBoardDecorator(Board decoratedBoard, List<GameState> history) {
        super(decoratedBoard, history);
    }

    public boolean isFirstBuild() {
        GameState prevState = getHistory().get(getHistory().size() - 2);
        GameState lastState = getHistory().get(getHistory().size() - 1);
    
        return lastState.getCurrentPeriod().equals("build") && prevState.getCurrentPeriod().equals("move");
    }
    
    public boolean isSecondBuild() {
        GameState prevState = getHistory().get(getHistory().size() - 2);
        GameState lastState = getHistory().get(getHistory().size() - 1);
    
        return lastState.getCurrentPeriod().equals("build") && prevState.getCurrentPeriod().equals("build");
    }
    public Position getLastBuildPosition() {
        if (getHistory().size() < 2) {
            return null;
        }

        GameState prevState = getHistory().get(getHistory().size() - 2);
        GameState lastState = getHistory().get(getHistory().size() - 1);
        Board prevBoard = prevState.getBoard();
        Board lastBoard = lastState.getBoard();

        for (int i = 0; i < lastBoard.getMyGrid().length; i++) {
            for (int j = 0; j < lastBoard.getMyGrid()[i].length; j++) {
                Grid lastGrid = lastBoard.getMyGrid()[i][j];
                Grid prevGrid = prevBoard.getMyGrid()[i][j];
                if (lastGrid.getBlockHeight() == prevGrid.getBlockHeight() + 1) {
                    return new Position(i, j);
                }
            }
        }
        return null;
    }

    @Override
    public boolean isBuildLegal(Position workerPosition, String direction) {
        if (isSecondBuild() && direction.equals("mid")) {
            return true;
        }
        Position nextPosition = Board.getNextPosition(workerPosition, direction);
        Grid nextGrid = getGrid(nextPosition);

        if (isSecondBuild()) {
            if (nextPosition.equals(getLastBuildPosition()) && nextGrid.getBlockHeight() < DOME_HEIGHT) {
                return true;
            } else {
                return false;
            }
        }
        return super.isBuildLegal(workerPosition, direction);
    }

    @Override
    public void buildAndUpdateBoard(Position workerPosition, String direction) {
        if(direction.equals("mid")){
            return;
        }
        if (isSecondBuild()) {
            Position targetPosition = Board.getNextPosition(workerPosition, direction);
            
            Grid targetGrid = decoratedBoard.getGrid(targetPosition);
            while(targetGrid.getBlockHeight()<DOME_HEIGHT){
                targetGrid.buildBlock();
            }
        } else {
            super.buildAndUpdateBoard(workerPosition, direction);
        
        }
    }
}


