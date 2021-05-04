package be.howest.ti.stratego2021.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Board {

    private List<List<Pawn>> board;

    public Board(){
        board = new ArrayList<>();
        constructEmptyBoard();
    }

    private void constructEmptyBoard(){
        board = new ArrayList<>(Arrays.asList(getEmptyRow(),getEmptyRow(),getEmptyRow(),getEmptyRow(),getWaterRow(),getWaterRow(),getEmptyRow(),getEmptyRow(),getEmptyRow(),getEmptyRow()));
    }

    private List<Pawn> getEmptyRow(){
        return new ArrayList<>(Arrays.asList(getEmptyPawn(),getEmptyPawn(),getEmptyPawn(),getEmptyPawn(),getEmptyPawn(),getEmptyPawn(),getEmptyPawn(),getEmptyPawn(),getEmptyPawn(),getEmptyPawn()));
    }

    private Pawn getEmptyPawn(){
        return new Pawn("empty", "EMPTY");
    }

    private List<Pawn> getWaterRow(){
        return new ArrayList<>(Arrays.asList(getEmptyPawn(),getEmptyPawn(),getWaterPawn(),getWaterPawn(),getEmptyPawn(),getEmptyPawn(),getWaterPawn(),getWaterPawn(),getEmptyPawn(),getEmptyPawn()));
    }

    private Pawn getWaterPawn(){
        return new Pawn("WATER", "WATER");
    }

    public void postBlueConfig(List<List<String>> blueConfig, String blueToken){
        for(int rowindex = 6; rowindex < 10; rowindex++){
            for(int colindex = 0; colindex < 10; colindex++){
                if(blueConfig.get(rowindex).get(colindex) == null){
                    board.get(rowindex).set(colindex,new Pawn(blueToken, "EMPTY"));
                }else{
                    board.get(rowindex).set(colindex,new Pawn(blueToken, blueConfig.get(rowindex).get(colindex)));
                }
            }
        }
    }

    public void postRedConfig(List<List<String>> redConfig, String redToken){
        //reverse the config so it looks like blue pov
        Collections.reverse(redConfig);
        for(List<String> row : redConfig){
            Collections.reverse(row);
        }
        for(int rowindex = 0; rowindex < 4; rowindex++){
            for(int colindex = 0; colindex < 10; colindex++){
                if(redConfig.get(rowindex).get(colindex) == null){
                    board.get(rowindex).set(colindex,new Pawn(redToken, "EMPTY"));
                }else{
                    board.get(rowindex).set(colindex,new Pawn(redToken, redConfig.get(rowindex).get(colindex)));
                }
            }
        }
    }

    public List<List<Pawn>> getBoard(){
        return board;
    }

    public Pawn getPawn(Coords src){
        return board.get(src.getRow()).get(src.getCol());
    }

    @Override
    public String toString() {
        return "" + board;
    }
}
