package be.howest.ti.stratego2021.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Game {

    private String gameId;
    private final PieceCount gameType;
    private Board board;
    private List<Move> moveList;
    private boolean has2Players;

    public Game(String  id, List<List<String>> blueConfig, String gameType){
        gameId = id;
        this.gameType = PieceCount.valueOf(gameType.toUpperCase(Locale.ROOT));
        board = new Board();
        board.postBlueConfig(blueConfig);
        moveList = new ArrayList<>();
        has2Players = false;

    }

    private void connectRedPlayer(Board redConfig){
        has2Players = true;
    }
}
