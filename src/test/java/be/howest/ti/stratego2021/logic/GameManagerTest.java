package be.howest.ti.stratego2021.logic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    List<List<String>> returnBlueConfig(){
        List<String> nullList = new ArrayList<>(Arrays.asList(null,null,null,null,null,null,null,null,null,null));
        List<String> pawnList = new ArrayList<>(Arrays.asList("flag","bomb","colonel","colonel","scout","colonel","colonel","colonel","colonel","infiltrator"));
        return new ArrayList<>(Arrays.asList(nullList,nullList,nullList,nullList,nullList,nullList,pawnList,pawnList,pawnList,pawnList));
    }

    List<List<String>> returnRedConfig(){
        List<String> nullList = new ArrayList<>(Arrays.asList(null,null,null,null,null,null,null,null,null,null));
        List<String> pawnList = new ArrayList<>(Arrays.asList("flag","bomb","sergeant","sergeant",null,"sergeant","sergeant","sergeant","sergeant","infiltrator"));
        return new ArrayList<>(Arrays.asList(nullList,nullList,nullList,nullList,nullList,nullList,pawnList,pawnList,pawnList,nullList));
    }

    GameManager gameManager = new GameManager();

    @Test
    void testJoinGame(){
        gameManager.connectToGame("original","blueTestToken","groep25test",returnBlueConfig());
        assertTrue(gameManager.checkForExistingGames("original"));
    }

    @Test
    void testTokenBelongsToGame(){
        gameManager.connectToGame("original","blueTestToken","groep25test",returnBlueConfig());
        assertTrue(gameManager.checkIfTokenBelongsToGame(gameManager.getGameById("groep25test"),"blueTestToken"));
    }

    @Test
    void gamesCounterIncreases(){
        gameManager.connectToGame("original","blueTestToken","groep25test",returnBlueConfig());
        gameManager.connectToGame("original","redTestToken","groep25test",returnRedConfig());
        assertTrue(gameManager.getGamesCounter()>0);
    }

}