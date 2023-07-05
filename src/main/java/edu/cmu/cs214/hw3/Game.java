package edu.cmu.cs214.hw3;

import java.util.ArrayList;
import java.util.List;

import edu.cmu.cs214.hw3.map.Board;
import edu.cmu.cs214.hw3.map.Position;
import edu.cmu.cs214.hw3.map.godCards.BoardDecorator;
import edu.cmu.cs214.hw3.map.godCards.Card;
import edu.cmu.cs214.hw3.map.godCards.ApolloBoardDecorator;
import edu.cmu.cs214.hw3.map.godCards.ArtemisBoardDecorator;
import edu.cmu.cs214.hw3.map.godCards.AthenaBoardDecorator;
import edu.cmu.cs214.hw3.map.godCards.AtlasBoardDecorator;
import edu.cmu.cs214.hw3.map.godCards.DemeterBoardDecorator;
import edu.cmu.cs214.hw3.map.godCards.HephaestusBoardDecorator;
import edu.cmu.cs214.hw3.map.godCards.HermesBoardDecorator;
import edu.cmu.cs214.hw3.map.godCards.MinotaurBoardDecorator;
import edu.cmu.cs214.hw3.map.godCards.PanBoardDecorator;
import edu.cmu.cs214.hw3.map.godCards.NoCardBoardDecorator;

/**
 * Game class that represents the game logic, managing Board and Player instances.
 */
public class Game {
    private List<GameState> history;
    private Board board;
    private BoardDecorator currentBoard;
    private Player player1;
    private Player player2;
    private Card player1Card=Card.None;
    private Card player2Card=Card.None;
    private String currentPeriod;// set,select,move,build
    private Integer currentPlayer = 1;
    private Integer currentWorker = 1;
    public Game(Card player1Card, Card player2Card) {
        // Initialize Board and Player instances
        board = new Board();
        player1 = new Player("player1");
        player2 = new Player("player2");
        this.player1Card= player1Card;
        this.player2Card= player2Card;
        currentPeriod = "set";
        currentPlayer = 1;
        currentWorker = 1;
        history = new ArrayList<GameState>();
        history.add(new GameState(this.copy()));
        currentBoard = getBoardWithCard(board, currentPlayer==1? player1Card:player2Card);
        // this.currentBoard = getBoardWithCard(board, player1Card, player2Card, currentPlayer);
    }
    public Game(Board board,Player player1,Player player2, Card player1Card, Card player2Card,
    String currentPeriod, Integer currentPlayer, Integer currentWorker,List<GameState> newHistory) {
        this.board = new Board(board);
        this.player1 = new Player(player1);
        this.player2 = new Player(player2);
        this.player1Card = player1Card;
        this.player2Card = player2Card;
        this.currentPeriod = currentPeriod;
        this.currentPlayer = currentPlayer;
        this.currentWorker = currentWorker;
        this.history = new ArrayList<GameState>(newHistory);
        this.currentBoard = getBoardWithCard(board, currentPlayer==1? player1Card:player2Card);
        // this.currentBoard = getBoardWithCard(board, player1Card, player2Card, currentPlayer);


    }
    public Game copy() {
        return new Game(new Board(this.board),new Player(this.player1), new Player(this.player2),player1Card,player2Card,
        this.currentPeriod,this.currentPlayer,this.currentWorker,new ArrayList<GameState>(this.history));
    }
    
    public Game undo() {
        if (history.size() <= 1) {
            return this;
        }
        history.remove(history.size() - 1);
        GameState previousState = history.get(history.size() - 1);
        return new Game(new Board(previousState.getBoard()), 
        new Player(previousState.getPlayer1()), new Player(previousState.getPlayer2()), player1Card ,player2Card,
        previousState.getCurrentPeriod(),previousState.getCurrentPlayer(),previousState.getCurrentWorker(),new ArrayList<>(history));
    }
    public Board getBoard() {
        return board;
    }

