package edu.cmu.cs214.hw3;
import edu.cmu.cs214.hw3.map.Board;
import edu.cmu.cs214.hw3.map.Position;

public class Player {
    private String playerName;
    private Worker worker1;
    private Worker worker2;
    private Integer selectWorkerId;
    // private List<Worker> workers= new ArrayList<>(); 

    public Player(String playerName) {
        this.playerName = playerName;
        this.worker1 = new Worker(this.playerName);
        this.worker2 = new Worker(this.playerName);
        this.selectWorkerId = 1;
    }

    public Player(Player other) {
        this.playerName = other.playerName;
        this.worker1 = new Worker(other.worker1);
        this.worker2 = new Worker(other.worker2);
        this.selectWorkerId = other.selectWorkerId;
    }
    /**
     * @param board
    * @param position
    * @param workerId
    * @return Boolean if set worker successfully
    *  set worker to position, choose worker based on workerId
     */
    public Boolean setWorker(Board board, Position position,int workerId) {
        Boolean setWorkerResult=false;
        if(workerId==1){
            setWorkerResult = this.worker1.setPosition(board,position);
        }
        else if(workerId==2){
            setWorkerResult = this.worker2.setPosition(board,position);
        }
        return setWorkerResult;
    }
    public Worker getWorker1() {
        return worker1;
    }
    public Worker getWorker2() {
        return worker2;
    }
    public Integer getSelectWorkerId() {
        return this.selectWorkerId;
    }
    public Worker getSelectWorker(){
        if(this.selectWorkerId==1){
            return worker1;
        }
        else{
            return worker2;
        }
    }
    /**
    * @param workerId
    *   select work1 or work2 based on workerId==1 or workerId==2
     */
    public void setSelectWorker(int workerId){
        if(workerId==1)
        {
            this.selectWorkerId = 1;
        }
        else if(workerId==2){
            this.selectWorkerId = 2;
        }
    }
    /**
     * player move current selected worker to next direction
     * @param board
     * @param direction
     * @return return true if move successfully, else false
     */
    public Boolean move(Board board,String direction){
        if(selectWorkerId==1){
            if (worker1.move(board,direction)){
            return true;
        }
        }
        else{
            if (worker2.move(board,direction)){
                return true;
            }
        }
        return false;
    }

    /**
     * player build a block to next direction using current worker
     * @param board
     * @param direction
     * @return return true if build successfully, else false
     */
    public Boolean build(Board board,String direction){
        if(selectWorkerId==1){
            if(worker1.build(board, direction)){
                return true;
            }
        }
        else{
            if(worker2.build(board, direction)){
                return true;
            }
        }


        return false;
    }
}