package edu.cmu.cs214.hw3.map.godCards;

import java.util.List;

import edu.cmu.cs214.hw3.GameState;
import edu.cmu.cs214.hw3.map.Board;

public class NoCardBoardDecorator extends BoardDecorator {

    public NoCardBoardDecorator(Board decoratedBoard,List<GameState> history) {
        super(decoratedBoard,history);
    }
}