    public Player getPlayer1() {
        return player1;
    }
    public Player getPlayer2() {
        return player2;
    }
    public GameState getLatestGameStatus(){
        GameState latestState = history.get(history.size() - 1);
        return latestState.copy();
    }
    private List<GameState> createNewHistory(Game copiedGame){
        List<GameState> newHistory = new ArrayList<GameState>(this.history);
        GameState newState = new GameState(copiedGame.copy());
        newHistory.add(newState);
        return newHistory;
    }



    private BoardDecorator getBoardWithCard(Board board, Card card) {
        switch (card) {
            case Demeter:
                return new DemeterBoardDecorator(board,this.history);
            case Minotaur:
                return new MinotaurBoardDecorator(board,this.history,player1,player2);
            case Pan:
                return new PanBoardDecorator(board,this.history);
            case Apollo:
                return new ApolloBoardDecorator(board, this.history,player1,player2);
            case Artemis:
                return new ArtemisBoardDecorator(board, history);
            case Athena:
                return new AthenaBoardDecorator(board, history);
            case Hephaestus:
                return new HephaestusBoardDecorator(board, history);
            case Hermes:
                return new HermesBoardDecorator(board, history);
            case Atlas:
                return new AtlasBoardDecorator(board, history);
            case None:
                return new NoCardBoardDecorator(board,this.history);
            default:
                // return board;
                return new NoCardBoardDecorator(board,this.history);
        }
    }

    

    // Provides a method for players to set workers
    public Game playerSet(int player, int worker, int x, int y) {
        GameState latestState = getLatestGameStatus();
        Game copiedGame = new Game(new Board(latestState.getBoard()),new Player(latestState.getPlayer1()), 
        new Player(latestState.getPlayer2()), player1Card ,player2Card,latestState.getCurrentPeriod(),
        latestState.getCurrentPlayer(),latestState.getCurrentWorker(),new ArrayList<>(history));

        Position setPosition = new Position(x, y);
        boolean success;
        if (player == 1) {
            success = copiedGame.player1.setWorker(copiedGame.currentBoard, setPosition, worker);
        } else {
            success = copiedGame.player2.setWorker(copiedGame.currentBoard, setPosition, worker);
        }
        if(success){
            copiedGame.board=copiedGame.currentBoard.getUpdatedBoard();
            if (copiedGame.currentPlayer == 1 && copiedGame.currentWorker == 1) {
                copiedGame.currentWorker = 2;
                } else if (copiedGame.currentPlayer == 1 && copiedGame.currentWorker == 2) {
                    copiedGame.currentPlayer = 2;
                    copiedGame.currentWorker = 1;
                } else if (copiedGame.currentPlayer == 2 && copiedGame.currentWorker == 1) {
                    copiedGame.currentPlayer = 2;
                    copiedGame.currentWorker = 2;
                } else if (copiedGame.currentPlayer == 2 && copiedGame.currentWorker == 2) {
                    copiedGame.currentPlayer = 1;
                    copiedGame.currentWorker = 1;
                    copiedGame.currentPeriod = "select";
                }
            this.history = createNewHistory(copiedGame);
            return new Game(copiedGame.board,copiedGame.player1,copiedGame.player2,copiedGame.player1Card,copiedGame.player2Card,
            copiedGame.currentPeriod,copiedGame.currentPlayer,copiedGame.currentWorker,history);
        }
        else{
            return this;
        }

    }

    // Provides a method for players to select workers
    public Game playerSelect(int player, int worker) {
        if(worker==-1){
            return this;
        }

        GameState latestState = getLatestGameStatus();
        Game copiedGame = new Game(new Board(latestState.getBoard()), new Player(latestState.getPlayer1()), 
        new Player(latestState.getPlayer2()), player1Card ,player2Card,latestState.getCurrentPeriod(),
        latestState.getCurrentPlayer(),latestState.getCurrentWorker(),new ArrayList<>(history));

        if (player == 1) {
            copiedGame.getPlayer1().setSelectWorker(worker);
        } else if(player==2) {
            copiedGame.getPlayer2().setSelectWorker(worker);
        }
        copiedGame.currentPeriod = "move";
        this.history = createNewHistory(copiedGame);
        return new Game(copiedGame.board,copiedGame.player1,copiedGame.player2,copiedGame.player1Card,copiedGame.player2Card,
        currentPeriod,currentPlayer,currentWorker,history);
  
    }

