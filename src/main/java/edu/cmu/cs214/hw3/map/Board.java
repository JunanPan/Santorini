package edu.cmu.cs214.hw3.map;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Board {
    private static final int COLS_NUM = 5;
    private static final int DOME_HEIGHT = 4;
    private static final int WIN_HEIGHT = 3;
    private Grid[][] myGrid; 
    public Board() { 
        myGrid = new Grid[COLS_NUM][COLS_NUM]; 
        for (int i = 0; i < myGrid.length; i++) { 
            for (int j = 0; j < myGrid[i].length; j++) { myGrid[i][j] = new Grid(); 
            } } 
    }
    public Board(Board other) {
        myGrid = new Grid[COLS_NUM][COLS_NUM];
        for (int i = 0; i < myGrid.length; i++) {
            for (int j = 0; j < myGrid[i].length; j++) {
                myGrid[i][j] = new Grid(other.myGrid[i][j]);
            }
        }
    }
    public Grid[][] getMyGrid() {
        return myGrid;
    }
    
    /**
     * if game is over, return True.
     * @return if game is over, return True.
     */
    public Boolean gameOver(){
        for (int i = 0; i < myGrid.length; i++) {
            for (int j = 0; j < myGrid[i].length; j++) {
            if(myGrid[i][j].getBlockHeight()==WIN_HEIGHT&&myGrid[i][j].getPlayer()==1){
                return true;
            }
            else if(myGrid[i][j].getBlockHeight()==WIN_HEIGHT&&myGrid[i][j].getPlayer()==2){
                return true;
            }
            }}
        return false;
    }

    /**
     * if game is over, return winner.
     * @return if game is over, return winner.
     */
    public String getWinner(){
        for (int i = 0; i < myGrid.length; i++) {
            for (int j = 0; j < myGrid[i].length; j++) {
            if(myGrid[i][j].getBlockHeight()==WIN_HEIGHT&&myGrid[i][j].getPlayer()==1){
                return "player1";
            }
            else if(myGrid[i][j].getBlockHeight()==WIN_HEIGHT&&myGrid[i][j].getPlayer()==2){
                return "player2";
            }
            }}
            return null;
    }

    /**
     * print the board
     * @return the board json string
     */
    public String toStringGrid() {
        JsonArray jsonArray = new JsonArray();
        JsonObject gameObject = new JsonObject();
        gameObject.addProperty("gameOver", gameOver());
        jsonArray.add(gameObject);
        JsonArray gridArray = new JsonArray();
        for (int i = 0; i < myGrid.length; i++) {
            for (int j = 0; j < myGrid[i].length; j++) {
                Grid grid = myGrid[i][j];
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("isOccupied", grid.getIsOccupied());
                jsonObject.addProperty("blockHeight", grid.getBlockHeight());
                jsonObject.addProperty("player", grid.getPlayer());
                jsonObject.addProperty("x", i);
                jsonObject.addProperty("y", j);
                gridArray.add(jsonObject);
            }
        }
        JsonObject gridObject = new JsonObject();
        gridObject.add("grids", gridArray);
        jsonArray.add(gridObject);
        String json = jsonArray.toString();
        return json;
    }

    /**
     * reset the board to initial state
     */
    public void resetGrid(){
        for (int i = 0; i < myGrid.length; i++) {
            for (int j = 0; j < myGrid[i].length; j++) {
                myGrid[i][j] = new Grid();
            }
        }
    }
    /**
     * 
     * @param position
     * @param direction
     * @return Just return the next position without boundary check
     */
    public static Position getNextPosition(Position position,String direction){
        int x = position.getX();
        int y = position.getY();
        switch(direction){
            case "up":
                x-=1;
                break;
            case "left":
                y-=1;
                break;
            case "down":
                x+=1;
                break;
            case "right":
                y+=1;
                break;
            case "upleft":
                x-=1;
                y-=1;
                break;
            case "upright":
                x-=1;
                y+=1;
                break;
            case "downleft":
                x+=1;
                y-=1;
                break;
            case "downright":
                x+=1;
                y+=1;
                break;
            default:
                break;
        }
        Position nexPosition = new Position(x, y);
        return nexPosition;
    }

    /**
     * 
     * @param position
     * @return the grid of given position
     */
    public Grid getGrid(Position position){
        int x = position.getX();
        int y = position.getY();
        Grid grid = myGrid[x][y];
        return grid;
    }

    /**
     * 
     * @param setPosition
     * @return true if setPosition is available to set
     */
    public boolean isSetLegal(Position setPosition){
        int x = setPosition.getX();
        int y = setPosition.getY();
        if (!((x>=0&&x<=DOME_HEIGHT)&&(y>=0&&y<=DOME_HEIGHT))){
            return false;
        }
        Grid grid = getGrid(setPosition);
        if(grid.getIsOccupied()){
            return false;
        }
        return true;
    }
    /**
     * will set worker to w1/w2(depends on playerName is player1/player2) in the Grid of given setPosition
     * @param playerName 
     * @param setPosition 
     */
    public void setAndUpdateBoard(String playerName,Position setPosition){
        Grid grid = getGrid(setPosition);
        grid.workerCome(playerName);
    }

    /**
     * move w1/w2(depends on playerName) from workerPosition to next direction
     * @param playerName
     * @param workerPosition
     * @param direction
     */
    public void moveAndUpdateBoard(String playerName,Position workerPosition, String direction){
        Grid preGrid = getGrid(workerPosition);
        preGrid.workerLeave();
        Position nextPosition = getNextPosition(workerPosition, direction);
        Grid nextGrid = getGrid(nextPosition);
        nextGrid.workerCome(playerName);

    }
    /**
     * check if given position is out of boundary
     * @param position
     * @return true if out of boundary
     */
    public static boolean outOfBoundary(Position position){
        if (position.getX()<0||position.getX()>DOME_HEIGHT||position.getY()<0||position.getY()>DOME_HEIGHT){
            return true;
        }
        return false;
    }

    /**
     * return false if next position is not legal to move.
     * @param position current position
     * @param direction move direction
     * @return false if next position is not legal to move.
     */
    public boolean isMoveLegal(Position position,String direction){

        Position nexPosition= getNextPosition(position,direction);
        if(outOfBoundary(nexPosition)){
            return false;
        }
        Grid preGrid = getGrid(position);
        Grid nextGrid = getGrid(nexPosition);
        if(nextGrid.getBlockHeight()-preGrid.getBlockHeight()>1||nextGrid.getIsOccupied()){
            return false;
        }
        return true;

    }


    /**
     * determine if next position is legal to build
     * @param position
     * @param direction
     * @return false if next position is not legal to build.
     */
    public boolean isBuildLegal(Position position,String direction){
        Position nexPosition= getNextPosition(position,direction);
        if(outOfBoundary(nexPosition)){
            return false;
        }
        Grid grid = getGrid(nexPosition);
        if(grid.getBlockHeight()>=DOME_HEIGHT||grid.getIsOccupied()){
            return false;
        }
        return true;
    }
    /**
     * build a block in the next position, the blockHeight should increase by 1
     * @param workerPosition
     * @param direction
     */
    public void buildAndUpdateBoard(Position workerPosition, String direction){
        Position nextPosition = getNextPosition(workerPosition, direction);
        Grid nextGrid = getGrid(nextPosition);
        nextGrid.buildBlock();
    }

}
