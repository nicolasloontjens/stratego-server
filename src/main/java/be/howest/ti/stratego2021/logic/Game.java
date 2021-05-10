package be.howest.ti.stratego2021.logic;

import be.howest.ti.stratego2021.logic.exceptions.StrategoGameRuleException;
import be.howest.ti.stratego2021.web.bridge.ReturnBoardGetBody;
import be.howest.ti.stratego2021.web.bridge.ReturnBoardPawn;

import java.util.*;

public class Game {

    private final String gameId;
    private final String blueToken;
    private String redToken;
    private final PieceCount gameType;
    private Board board;
    private List<Move> moveList;
    private boolean gameStarted;
    private boolean isBlueTurn;

    public Game(String  id, List<List<String>> blueConfig, String blueToken, String gameType){
        gameId = id;
        this.blueToken = blueToken;
        this.redToken = null;
        this.gameType = PieceCount.valueOf(gameType.toUpperCase(Locale.ROOT));
        board = new Board();
        board.postConfig(blueConfig, blueToken, 6,10);
        moveList = new ArrayList<>();
        moveList.add(new Move("blue",new Coords(0,0),new Coords(0,0)));
        isBlueTurn = true;
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

    private boolean checkIfMyTurn(String token){
        if(blueToken.equals(token)&&isBlueTurn){
            return true;
        }else return redToken.equals(token) && !isBlueTurn;
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

    public List<Move> getMoveList(){
        return moveList;
    }

    public void addMove(Move move){
        moveList.add(move);
    }

    public Pawn getPawnAtPos(Coords src){
        return board.getPawn(src);
    }

    public void setPawnAtPos(Coords src, Pawn pawn){
        board.setPawn(src,pawn);
    }



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


    public boolean applyGameRulesAndCheckIfAttackOrMove(Coords src, Coords tar, String token){
        if(!checkIfMyTurn(token)){
            throw new StrategoGameRuleException("It's not your turn");
        }
        if(!checkIfCoordsOutOfBounds(src,tar)){
            throw new StrategoGameRuleException("Cannot move outside the board");
        }
        if(!validateIfMoveable(src,token)){
            throw new StrategoGameRuleException("The selected pawn isn't movable");
        }
        if(!gameStarted){
            throw new StrategoGameRuleException("The game hasn't started yet");
        }
        if(!validateTargetCoords(src,tar)){
            throw new StrategoGameRuleException("The pawn can't move like that");
        }
        if(!isAttackableTarget(tar, token)){
            throw new StrategoGameRuleException("You can't attack this target");
        }
        if(checkIfCoordsOutOfBounds(src,tar) && validateIfMoveable(src,token)&&gameStarted){
            if(validateTargetCoords(src,tar)&& isAttackableTarget(tar,token)){
                return isEmptySpot(tar);
            }
        }
        throw new IllegalArgumentException();

    }

    public AttackMove executeAttack(Coords src, Coords tar, String token){
        int res = getPawnAtPos(src).compareTo(getPawnAtPos(tar));
        String player = checkIfBlueOrRed(token).toUpperCase(Locale.ROOT);
        isBlueTurn = !isBlueTurn;
        if(res>0){
            AttackMove move = new AttackMove(player,src,tar,getPawnAtPos(src).getPawnType(),getPawnAtPos(tar).getPawnType(),"attacker");
            addMove(move);
            setPawnAtPos(tar,getPawnAtPos(src));
            setPawnAtPos(src,board.getEmptyPawn());
            return move;
        }else if(res == 0){
            AttackMove move = new AttackMove(player,src,tar,getPawnAtPos(src).getPawnType(),getPawnAtPos(tar).getPawnType(),"draw");
            addMove(move);
            setPawnAtPos(tar,board.getEmptyPawn());
            setPawnAtPos(src,board.getEmptyPawn());
            return move;
        }
        else{
            AttackMove move= new AttackMove(player,src,tar,getPawnAtPos(src).getPawnType(),getPawnAtPos(tar).getPawnType(),"defender");
            addMove(move);
            setPawnAtPos(src,board.getEmptyPawn());
            return move;
        }
    }

    public Move executeMove(Coords src, Coords tar, String token){
        Move move = new Move(checkIfBlueOrRed(token).toUpperCase(Locale.ROOT),src,tar);
        addMove(move);
        setPawnAtPos(tar,getPawnAtPos(src));
        setPawnAtPos(src,board.getEmptyPawn());
        isBlueTurn = !isBlueTurn;
        return move;
    }

    private InfiltrationMove executeInfiltration(Coords src, Coords tar, String token, String guess){
        String player = checkIfBlueOrRed(token).toUpperCase(Locale.ROOT);
        isBlueTurn = !isBlueTurn;
        if(getPawnAtPos(tar).getPawnType().equals(guess)){
            InfiltrationMove move = new InfiltrationMove(player,src,tar,guess,getPawnAtPos(tar).getPawnType(),true);
            addMove(move);
            setPawnAtPos(tar,board.getEmptyPawn());
            return move;
        }else{
            InfiltrationMove move = new InfiltrationMove(player,src,tar,guess,getPawnAtPos(tar).getPawnType(),false);
            addMove(move);
            return move;
        }
    }

    public InfiltrationMove infiltratePlayer(Coords src, Coords tar, String token, String guess){
        if(!checkIfMyTurn(token)){
            throw new StrategoGameRuleException("It's not your turn");
        }
        if(!isInfiltrator(src,token)){
            throw new StrategoGameRuleException("You can't infiltrate if you're not the infiltrator");
        }
        if(!checkIfCoordsOutOfBounds(src,tar)){
            throw new StrategoGameRuleException("not on board");
        }
        if(!isInEnemyTerritory(src,tar,token)){
            throw new StrategoGameRuleException("You need to be in the enemies territory to infiltrate");
        }
        if(!infiltratorMovementValidation(src,tar)){
            throw new StrategoGameRuleException("You can't move there");
        }
        if(!isAttackableTarget(tar,token)){
            throw new StrategoGameRuleException("You can't infiltrate that target");
        }
        if(isInfiltrator(src,token)&&checkIfCoordsOutOfBounds(src,tar)&&gameStarted){
            if(isInEnemyTerritory(src,tar,token)){
                if(infiltratorMovementValidation(src,tar)&& isAttackableTarget(tar,token)){
                    return executeInfiltration(src,tar,token,guess);
                }
            }
        }
        throw new IllegalArgumentException();
    }


    //validation methods for the movement of pawns, comments in every function with it's functionality
    private boolean isAttackableTarget(Coords tar, String playerToken){
        //check if the target isn't water, or one of your own pawns
        return !board.getPawn(tar).getPawnType().equals("water") && !board.getPawn(tar).getPlayerToken().equals(playerToken);
    }

    private boolean isEmptySpot(Coords tar){
        //check if tar contains a pawn or an empty spot
        return !getPawnAtPos(tar).getPawnType().equals("empty");
    }

    private boolean validateIfMoveable(Coords src, String token){
        //check if the src pawn can be moved, by checking the type and the token it belongs to
        Pawn pawn = getPawnAtPos(src);
        List<String> nonMoveableTypes = new ArrayList<>(Arrays.asList("water","empty","bomb","flag"));
        return !nonMoveableTypes.contains(pawn.getPawnType()) && pawn.getPlayerToken().equals(token);
    }

    private boolean checkIfCoordsOutOfBounds(Coords src, Coords tar){
        //check if the given Coords are out of bounds and check if the coords arent the same
        return src.getCol() >= 0 && src.getCol() < 10 && src.getRow() >= 0 && src.getRow() < 10 && tar.getCol() >= 0 && tar.getCol() < 10 && tar.getRow() >= 0 && tar.getRow() < 10 && !src.equals(tar);
    }

    private boolean validateTargetCoords(Coords src, Coords tar) {
        //check if the target coords are within the capabilities of the pawn
        //for the scout, we check if there is anything between the src and tar, for normal pawns
        //check if the tar is +1 in any direction
        if(getPawnAtPos(src).getPawnType().equals("scout")) {
            return scoutMovementValidation(src, tar);
        }else{
            return normalPawnMovementValidation(src, tar);
        }
    }

    private boolean scoutMovementValidation(Coords src, Coords tar){
        //check the movement in any direction, first decide what the direction is
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
        //if any spot between the src and tar is not empty, return false
        boolean res = true;
        Coords curCords = new Coords(src.getRow(),src.getCol());
        while(!curCords.equals(tar)){
            curCords.setRow(curCords.getRow()+value);
            if(!getPawnAtPos(curCords).getPawnType().equals("empty")&&curCords.getRow()!=tar.getRow()){
                res = false;
            }
        }
        return res;
    }

    private boolean checkAvailableSpotsHorizontal(Coords src, Coords tar, int value){
        //if any spot between the src and tar is not empty, return false
        boolean res = true;
        Coords curCords = new Coords(src.getRow(),src.getCol());
        while(!curCords.equals(tar)){
            curCords.setCol(curCords.getCol()+value);
            if(!getPawnAtPos(curCords).getPawnType().equals("empty")&&curCords.getCol()!=tar.getCol()){
                res = false;
            }
        }
        return res;
    }

    private boolean normalPawnMovementValidation(Coords src, Coords tar){
        //check if the tar coords deviate by +1 or -1, if not, return false
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


    //infiltrator specific
    private boolean isInfiltrator(Coords src, String token){
        //check if the pawn type is infiltrator, and it's your infiltrator
        return getPawnAtPos(src).getPawnType().equals("infiltrator") && getPawnAtPos(src).getPlayerToken().equals(token);
    }

    private boolean isInEnemyTerritory(Coords src, Coords tar, String token){
        //get player color, then check if they are in enemy territory. the red coords are already adjusted for blue server pov
        if(checkIfBlueOrRed(token).equals("red")){
            return checkEnemyTerritory(src,tar,6,10);
        }else{
            return checkEnemyTerritory(src,tar,0,4);
        }
    }

    private boolean checkEnemyTerritory(Coords src, Coords tar, int startrow,int maxrow){
        //check if both coords are in the enemy territory
        return src.getRow()>= startrow && src.getRow() < maxrow && tar.getRow()>= startrow && tar.getRow()< maxrow;
    }

    private boolean infiltratorMovementValidation(Coords src, Coords tar){
        //check the direction, and if it doesn't move diagonally, then check if it's max +2 or -2 from src, then check if there's nothing between

        if(tar.getRow()>src.getRow() && tar.getCol() == src.getCol() && tar.getRow()<=src.getRow()+2){
            return checkAvailableSpotsVertical(src,tar,+1);
        }
        if(tar.getRow()<src.getRow() && tar.getCol() == src.getCol() && tar.getRow()>=src.getRow()-2){
            return checkAvailableSpotsVertical(src,tar,-1);
        }
        if(tar.getCol()>src.getCol() && tar.getRow() == src.getRow() && tar.getCol()<=src.getCol()+2){
            return checkAvailableSpotsHorizontal(src,tar,+1);
        }
        if(tar.getCol()<src.getCol() && tar.getRow() == src.getRow() && tar.getCol()>=src.getCol()-2){
            return checkAvailableSpotsHorizontal(src,tar,-1);
        }
        return false;
    }

}