    // Provides a method for players to move workers
    public Game playerMove(int player, String direction) {
        GameState latestState = getLatestGameStatus();
        Game copiedGame = new Game(new Board(latestState.getBoard()), new Player(latestState.getPlayer1()), 
        new Player(latestState.getPlayer2()), player1Card ,player2Card,latestState.getCurrentPeriod(),
        latestState.getCurrentPlayer(),latestState.getCurrentWorker(),new ArrayList<>(history));

        boolean success;
        if (player == 1) {
            success = copiedGame.player1.move(copiedGame.currentBoard, direction);
        } else {
            success = copiedGame.player2.move(copiedGame.currentBoard, direction);
        }
        if(success){
            copiedGame.board=copiedGame.currentBoard.getUpdatedBoard();

            if(!(copiedGame.currentPlayer==1&&copiedGame.player1Card==Card.Artemis&&((ArtemisBoardDecorator)copiedGame.currentBoard).isFirstMove())&&
            !(copiedGame.currentPlayer==2&&copiedGame.player2Card==Card.Artemis&&((ArtemisBoardDecorator)copiedGame.currentBoard).isFirstMove())&&
            !(copiedGame.currentPlayer==1&&copiedGame.player1Card==Card.Hermes&&((HermesBoardDecorator)copiedGame.currentBoard).canContinueMoving())&&
            !(copiedGame.currentPlayer==2&&copiedGame.player2Card==Card.Hermes&&((HermesBoardDecorator)copiedGame.currentBoard).canContinueMoving())
            ){
                copiedGame.currentPeriod="build";
            }
            this.history = createNewHistory(copiedGame);
            return new Game(copiedGame.board,copiedGame.player1,copiedGame.player2,copiedGame.player1Card,copiedGame.player2Card,
            currentPeriod,currentPlayer,currentWorker,history);
  
        }
        else{
            return this;
        }
    }

    // Provides a method for players to build for workers
    public Game playerBuild(int player, String direction) {
        
        GameState latestState = getLatestGameStatus();
        Game copiedGame = new Game(new Board(latestState.getBoard()), new Player(latestState.getPlayer1()), 
        new Player(latestState.getPlayer2()), player1Card ,player2Card,latestState.getCurrentPeriod(),
        latestState.getCurrentPlayer(),latestState.getCurrentWorker(),new ArrayList<>(history));
        boolean success;
        if (player == 1) {
            success = copiedGame.player1.build(copiedGame.currentBoard, direction);
        } else {
            success = copiedGame.player2.build(copiedGame.currentBoard, direction);
        }
        if(success){
            copiedGame.board=copiedGame.currentBoard.getUpdatedBoard();
            if(
            !(copiedGame.currentPlayer==1&&copiedGame.player1Card==Card.Demeter&&((DemeterBoardDecorator)copiedGame.currentBoard).isFirstBuild())&&
            !(copiedGame.currentPlayer==2&&copiedGame.player2Card==Card.Demeter&&((DemeterBoardDecorator)copiedGame.currentBoard).isFirstBuild())&&
            !(copiedGame.currentPlayer==1&&copiedGame.player1Card==Card.Hephaestus&&((HephaestusBoardDecorator)copiedGame.currentBoard).isFirstBuild())&&
            !(copiedGame.currentPlayer==2&&copiedGame.player2Card==Card.Hephaestus&&((HephaestusBoardDecorator)copiedGame.currentBoard).isFirstBuild())&&
            !(copiedGame.currentPlayer==1&&copiedGame.player1Card==Card.Atlas&&((AtlasBoardDecorator)copiedGame.currentBoard).isFirstBuild())&&
            !(copiedGame.currentPlayer==2&&copiedGame.player2Card==Card.Atlas&&((AtlasBoardDecorator)copiedGame.currentBoard).isFirstBuild())){
                copiedGame.currentPlayer = copiedGame.currentPlayer==1?2:1;
                copiedGame.currentPeriod="select";
            }
            this.history = createNewHistory(copiedGame);
            return new Game(copiedGame.board,copiedGame.player1,copiedGame.player2,copiedGame.player1Card,
            copiedGame.player2Card,currentPeriod,currentPlayer,currentWorker,history);
  
        }
        else{
            return this;
        }
    }

