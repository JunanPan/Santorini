package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw3.map.Position;
import edu.cmu.cs214.hw3.map.godCards.Card;

import static org.junit.Assert.*;


public class GameTest {
    private Game game;

    @Before
    public void initialize() {
        // This method will run before each test, initializing the game variable
    }
    @Test
    public void testWholeProcessWithNoneCard() {
        game = new Game(Card.None, Card.None); 
        // Set initial worker positions
        game = game.playerSet(1, 1, 0, 0);
        game = game.playerSet(1, 2, 4, 4);
        game = game.playerSet(2, 1, 0, 1);
        game = game.playerSet(2, 2, 3, 4);
        
        // Select worker and build a block at position (1, 0)
        game = game.playerSelect(1, 1);
        game = game.playerMove(1, "down");
        game = game.playerBuild(1, "up");

        game = game.playerSelect(2, 1);
        game = game.playerMove(2, "down");
        game = game.playerBuild(2, "up");

        game = game.playerSelect(1, 1);
        game = game.playerMove(1, "upright");
        game = game.playerBuild(1, "left");

        game = game.playerSelect(2, 1);
        game = game.playerMove(2, "down");
        game = game.playerBuild(2, "upleft");

        game = game.playerSelect(1, 1);
        game = game.playerMove(1, "downleft");
        game = game.playerBuild(1, "upright");

        game = game.playerSelect(2, 1);
        game = game.playerMove(2, "up");
        game = game.playerBuild(2, "upleft");

        game = game.playerSelect(1, 1);
        game = game.playerMove(1, "upright");
        game = game.playerBuild(1, "right");

        game = game.playerSelect(2, 1);
        game = game.playerMove(2, "right");
        game = game.playerBuild(2, "right");

        game = game.playerSelect(1, 1);
        game = game.playerMove(1, "left");
        assertTrue(game.isGameOver());
    }
    @Test
    public void testDemeterCard() {
        game = new Game(Card.Demeter, Card.None); 
        game = game.playerSet(1, 1, 0, 0);
        game = game.playerSelect(1, 1);
        game = game.playerMove(1, "down");
        game = game.playerBuild(1, "down");
        Game newGame = game.playerBuild(1, "down");
        //build at the same position, height should not change
        Position buildPosition = new Position(2, 0);
        assertEquals(newGame.getBoard().getGrid(buildPosition).getBlockHeight(), 1);
        //build at a different position, height should change
        newGame = game.playerBuild(1, "right");
        buildPosition = new Position(1, 1);
        assertEquals(newGame.getBoard().getGrid(buildPosition).getBlockHeight(), 1);
    }

    @Test
    public void testMinotaurCard() {
        game = new Game(Card.Minotaur, Card.None); 
        game = game.playerSet(1, 1, 0, 0);
        game = game.playerSet(1, 2, 4, 4);
        game = game.playerSet(2, 1, 1, 0);
        game = game.playerSet(2, 2, 3, 3);
        assertEquals(game.getBoard().getGrid(new Position(0, 0)).getPlayer(), 1);
        assertEquals(game.getBoard().getGrid(new Position(1, 0)).getPlayer(), 2);
        game = game.playerSelect(1, 1);
        Game newGame = game.playerMove(1, "down");
         //opponent(player2) should be pushed to 2,0
        Position position = new Position(2, 0);
        System.out.println(newGame.getBoard().getGrid(position).getPlayer());
        assertEquals(newGame.getBoard().getGrid(position).getPlayer(), 2);
        //player1 should be 1,0
        position = new Position(1, 0);
        assertEquals(newGame.getBoard().getGrid(position).getPlayer(), 1);
    }
    @Test
    public void testPanCard() {
        game = new Game(Card.None, Card.Pan);
        // Set initial worker positions
        game = game.playerSet(1, 1, 0, 0);
        game = game.playerSet(1, 2, 4, 4);
        game = game.playerSet(2, 1, 0, 1);
        game = game.playerSet(2, 2, 3, 4);
        
        // Select worker and build a block at position (1, 0)
        game = game.playerSelect(1, 1);
        game = game.playerMove(1, "down");
        game = game.playerBuild(1, "up");

        game = game.playerSelect(2, 1);
        game = game.playerMove(2, "down");
        game = game.playerBuild(2, "up");

        game = game.playerSelect(1, 1);
        game = game.playerMove(1, "upright");
        game = game.playerBuild(1, "left");

        assertFalse(game.isGameOver());
        game = game.playerSelect(2, 1);
        game = game.playerMove(2, "down");
        game = game.playerBuild(2, "down");

        game = game.playerSelect(1, 1);
        game = game.playerMove(1, "left");
        game = game.playerBuild(1, "right");

        game = game.playerSelect(2, 1);
        game = game.playerMove(2, "down");
        game = game.playerBuild(2, "up");

        game = game.playerSelect(1, 1);
        game = game.playerMove(1, "down");
        // game = game.playerBuild(1, "up");
        // assertTrue(game.isGameOver());
    }
    @Test
    public void testApolloCard() {
        game = new Game(Card.Apollo, Card.None); 
        game = game.playerSet(1, 1, 0, 0);
        game = game.playerSet(1, 2, 4, 4);
        game = game.playerSet(2, 1, 1, 0);
        game = game.playerSet(2, 2, 3, 3);
        assertEquals(game.getBoard().getGrid(new Position(0, 0)).getPlayer(), 1);
        assertEquals(game.getBoard().getGrid(new Position(1, 0)).getPlayer(), 2);
        game = game.playerSelect(1, 1);
        Game newGame = game.playerMove(1, "down");
         //opponent(player2) should be pushed to 0,0
        Position position = new Position(0, 0);
        System.out.println(newGame.getBoard().getGrid(position).getPlayer());
        assertEquals(newGame.getBoard().getGrid(position).getPlayer(), 2);
        //player1 should be 1,0
        position = new Position(1, 0);
        assertEquals(newGame.getBoard().getGrid(position).getPlayer(), 1);
    }


