package edu.cmu.cs214.hw3;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw3.map.Board;
import edu.cmu.cs214.hw3.map.Grid;
import edu.cmu.cs214.hw3.map.Position;

public class WorkerTest {
    private Board board;

    @Before
    public void initialize() {
        board = new Board();
    }
    @Test
    public void moveTest(){
        Worker worker1 = new Worker("player1");
        Position prePosition = new Position(3, 1);
        Grid preGrid = board.getGrid(prePosition);
        worker1.setPosition(board,prePosition);
        worker1.move(board,"down");
        Position nextPosition = Board.getNextPosition(prePosition,"down");
        Grid nextGrid = board.getGrid(nextPosition);
        assertEquals(preGrid.getPlayer(), 0);
        assertEquals(nextGrid.getPlayer(),1);
    }
    @Test
    public void buildTest(){
        Worker worker1 = new Worker("player1");
        Position prePosition = new Position(0, 1);
        worker1.setPosition(board,prePosition);
        worker1.build(board,"down");
        Position nextPosition = Board.getNextPosition(prePosition,"down");
        Grid nextGrid = board.getGrid(nextPosition);
        assertEquals(nextGrid.getBlockHeight(), 1);
    }
}
