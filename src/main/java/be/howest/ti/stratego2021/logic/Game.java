package be.howest.ti.stratego2021.logic;

import be.howest.ti.stratego2021.web.bridge.ReturnBoardGetBody;
import be.howest.ti.stratego2021.web.bridge.ReturnBoardPawn;

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

    public void setPawnAtPos(Coords src, Pawn pawn){
        board.setPawn(src,pawn);
    }

    public Pawn getTargetCoords(Coords tar) {return board.getPawn(tar);}

    public List<List<ReturnBoardPawn>> returnClientBoard(String token){
        String player = checkIfBlueOrRed(token);
        String enemy = getEnemy(player);
        List<List<ReturnBoardPawn>> res = getEmptyReturnBoard();
        List<String> nonMoveableTypes = new ArrayList<>(Arrays.asList("water","empty"));
        for(int row = 0;row<10;row++){
            for(int col = 0;col<10;col++){
                if(board.getPawn(new Coords(row,col)).getPlayerToken().equals(token)){
                    res.get(row).set(col, new ReturnBoardGetBody(player,board.getPawn(new Coords(row,col)).getPawnType()));
                }
                else if(nonMoveableTypes.contains(board.getPawn(new Coords(row,col)).getPawnType())){
                    res.get(row).set(col,null);
                }
                else{
                    res.get(row).set(col, new ReturnBoardPawn(enemy));
                }
            }
        }
        return flipBoardIfRedPlayer(res,player);
    }

    //methods used to return the board to the user
    private List<List<ReturnBoardPawn>> flipBoardIfRedPlayer(List<List<ReturnBoardPawn>> res,String player){
        if(player.equals("red")){
            Collections.reverse(res);
            for(List<ReturnBoardPawn> row : res){
                Collections.reverse(row);
            }
            return res;
        }
        return res;
    }
    private List<List<ReturnBoardPawn>> getEmptyReturnBoard(){
        return Arrays.asList(getEmptyRow(),getEmptyRow(),getEmptyRow(),getEmptyRow(),getEmptyRow(),getEmptyRow(),getEmptyRow(),getEmptyRow(),getEmptyRow(),getEmptyRow());
    }
    private List<ReturnBoardPawn> getEmptyRow(){
        return Arrays.asList(null,null,null,null,null,null,null,null,null,null);
    }
    private String checkIfBlueOrRed(String token){
        if(redToken.equals(token)){
            return "red";
        }
        return "blue";
    }
    private String getEnemy(String player){
        if(player.equals("red")){
            return "blue";
        }
        return "red";
    }


    public Move movePlayer(Coords src, Coords tar, String playerToken){
        if(validateIfMoveable(getPawnAtPos(src),playerToken)){
            if(validateIfCoordsOutOfBounds(src,tar)){
                if(validateTargetCoords(src,tar)){
                    if(validateTarget(tar,playerToken)){
                        if(isAttack(tar)){
                            return executeAttack(src,tar, playerToken);
                        }
                        else{
                            return executeMove(src,tar,playerToken);
                        }
                    }
                }
            }
        }
        throw new IllegalArgumentException();
    }

    private Move executeAttack(Coords src, Coords tar, String token){
        int res = getPawnAtPos(src).compareTo(getPawnAtPos(tar));
        String player = checkIfBlueOrRed(token).toUpperCase(Locale.ROOT);
        if(res>0){
            Move move = new Move(player,src,tar,getPawnAtPos(src).getPawnType(),getPawnAtPos(tar).getPawnType(),"attacker");
            setPawnAtPos(tar,getPawnAtPos(src));
            setPawnAtPos(src,board.getEmptyPawn());
            return move;
        }else if(res == 0){
            Move move = new Move(player,src,tar,getPawnAtPos(src).getPawnType(),getPawnAtPos(tar).getPawnType(),"draw");
            setPawnAtPos(tar,board.getEmptyPawn());
            setPawnAtPos(src,board.getEmptyPawn());
            return move;
        }
        else{
            Move move= new Move(player,src,tar,getPawnAtPos(src).getPawnType(),getPawnAtPos(tar).getPawnType(),"defender");
            setPawnAtPos(src,board.getEmptyPawn());
            return move;
        }
    }


    private Move executeMove(Coords src, Coords tar, String token){
        setPawnAtPos(tar,getPawnAtPos(src));
        setPawnAtPos(src,board.getEmptyPawn());
        return new Move(checkIfBlueOrRed(token).toUpperCase(Locale.ROOT),src,tar);
    }

    //validation methods for the movement of pawns
    private boolean validateTarget(Coords tar, String playerToken){
        return !board.getPawn(tar).getPawnType().equals("water") && !board.getPawn(tar).getPlayerToken().equals(playerToken);
    }

    private boolean isAttack(Coords tar){
        return !getPawnAtPos(tar).getPawnType().equals("empty");
    }

    private boolean validateIfMoveable(Pawn pawn, String token){
        List<String> nonMoveableTypes = new ArrayList<>(Arrays.asList("water","empty","bomb","flag"));
        return !nonMoveableTypes.contains(pawn.getPawnType()) && pawn.getPlayerToken().equals(token);
    }

    private boolean validateIfCoordsOutOfBounds(Coords src, Coords tar){
        return src.getCol() > 0 && src.getCol() < 10 && src.getRow() > 0 && src.getRow() < 10 && tar.getCol() > 0 && tar.getCol() < 10 && tar.getRow() > 0 && tar.getRow() < 10;
    }

    private boolean validateTargetCoords(Coords src, Coords tar) {
        if(getPawnAtPos(src).getPawnType().equals("scout")) {
            return scoutMovementValidation(src, tar);
        }else{
            return normalPawnMovementValidation(src, tar);
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

    private boolean normalPawnMovementValidation(Coords src, Coords tar){
        if(tar.getRow()>src.getRow() && tar.getCol()==src.getCol()){
            return tar.getRow() == src.getRow() + 1;
        }
        if(tar.getRow()<src.getRow() && tar.getCol()==src.getCol()){
            return tar.getRow() == src.getRow() - 1;
        }
        if(tar.getCol()>src.getCol() && tar.getRow()==src.getRow()){
            return tar.getCol() == src.getCol() + 1;
        }
        if(tar.getCol()<src.getCol() && tar.getRow()==src.getRow()){
            return tar.getCol() == src.getCol() - 1;
        }
        return false;
    }

}
