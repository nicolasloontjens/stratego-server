package be.howest.ti.stratego2021.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager {

    private Map<String, List<Game>> gamesList;

    public GameManager(){
        gamesList = new HashMap<>();
        gamesList.put("original",new ArrayList<>());
        gamesList.put("infiltrator",new ArrayList<>());
        gamesList.put("duel",new ArrayList<>());
        gamesList.put("mini",new ArrayList<>());
        gamesList.put("tiny",new ArrayList<>());
    }

    public void connectToGame(String version, String token, List<List<String>> config){
        if(checkForExistingGames(version)){
            gamesList.get(version).get(gamesList.get(version).size()).connectRedPlayer(config);
        }
        else{
            gamesList.get(version).add(new Game("",null,version));
        }
    }

    private boolean checkForExistingGames(String version){
        for(Game game: gamesList.get(version)){
            if(!game.isGameStarted()){
                return true;
            }
        }
        return false;
    }
}
