package edu.cmu.cs214.hw3;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw3.map.Board;
import edu.cmu.cs214.hw3.map.Grid;
import edu.cmu.cs214.hw3.map.Position;

public class PlayerTest {
    private Board board;

    @Before
    public void initialize() {
        board = new Board();
    }
    @Test
    public void moveTest(){
        Player player1 = new Player("player1");
        Position prePosition = new Position(1, 0);
        Grid preGrid = board.getGrid(prePosition);
        player1.setWorker(board,prePosition, 1);
        player1.setSelectWorker(1);
        player1.move(board,"down");
        assertEquals(preGrid.getPlayer(),0);
        Position nextPosition = Board.getNextPosition(prePosition,"down");
        Grid nextGrid = board.getGrid(nextPosition);
        assertEquals(nextGrid.getPlayer(),1);
    }

    @Test
    public void buildTest(){
        Player player1 = new Player("player1");
        Position prePosition = new Position(1, 0);
        Grid preGrid = board.getGrid(prePosition);
        player1.setWorker(board,prePosition, 1);
        player1.setSelectWorker(1);
        player1.build(board,"down");
        assertEquals(preGrid.getPlayer(),1);
        Position nextPosition = Board.getNextPosition(prePosition,"down");
        Grid nextGrid = board.getGrid(nextPosition);
        assertEquals(nextGrid.getBlockHeight(),1);
    }
}
