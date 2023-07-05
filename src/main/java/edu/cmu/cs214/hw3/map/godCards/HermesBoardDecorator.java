package edu.cmu.cs214.hw3.map.godCards;

import java.util.List;

import edu.cmu.cs214.hw3.GameState;
import edu.cmu.cs214.hw3.map.Board;
import edu.cmu.cs214.hw3.map.Grid;
import edu.cmu.cs214.hw3.map.Position;

//not implemented
public class HermesBoardDecorator extends BoardDecorator {

    public HermesBoardDecorator(Board decoratedBoard, List<GameState> history) {
        super(decoratedBoard, history);
    }

    /**
     * Checks if the player can continue moving after a move
     * @return true if the player can continue moving, false otherwise
     */
    public boolean canContinueMoving() {
        if (getHistory().size() < 2) {
            return false;
        }
        GameState prevState = getHistory().get(getHistory().size() - 2);
        GameState lastState = getHistory().get(getHistory().size() - 1);

        Board prevBoard = prevState.getBoard();
        Board lastBoard = lastState.getBoard();

        Position prevWorkerPosition = null;
        Position lastWorkerPosition = null;
        int currentPlayer = lastState.getCurrentPlayer();
        for (int i = 0; i < lastBoard.getMyGrid().length; i++) {
            for (int j = 0; j < lastBoard.getMyGrid()[i].length; j++) {
                Grid lastGrid = lastBoard.getMyGrid()[i][j];
                Grid prevGrid = prevBoard.getMyGrid()[i][j];

                if (lastGrid.getPlayer() != prevGrid.getPlayer()) {
                    if (lastGrid.getPlayer() == currentPlayer) {
                        lastWorkerPosition = new Position(i, j);
                    } else {
                        prevWorkerPosition = new Position(i, j);
                    }
                }
            }
        }
        if (prevWorkerPosition != null && lastWorkerPosition != null) {
            
            int prevHeight = prevBoard.getGrid(prevWorkerPosition).getBlockHeight();
            int lastHeight = lastBoard.getGrid(lastWorkerPosition).getBlockHeight();
            return prevHeight == lastHeight;
        }  

        return false;
    }
}

