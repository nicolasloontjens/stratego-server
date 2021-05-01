package be.howest.ti.stratego2021.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {

    private List<List<Pawn>> board;

    public Board(){
        board = new ArrayList<>();
        constructEmptyBoard();
    }

    private void constructEmptyBoard(){
        Pawn emptySpot = new Pawn("empty", false,true, "EMPTY");
        Pawn waterSpot = new Pawn("water", true,false,"WATER");
        List<Pawn> emptyRow = Collections.nCopies(10,emptySpot);
        List<Pawn> waterRow = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            waterRow.addAll(Collections.nCopies(2,emptySpot));
            waterRow.addAll(Collections.nCopies(2,waterSpot));
        }
        waterRow.addAll(Collections.nCopies(2,emptySpot));
        board.addAll(Collections.nCopies(4,emptyRow));
        board.addAll(Collections.nCopies(2,waterRow));
        board.addAll(Collections.nCopies(4,emptyRow));
    }

    private void postBlueConfig(Board blueConfig){

    }


}