    public Game play(Integer x,Integer y){
        GameState latestState = getLatestGameStatus();
        Game latestGame = new Game(new Board(latestState.getBoard()), new Player(latestState.getPlayer1()), 
        new Player(latestState.getPlayer2()), player1Card ,player2Card,latestState.getCurrentPeriod(),
        latestState.getCurrentPlayer(),latestState.getCurrentWorker(),new ArrayList<>(history));
        
        if(latestGame.currentPeriod=="set"){
            Game newGame = playerSet(latestGame.currentPlayer,latestGame.currentWorker,x,y);
            return newGame;
        }
        else if(latestGame.currentPeriod=="select"){
            Position givenPosition = new Position(x,y);
            Integer workerId = -1;
            if(latestGame.currentPlayer==1){
                if(latestGame.getPlayer1().getWorker1().getWorkerPosition().equals(givenPosition)){
                    workerId = 1;
                }
                else if(latestGame.getPlayer1().getWorker2().getWorkerPosition().equals(givenPosition)){
                    workerId = 2;
                }

            }
            else{
                if(latestGame.getPlayer2().getWorker1().getWorkerPosition().equals(givenPosition)){
                    workerId = 1;
                }
                else if(latestGame.getPlayer2().getWorker2().getWorkerPosition().equals(givenPosition)){
                    workerId = 2;
                }
            }

            Game newGame = playerSelect(latestGame.currentPlayer, workerId);
            return newGame;
        }
        else if(latestGame.currentPeriod=="move"){
            Position givenPosition = new Position(x,y);
            String direction = "";
            if(latestGame.currentPlayer==1){
                Position prePosition = latestGame.getPlayer1().getSelectWorker().getWorkerPosition();
                direction = Position.getDirection(prePosition,givenPosition);
            }
            else{
                Position prePosition = latestGame.getPlayer2().getSelectWorker().getWorkerPosition();
                direction = Position.getDirection(prePosition,givenPosition);
            }
            if(direction=="invalid"){
                return this;
            }
            Game newGame = playerMove(latestGame.currentPlayer, direction);
            return newGame;
        }
        else if(latestGame.currentPeriod=="build"){
            Position givenPosition = new Position(x,y);
            String direction = "";
            if(latestGame.currentPlayer==1){
                Position prePosition = latestGame.getPlayer1().getSelectWorker().getWorkerPosition();
                direction = Position.getDirection(prePosition,givenPosition);
            }
            else{
                Position prePosition = latestGame.getPlayer2().getSelectWorker().getWorkerPosition();
                direction = Position.getDirection(prePosition,givenPosition);
            }
            if(direction=="invalid"){
                return this;
            }
            
            Game newGame = playerBuild(latestGame.currentPlayer, direction);
            return newGame;
        }
        return this;
    }
    public Integer getCurrentPlayer() {
        return currentPlayer;
    }
    public String getCurrentPeriod() {
        return currentPeriod;
    }
    public Boolean isGameOver() {
        return currentBoard.gameOver();
    }
    public String getWinner() {
        return currentBoard.getWinner();
    }
    public Integer getCurrentWorker() {
        return currentWorker;
    }
}
 