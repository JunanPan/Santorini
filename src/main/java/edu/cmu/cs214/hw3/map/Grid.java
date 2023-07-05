package edu.cmu.cs214.hw3.map;

public class Grid {
    private static final int DOME_HEIGHT = 4;
    
    private Boolean isOccupied= false;
    private int blockHeight = 0;
    private int player = 0;


    public Grid(Grid other) {
        this.isOccupied = other.isOccupied;
        this.blockHeight = other.blockHeight;
        this.player = other.player;
    }

    public Grid() {
    }
    public int getBlockHeight() {
        return blockHeight;
    }
    public Boolean getIsOccupied() {
        return this.isOccupied;
    }

    /**
     * increase blockHeight by 1
     * if get blockHeight of 4, set isOccupied to true
     */
    public void buildBlock() {
        this.blockHeight+=1;
        if(blockHeight == DOME_HEIGHT){
            this.isOccupied=true;
        }
    }
    /**
     * @param playerName
     * set isOCcupied to true
     * set worker of current grid to 1 if playerName is "player1", 
     * set worker of current grid to 2 if playerName is "player2", 
     */
    public void workerCome(String playerName){
        this.isOccupied = true;
        if(playerName=="player1"){
            this.player = 1;
        }
        else if(playerName=="player2"){
            this.player = 2;
        }
    }
    /**
     * set worker of current grid to 0, set isOCcupied to false
     */
    public void workerLeave() {
        this.player = 0;
        this.isOccupied = false;
    }
    public int getPlayer() {
        return player;
    }

}
