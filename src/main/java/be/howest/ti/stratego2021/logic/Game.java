package be.howest.ti.stratego2021.logic;

import java.util.*;

public class Game {

    private String gameId;
    private final String blueToken;
    private String redToken;
    private final PieceCount gameType;
    private Board board;
    private List<Move> moveList;
    private boolean gameStarted;

    public Game(String  id, List<List<String>> blueConfig, String blueToken, String gameType){
        gameId = id;
        this.blueToken = blueToken;
        this.redToken = null;
        this.gameType = PieceCount.valueOf(gameType.toUpperCase(Locale.ROOT));
        board = new Board();
        board.postConfig(blueConfig, blueToken, 6,10);
        moveList = new ArrayList<>();
        moveList.add(new Move("blue",new Coords(0,0),new Coords(0,0)));
        gameStarted = false;
    }

    public void connectRedPlayer(List<List<String>> redConfig, String redToken){
        Collections.reverse(redConfig);
        for(List<String> row : redConfig){
            Collections.reverse(row);
        }
        board.postConfig(redConfig, redToken, 0,4);
        this.redToken = redToken;
        moveList.add(new Move("red",new Coords(0,0),new Coords(0,0)));
        gameStarted = true;
    }

    public boolean isGameStarted(){
        return gameStarted;
    }

    public Board getBoard(){
        return board;
    }

    public String getGameId(){
        return gameId;
    }

    public String getBlueToken() {
        return blueToken;
    }

    public String getRedToken() {
        return redToken;
    }

    public Pawn getPawnAtPos(Coords src){
        return board.getPawn(src);
    }

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
            
        }
        return true;
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

    @Override
    public String toString() {
        return "Game{" +
                "gameId='" + gameId + '\'' +
                ", blueToken='" + blueToken + '\'' +
                ", redToken='" + redToken + '\'' +
                ", gameType=" + gameType +
                ", board=" + board +
                ", moveList=" + moveList +
                ", gameStarted=" + gameStarted +
                '}';
    }
}