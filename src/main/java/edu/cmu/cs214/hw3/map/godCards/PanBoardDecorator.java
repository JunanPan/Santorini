package edu.cmu.cs214.hw3.map.godCards;

import java.util.List;


import edu.cmu.cs214.hw3.GameState;
import edu.cmu.cs214.hw3.map.Board;
import edu.cmu.cs214.hw3.map.Grid;
import edu.cmu.cs214.hw3.map.Position;

public class PanBoardDecorator extends BoardDecorator {
    public PanBoardDecorator(Board decoratedBoard,List<GameState> history) {
        super(decoratedBoard,history);
    }

    /**
     * check if the player move down 2 or more levels
     * @return true if the player move down 2 or more levels
     */
    public boolean moveDown2orMore() {
        if (getHistory().size() < 2) {
            return false;
        }
        GameState prevState = getHistory().get(getHistory().size() - 2);
        GameState lastState = getHistory().get(getHistory().size() - 1);
        //this time is build and last time is move, we can see how much the user moved
        if(lastState.getCurrentPeriod().equals("build")&&prevState.getCurrentPeriod().equals("move")){
            Board prevBoard = prevState.getBoard();
            Board lastBoard = lastState.getBoard();
            Position prevWorkerPosition = null;
            Position lastWorkerPosition = null;
            for (int i = 0; i < lastBoard.getMyGrid().length; i++) {
                for (int j = 0; j < lastBoard.getMyGrid()[i].length; j++) {
                    Grid lastGrid = lastBoard.getMyGrid()[i][j];
                    Grid prevGrid = prevBoard.getMyGrid()[i][j];
                    if (lastGrid.getPlayer() != prevGrid.getPlayer()) {
                        if (lastGrid.getPlayer() != 0) {
                            lastWorkerPosition = new Position(i, j);
                        }
                        if (prevGrid.getPlayer() != 0) {
                            prevWorkerPosition = new Position(i, j);
                        }
                    }
                }
            }
            if (prevWorkerPosition != null && lastWorkerPosition != null) {
                int prevHeight = getGrid(prevWorkerPosition).getBlockHeight();
                int lastHeight = getGrid(lastWorkerPosition).getBlockHeight();
                int heightDifference = prevHeight - lastHeight;
                if (heightDifference >= 2) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getWinner() {
        if(moveDown2orMore()){
            GameState lastState = getHistory().get(getHistory().size() - 1);
            return lastState.getCurrentPlayer()==1?"player1":"player2";
        }
        return super.getWinner();
    }
    @Override
    public Boolean gameOver() {
        if (super.gameOver()) {
            return true;
        }
        if (moveDown2orMore()){
            return true;
        }
        return false;
    }
}