package be.howest.ti.stratego2021.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private List<List<Pawn>> theboard;

    public Board(){
        theboard = new ArrayList<>();
        constructEmptyBoard();
    }

    //initializing the board
    private void constructEmptyBoard(){
        theboard = new ArrayList<>(Arrays.asList(getEmptyRow(),getEmptyRow(),getEmptyRow(),getEmptyRow(),getWaterRow(),getWaterRow(),getEmptyRow(),getEmptyRow(),getEmptyRow(),getEmptyRow()));
    }
    private List<Pawn> getEmptyRow(){
        return new ArrayList<>(Arrays.asList(getEmptyPawn(),getEmptyPawn(),getEmptyPawn(),getEmptyPawn(),getEmptyPawn(),getEmptyPawn(),getEmptyPawn(),getEmptyPawn(),getEmptyPawn(),getEmptyPawn()));
    }
    public Pawn getEmptyPawn(){
        return new Pawn("empty", "EMPTY");
    }
    private List<Pawn> getWaterRow(){
        return new ArrayList<>(Arrays.asList(getEmptyPawn(),getEmptyPawn(),getWaterPawn(),getWaterPawn(),getEmptyPawn(),getEmptyPawn(),getWaterPawn(),getWaterPawn(),getEmptyPawn(),getEmptyPawn()));
    }
    private Pawn getWaterPawn(){
        return new Pawn("WATER", "WATER");
    }


    public void postConfig(List<List<String>> config, String token, int startRowIndex, int maxRowIndex){
        for(int rowindex = startRowIndex; rowindex < maxRowIndex; rowindex++){
            for(int colindex = 0; colindex < 10; colindex++){
                if(config.get(rowindex).get(colindex) == null){
                    theboard.get(rowindex).set(colindex,new Pawn("empty", "EMPTY"));
                }else{
                    theboard.get(rowindex).set(colindex,new Pawn(token, config.get(rowindex).get(colindex)));
                }
            }
        }
    }

    public List<List<Pawn>> getBoard(){
        return theboard;
    }

    public Pawn getPawn(Coords src){
        return theboard.get(src.getRow()).get(src.getCol());
    }

    public void setPawn(Coords src, Pawn pawn){
        theboard.get(src.getRow()).set(src.getCol(),pawn);
    }

    @Override
    public String toString() {
        return "" + theboard;
    }
}
