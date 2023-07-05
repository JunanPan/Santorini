package edu.cmu.cs214.hw3;


import edu.cmu.cs214.hw3.map.Board;
import edu.cmu.cs214.hw3.map.Position;

public class Worker {
    private Position workerPosition = new Position(0,0);
    private String playerName;

    public Worker(String playerName) {
        this.playerName = playerName;
    }
    public Worker(Worker other) {
        this.workerPosition = new Position(other.workerPosition);
        this.playerName = other.playerName;
    }
    // public String getOwner() {
    //     return player;
    // }

    public Boolean setPosition(Board board,Position setPosition) {
        if(board.isSetLegal(setPosition)){
            workerPosition=setPosition; //change worker position
            board.setAndUpdateBoard(playerName,setPosition);
            return true;
        }
        return false;
    }
    public Position getWorkerPosition() {
        return workerPosition;
    }
    public void setWorkerPosition(Position workerPosition) {
        this.workerPosition = workerPosition;
    }
    /**
     * move current worker to next direction
     * @param board
     * @param direction
     * @return true if move is successful
     */
    public Boolean move(Board board,String direction){
        if(board.isMoveLegal(workerPosition,direction)){
            board.moveAndUpdateBoard(playerName,workerPosition,direction);
            Position nextPosition = Board.getNextPosition(workerPosition,direction);
            this.workerPosition=nextPosition;
            return true;
        }
        else {
            return false;

        }
    }
    /**
     * use current worker to build in next direction
     * @param board
     * @param direction
     * @return true if build is successful
     */
    public Boolean build(Board board,String direction){
        if(board.isBuildLegal(workerPosition,direction)){
            board.buildAndUpdateBoard(workerPosition, direction);
            return true;
        }
        else{
            return false;
        }
    }
}
