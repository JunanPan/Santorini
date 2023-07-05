package edu.cmu.cs214.hw3.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GridTest {
    @Test
    public void workerComeTest(){
        Grid grid = new Grid();

        grid.workerCome("player1");
        assertTrue(grid.getIsOccupied());
        assertEquals(grid.getPlayer(), 1);
        grid.workerCome("player2");
        assertEquals(grid.getPlayer(), 2);
    }

    @Test
    public void workerLeaveTest(){
        Grid grid = new Grid();
        grid.workerCome("player1");
        grid.workerLeave();
        assertFalse(grid.getIsOccupied());
        assertEquals(grid.getPlayer(), 0);
    }

    @Test
    public void buildBlockTest(){
        Grid grid = new Grid();

        assertEquals(grid.getBlockHeight(), 0);
        grid.buildBlock();
        assertEquals(grid.getBlockHeight(), 1);
        grid.buildBlock();
        assertEquals(grid.getBlockHeight(), 2);
        grid.buildBlock();
        grid.buildBlock();
        assertEquals(grid.getBlockHeight(), 4);
        assertTrue(grid.getIsOccupied());
    }
}
