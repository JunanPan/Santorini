package edu.cmu.cs214.hw3.map;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {
    private Board board;

    @Before
    public void initialize() {
        board = new Board();
    }

    @Test
    public void getNextPosition_test() {
        Position prePosition = new Position(1, 1);
        Position nexPosition;
        nexPosition = Board.getNextPosition(prePosition, "up");
        assertEquals(nexPosition.getX(), 0);
        assertEquals(nexPosition.getY(), 1);
        nexPosition = Board.getNextPosition(prePosition, "down");
        assertEquals(nexPosition.getX(), 2);
        assertEquals(nexPosition.getY(), 1);
        nexPosition = Board.getNextPosition(prePosition, "left");
        assertEquals(nexPosition.getX(), 1);
        assertEquals(nexPosition.getY(), 0);
        nexPosition = Board.getNextPosition(prePosition, "right");
        assertEquals(nexPosition.getX(), 1);
        assertEquals(nexPosition.getY(), 2);
        nexPosition = Board.getNextPosition(prePosition, "upleft");
        assertEquals(nexPosition.getX(), 0);
        assertEquals(nexPosition.getY(), 0);
        nexPosition = Board.getNextPosition(prePosition, "downleft");
        assertEquals(nexPosition.getX(), 2);
        assertEquals(nexPosition.getY(), 0);
        nexPosition = Board.getNextPosition(prePosition, "upright");
        assertEquals(nexPosition.getX(), 0);
        assertEquals(nexPosition.getY(), 2);
        nexPosition = Board.getNextPosition(prePosition, "downright");
        assertEquals(nexPosition.getX(), 2);
        assertEquals(nexPosition.getY(), 2);
    }

    @Test
    public void isSetLegal_test() {
        Position setPosition = new Position(2, 2);
        Grid grid = board.getGrid(setPosition);
        assertTrue(board.isSetLegal(setPosition));
        grid.workerCome("player1");
        assertFalse(board.isSetLegal(setPosition));
        grid.workerLeave();
        assertTrue(board.isSetLegal(setPosition));
    }
    @Test
    public void setAndUpdateBoard_test() {
        Position setPosition = new Position(3, 3);
        board.setAndUpdateBoard("player1", setPosition);
        Grid grid = board.getGrid(setPosition);
        assertEquals(grid.getPlayer(), 1);
    }

    @Test
    public void isMoveLegal_test() {
        Position prePosition = new Position(0, 0);
        Boolean res;
        res = board.isMoveLegal(prePosition, "up");
        assertFalse(res);
        Position nextPosition = Board.getNextPosition(prePosition, "down");
        Grid nextGrid = board.getGrid(nextPosition);
        nextGrid.workerCome("player2");
        assertFalse(board.isMoveLegal(prePosition, "down"));

        nextGrid.workerLeave();
        assertTrue(board.isMoveLegal(prePosition, "down"));

        nextGrid.buildBlock();
        assertTrue(board.isMoveLegal(prePosition, "down"));
        nextGrid.buildBlock();
        assertFalse(board.isMoveLegal(prePosition, "down"));
    }

    @Test
    public void moveAndUpdateBoard_test() {
        Position prePosition = new Position(0, 0);
        Grid preGrid = board.getGrid(prePosition);
        preGrid.workerCome("player1");
        assertEquals(preGrid.getPlayer(), 1);
        board.moveAndUpdateBoard("player1", prePosition, "down");
        Position nextPosition = Board.getNextPosition(prePosition, "down");
        Grid nextGrid = board.getGrid(nextPosition);
        assertEquals(preGrid.getPlayer(), 0);
        assertEquals(nextGrid.getPlayer(), 1);
    }
}