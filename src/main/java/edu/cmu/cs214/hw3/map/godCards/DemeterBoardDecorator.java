package edu.cmu.cs214.hw3.map.godCards;

import java.util.List;

import edu.cmu.cs214.hw3.GameState;
import edu.cmu.cs214.hw3.map.Board;
import edu.cmu.cs214.hw3.map.Grid;
import edu.cmu.cs214.hw3.map.Position;

public class DemeterBoardDecorator extends BoardDecorator {

    public DemeterBoardDecorator(Board decoratedBoard,List<GameState> history) {
        super(decoratedBoard,history);
    }



    public boolean isFirstBuild() {
        GameState prevState = getHistory().get(getHistory().size() - 2);
        GameState lastState = getHistory().get(getHistory().size() - 1);
        // System.out.println(prevState.getCurrentPeriod());
        // System.out.println(lastState.getCurrentPeriod());
        //current period is build and last period is move.
        //this help to find the first build, so that we don't change period after the first build.
        if(lastState.getCurrentPeriod().equals("build")&&prevState.getCurrentPeriod().equals("move")){
            return true;
        }
        return false;
    }

    public boolean isSecondBuild() {
        GameState prevState = getHistory().get(getHistory().size() - 2);
        GameState lastState = getHistory().get(getHistory().size() - 1);
        //current period is build and last period is build too.
        if(lastState.getCurrentPeriod().equals("build")&&prevState.getCurrentPeriod().equals("build")){
            return true;
        }
        return false;
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
    public void buildAndUpdateBoard(Position workerPosition, String direction) {
        if(direction.equals("mid")){
            return;
        }
        super.buildAndUpdateBoard(workerPosition, direction);
    }

    @Override
    public boolean isBuildLegal(Position workerPosition, String direction) {
        if(isSecondBuild()&&direction.equals("mid")){
            return true;
        }
        Position nextWorkerPosition = Board.getNextPosition(workerPosition, direction);
        if (nextWorkerPosition.equals(getLastBuildPosition())) {
            return false;
        }
        return super.isBuildLegal(workerPosition, direction);
    }
}