package be.howest.ti.stratego2021.logic;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private String gameId;
    private Board board;
    private List<Move> moveList;
    private boolean has2Players;

    public Game(String  id, Board blueConfig){
        gameId = id;
        board = new Board();
        moveList = new ArrayList<>();
        has2Players = false;

    }

    private void connectRedPlayer(Board redConfig){
        has2Players = true;
    }
}
