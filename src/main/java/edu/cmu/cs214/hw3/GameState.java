package edu.cmu.cs214.hw3;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import edu.cmu.cs214.hw3.map.Board;
import edu.cmu.cs214.hw3.map.Grid;

public final class GameState {
    private final Game game;

    public GameState(Game game) {
        this.game = game;
    }
    public GameState copy(){
        return new GameState(game.copy());
    }
    public Board getBoard(){
        return game.getBoard();
    }
    public Player getPlayer1(){
        return game.getPlayer1();
    }
    public Player getPlayer2(){
        return game.getPlayer2();
    }
    public Integer getCurrentPlayer(){
        return game.getCurrentPlayer();
    }
    public String getCurrentPeriod(){
        return game.getCurrentPeriod();
    }
    public Integer getCurrentWorker(){
        return game.getCurrentWorker();
    }
    public String toJson() {
        Board board = getBoard();
        JsonObject gameStateObject = new JsonObject();

        // 添加gameOver属性
        gameStateObject.addProperty("gameOver", game.isGameOver());

        gameStateObject.addProperty("winner", game.getWinner());
        // 添加currentPlayer属性
        gameStateObject.addProperty("currentPlayer", getCurrentPlayer());

        // 添加currentPeriod属性
        gameStateObject.addProperty("currentPeriod", getCurrentPeriod());

        // 添加grids属性
        JsonArray gridArray = new JsonArray();
        for (int i = 0; i < board.getMyGrid().length; i++) {
            for (int j = 0; j < board.getMyGrid()[i].length; j++) {
                Grid grid = board.getMyGrid()[i][j];
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("isOccupied", grid.getIsOccupied());
                jsonObject.addProperty("blockHeight", grid.getBlockHeight());
                jsonObject.addProperty("player", grid.getPlayer());
                jsonObject.addProperty("x", i);
                jsonObject.addProperty("y", j);
                gridArray.add(jsonObject);
            }
        }
        gameStateObject.add("grids", gridArray);
        return gameStateObject.toString();
    }
}
