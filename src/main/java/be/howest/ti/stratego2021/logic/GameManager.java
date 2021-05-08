package be.howest.ti.stratego2021.logic;

import be.howest.ti.stratego2021.web.exceptions.InvalidTokenException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
