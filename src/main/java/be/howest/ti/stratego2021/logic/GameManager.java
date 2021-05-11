package be.howest.ti.stratego2021.logic;

import be.howest.ti.stratego2021.logic.exceptions.StrategoGameRuleException;
import be.howest.ti.stratego2021.web.bridge.ReturnBoardPawn;
import be.howest.ti.stratego2021.logic.StrategoController;
import be.howest.ti.stratego2021.web.exceptions.InvalidTokenException;

import java.util.*;

public class GameManager {

    private Map<String, List<Game>> gamesList;
    private Map<String,Game> gamesById;
    private int gamesCounter;

    public GameManager(){
        gamesList = new HashMap<>();
        gamesById = new HashMap<>();
        gamesList.put("original",new ArrayList<>());
        gamesList.put("infiltrator",new ArrayList<>());
        gamesList.put("duel",new ArrayList<>());
        gamesList.put("mini",new ArrayList<>());
        gamesList.put("tiny",new ArrayList<>());
    }

    public void connectToGame(String version, String token, String id, List<List<String>> config){
        if(checkForExistingGames(version)){
            gamesList.get(version).get(gamesList.get(version).size()-1).connectRedPlayer(config,token);
            gamesCounter++;
        }
        else{
            Game game = new Game(id,config,token,version);
            gamesList.get(version).add(game);
            gamesById.put(id,game);
        }
    }

    public Game getGameById(String gameID){
        if(!gamesById.containsKey(gameID)){
            throw new StrategoGameRuleException("The game doesn't exist");
        }
        return gamesById.get(gameID);
    }

    public boolean checkIfTokenBelongsToGame(Game game, String token){
        return game.getBlueToken().equals(token) || game.getRedToken().equals(token);
    }

    public int getGamesCounter(){
        return gamesCounter;
    }

    public boolean checkForExistingGames(String version){
        for(Game game: gamesList.get(version)){
            if(!game.isGameStarted()){
                return true;
            }
        }
        return false;
    }

    public boolean applyGameRulesAndCheckIfAttackOrMove(String gameID, Coords src, Coords tar, String token){
        //method for controller that will either execute move method or attack method
        Game game = getGameById(gameID);
        if(!game.isGameStarted()){
            throw new StrategoGameRuleException("the game hasn't started yet");
        }
        if(game.getRedToken().equals(token)){
            return game.applyGameRulesAndCheckIfAttackOrMove(src.invertCoords(),tar.invertCoords(),token);
        }
        return game.applyGameRulesAndCheckIfAttackOrMove(src,tar,token);
    }

    public Move movePlayer(String gameID,Coords src, Coords tar, String token){
        Game game = getGameById(gameID);
        if(game.getRedToken().equals(token)){
            return game.executeMove(src.invertCoords(),tar.invertCoords(),token);
        }
        return game.executeMove(src,tar,token);
    }

    public AttackMove attackPlayer(String gameID, Coords src, Coords tar, String token){
        Game game = getGameById(gameID);
        if(game.getRedToken().equals(token)){
            return game.executeAttack(src.invertCoords(),tar.invertCoords(),token);
        }
        return game.executeAttack(src,tar,token);
    }

    public InfiltrationMove infiltrate(String gameID, Coords src, Coords tar, String token, String infiltrationGuess){
        Game game = getGameById(gameID);
        if(game.getRedToken().equals(token)){
            return game.infiltratePlayer(src.invertCoords(), tar.invertCoords(), token, infiltrationGuess);
        }
        return game.infiltratePlayer(src, tar, token, infiltrationGuess);
    }

    public List<Move> getMovesFromGame(String gameID, String player){
        Game game = getGameById(gameID);
        if(checkIfTokenBelongsToGame(game,player)){
            return game.getMoveList();
        }
        throw new StrategoGameRuleException("Game doesn't exist, or you don't have the right token");
    }

    public List<List<ReturnBoardPawn>> convertBoardForClient(String gameID, String token){
        Game game = getGameById(gameID);
        return game.returnClientBoard(token);
    }

    public boolean checkConfig(String version, List<List<String>> startConfig){
        Map<PawnTypes, Integer> pieceCount = PieceCount.valueOf(version.toUpperCase(Locale.ROOT)).getCounters();
        Map<PawnTypes,Integer> configCount = new HashMap<>();
        for(List<String> row: startConfig){
            for(String piece : row){
                if(piece != null){
                    PawnTypes currentpawn = PawnTypes.valueOf(piece);
                    if(configCount.containsKey(currentpawn)){
                        configCount.put(currentpawn,configCount.get(PawnTypes.valueOf(piece))+1);
                    }else{
                        configCount.put(currentpawn,1);
                    }
                }
            }
        }
        return configCount.equals(pieceCount);
    }

}
