package be.howest.ti.stratego2021.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Game {

    private String gameId;
    private final PieceCount gameType;
    private Board board;
    private List<Move> moveList;
    private boolean gameStarted;

    public Game(String  id, List<List<String>> blueConfig, String gameType){
        gameId = id;
        this.gameType = PieceCount.valueOf(gameType.toUpperCase(Locale.ROOT));
        board = new Board();
        board.postBlueConfig(blueConfig);
        moveList = new ArrayList<>();
        gameStarted = false;
    }

    public void connectRedPlayer(List<List<String>> redConfig){
        board.postRedConfig(redConfig);
        gameStarted = true;
    }

    public boolean isGameStarted(){
        return gameStarted;
    }

    public Board getBoard(){
        return board;
    }

    public Pawn getPawnAtPos(Coords src){
        return board.getPawn(src);
    }

    public void movePlayer(Coords src, Coords tar){

    }
}
