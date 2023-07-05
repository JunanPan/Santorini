package edu.cmu.cs214.hw3.map.godCards;

import java.util.List;

import edu.cmu.cs214.hw3.GameState;
import edu.cmu.cs214.hw3.map.Board;
import edu.cmu.cs214.hw3.map.Grid;
import edu.cmu.cs214.hw3.map.Position;

public abstract class BoardDecorator extends Board {
    // CHECKSTYLE:OFF: VisibilityModifier
    protected Board decoratedBoard;
    // CHECKSTYLE:ON: VisibilityModifier

    private List<GameState> history;
    public BoardDecorator(Board decoratedBoard,List<GameState> history) {
        this.decoratedBoard = decoratedBoard;
        this.history = history;
    }

    public Board getUpdatedBoard() {
        return decoratedBoard;
    }
    public List<GameState> getHistory() {
        return history;
    }

    @Override
    public Boolean gameOver() {
        return decoratedBoard.gameOver();
    }
    @Override
    public String getWinner(){
        return decoratedBoard.getWinner();
    }

    @Override
    public String toStringGrid() {
        return decoratedBoard.toStringGrid();
    }

    @Override
    public void resetGrid() {
        decoratedBoard.resetGrid();
    }



    @Override
    public Grid getGrid(Position position) {
        return decoratedBoard.getGrid(position);
    }

    @Override
    public boolean isSetLegal(Position setPosition) {
        return decoratedBoard.isSetLegal(setPosition);
    }

    @Override
    public void setAndUpdateBoard(String playerName, Position setPosition) {
        decoratedBoard.setAndUpdateBoard(playerName, setPosition);
    }

    @Override
    public void moveAndUpdateBoard(String playerName, Position workerPosition, String direction) {
        decoratedBoard.moveAndUpdateBoard(playerName, workerPosition, direction);
    }



    @Override
    public boolean isMoveLegal(Position position, String direction) {
        return decoratedBoard.isMoveLegal(position, direction);
    }

    @Override
    public boolean isBuildLegal(Position position, String direction) {
        return decoratedBoard.isBuildLegal(position, direction);
    }

    @Override
    public void buildAndUpdateBoard(Position workerPosition, String direction) {
        decoratedBoard.buildAndUpdateBoard(workerPosition, direction);
    }
}
