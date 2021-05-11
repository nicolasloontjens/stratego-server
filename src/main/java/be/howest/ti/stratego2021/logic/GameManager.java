package be.howest.ti.stratego2021.logic;

import be.howest.ti.stratego2021.web.bridge.ReturnBoardGetBody;
import be.howest.ti.stratego2021.web.bridge.ReturnBoardPawn;
import be.howest.ti.stratego2021.logic.StrategoController;

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
            throw new IllegalArgumentException();
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

    public Move infiltrate(String gameID, Coords src, Coords tar, String token, String infiltrationGuess){
        Game game = getGameById(gameID);
        if(game.getRedToken().equals(token)){
            src.invertCoords();
            tar.invertCoords();
        }
        return game.infiltratePlayer(src, tar, token, infiltrationGuess);
    }

    public Move movePlayer(String gameID,Coords src, Coords tar, String token){
        Game game = getGameById(gameID);
        if(game.getRedToken().equals(token)){
            src.invertCoords();
            tar.invertCoords();
        }
        return game.movePlayer(src,tar,token);
    }

    public List<List<ReturnBoardPawn>> convertBoardForClient(String gameID, String token){
        Game game = getGameById(gameID);
        return game.returnClientBoard(token);
    }

    public void checkRightVersion(String version,List<List<String>> startConfiguration){
        //get version total pieces
        for (String name: PieceCount.valueOf(version.toUpperCase(Locale.ROOT))) {

        }
        //count and compares pawnTypes + throw when pieces dont match
        countPiece(startConfiguration,pawnTypes, pieceAmount);
    }

    private void countPiece(List<List<String>> startConfiguration,PawnTypes pawnTypes, int pieceAmount) {
        int currentAmount = 0;
        for (List<String> row: startConfiguration) {
            for (String piece: row) {
                if (piece.equals(pawnTypes.toString())){
                    currentAmount++;
                }
            }
        }
        if (currentAmount != pieceAmount){
            throw new IllegalArgumentException();
        }
    }
}