    @Test
    public void testArtemisCard() {
        game = new Game(Card.Artemis, Card.None); 
        game = game.playerSet(1, 1, 0, 0);
        game = game.playerSet(1, 2, 4, 4);
        game = game.playerSet(2, 1, 0, 1);
        game = game.playerSet(2, 2, 3, 4);
        game = game.playerSelect(1, 1);
        game = game.playerMove(1, "down");
        //can't go back
        game = game.playerMove(1, "up"); 
        Position position = new Position(0, 0);
        assertEquals(game.getBoard().getGrid(position).getPlayer(), 0);
        //can go on 
        game = game.playerMove(1, "down");
        game = game.playerBuild(1, "right"); 
        position = new Position(2, 0);
        assertEquals(game.getBoard().getGrid(position).getPlayer(), 1);   
    }
    @Test
    public void testAthenaCard() {
        game = new Game(Card.None, Card.Athena); 
        game = game.playerSet(1, 1, 0, 0);
        game = game.playerSet(1, 2, 4, 4);
        game = game.playerSet(2, 1, 0, 1);
        game = game.playerSet(2, 2, 3, 4);
        game = game.playerSelect(1, 1);
        game = game.playerMove(1, "down");
        game = game.playerBuild(1, "up");

        game = game.playerSelect(2, 1);
        game = game.playerMove(2, "down");
        game = game.playerBuild(2, "up");

        game = game.playerSelect(1, 1);
        game = game.playerMove(1, "up");
        game = game.playerBuild(1, "down");

        game = game.playerSelect(2, 1);
        game = game.playerMove(2, "up"); 
        Position position = new Position(0, 1);
        //can't move up
        assertEquals(game.getBoard().getGrid(position).getPlayer(), 0);
        }
        @Test
        public void testAtlasCard() {
            game = new Game(Card.Atlas, Card.None); 
            game = game.playerSet(1, 1, 0, 0);
            game = game.playerSelect(1, 1);
            game = game.playerMove(1, "down");
            game = game.playerBuild(1, "down");
            Game newGame = game.playerBuild(1, "down");
            //2,0 should be a dome.
            Position buildPosition = new Position(2, 0);
            assertEquals(newGame.getBoard().getGrid(buildPosition).getBlockHeight(), 4);
        }
        @Test
        public void testHephaestusCard() {
            game = new Game(Card.Hephaestus, Card.None); 
            game = game.playerSet(1, 1, 0, 0);
            game = game.playerSelect(1, 1);
            game = game.playerMove(1, "down");
            game = game.playerBuild(1, "down");
            Game newGame = game.playerBuild(1, "down");
            //2,0 should become height of 2.
            Position buildPosition = new Position(2, 0);
            assertEquals(newGame.getBoard().getGrid(buildPosition).getBlockHeight(), 2);
        }
        @Test
        public void TestUndo() {
            game = new Game(Card.None, Card.None);
            game = game.playerSet(1, 1, 0, 0);
            game = game.playerSet(1, 2, 4, 4);
            game = game.undo();
            assertTrue(game.getCurrentPlayer()==1);
            game = game.playerSet(1, 2, 4, 4);
            assertTrue(game.getCurrentPlayer()==2);
            game = game.playerSet(2, 1, 0, 1);
            game = game.playerSet(2, 2, 3, 4);
            game = game.undo();
            assertEquals(game.getCurrentPeriod(), "set");
            game = game.playerSet(2, 2, 3, 4);
            assertEquals(game.getCurrentPeriod(), "select");
        }
        @Test
        public void TestImmutability(){
            game = new Game(Card.None, Card.None);
            game = game.playerSet(1, 1, 1, 1);
            game = game.playerSet(1, 2, 4, 4);
            GameState gameState1 = game.getLatestGameStatus();
            //current temp state should be changed
            gameState1.getBoard().getGrid(new Position(0, 0)).workerCome("player1");
            assertEquals(gameState1.getBoard().getGrid(new Position(0, 0)).getPlayer(), 1);
            //Get from history again, should not be changed
            GameState gameState2 = game.getLatestGameStatus();
            assertEquals(gameState2.getBoard().getGrid(new Position(0, 0)).getPlayer(), 0);
        }
}
