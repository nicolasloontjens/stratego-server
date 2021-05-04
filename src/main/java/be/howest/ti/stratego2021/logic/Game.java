package be.howest.ti.stratego2021.logic;

import java.util.ArrayList;
import java.util.Arrays;
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

    public Pawn getTargetCoords(Coords tar) {return board.getPawn(tar);}

    public Infiltration infiltrate(Coords tar) {return board.infiltrate(tar);}

    public void movePlayer(Coords src, Coords tar, String playerToken){
        if(validateIfMoveable(getPawnAtPos(src),playerToken)){
            if(validateTargetCoords(src,tar,playerToken)){

            }
        }
    }

    private boolean validateIfMoveable(Pawn pawn, String token){
        List<String> nonMoveableTypes = new ArrayList<>(Arrays.asList("water","empty","bomb","flag"));
        return !nonMoveableTypes.contains(pawn.getPawnType()) && pawn.getPlayerToken().equals(token);
    }

    private boolean validateTargetCoords(Coords src, Coords tar, String token){
        if(getPawnAtPos(src).getPawnType().equals("scout")){
            return scoutMovementValidation(src,tar);
        }else{
            if (getTargetCoords(tar).isEmpty()){
                return checkAvailableSpotsHorizontal(src,tar,1) && checkAvailableSpotsVertical(src,tar,1);
            }else{
                return
            }

        }

    }

    private boolean scoutMovementValidation(Coords src, Coords tar){
        if(tar.getRow()>src.getRow() && tar.getCol() == src.getCol()){
            return checkAvailableSpotsVertical(src,tar,+1);
        }
        if(tar.getRow()<src.getRow() && tar.getCol() == src.getCol()){
            return checkAvailableSpotsVertical(src,tar,-1);
        }
        if(tar.getCol()>src.getCol() && tar.getRow() == src.getRow()){
            return checkAvailableSpotsHorizontal(src,tar,+1);
        }
        if(tar.getCol()<src.getCol() && tar.getRow() == src.getRow()){
            return checkAvailableSpotsHorizontal(src,tar,-1);
        }
        return false;
    }


    private boolean checkAvailableSpotsVertical(Coords src, Coords tar, int value) {
        boolean res = true;
        while(!src.equals(tar)){
            src.setRow(src.getRow()+value);
            if(!getPawnAtPos(src).getPawnType().equals("empty")){
                res = false;
            }
        }
        return res;
    }


    private boolean checkAvailableSpotsHorizontal(Coords src, Coords tar, int value){
        boolean res = true;
        while(!src.equals(tar)){
            src.setCol(src.getCol()+value);
            if(!getPawnAtPos(src).getPawnType().equals("empty")){
                res = false;
            }
        }
        return res;
    }

}
