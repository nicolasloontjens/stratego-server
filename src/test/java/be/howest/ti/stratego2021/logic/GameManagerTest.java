package be.howest.ti.stratego2021.logic;

import be.howest.ti.stratego2021.logic.exceptions.StrategoGameRuleException;
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
        assertTrue(gameManager.getGamesCounter("original")>0);
    }

    @Test
    void testIfMoveWorks(){
        gameManager.connectToGame("original","blueTestToken","groep25test",returnBlueConfig());
        gameManager.connectToGame("original","redTestToken","groep25test",returnRedConfig());

        assertTrue(gameManager.applyGameRulesAndCheckIfAttackOrMove("groep25test",new Coords(6,4),new Coords(3,4),"blueTestToken"));
        AttackMove attack = gameManager.attackPlayer("groep25test",new Coords(6,4),new Coords(3,4),"blueTestToken");
        assertFalse(gameManager.applyGameRulesAndCheckIfAttackOrMove("groep25test",new Coords(6,3),new Coords(6,4),"redTestToken"));
        Move move = gameManager.movePlayer("groep25test",new Coords(6,3),new Coords(6,4),"redTestToken");

        assertEquals("BLUE",attack.getPlayer());
        assertEquals("RED",move.getPlayer());
        assertEquals("empty",gameManager.getGameById("groep25test").getPawnAtPos(new Coords(6,4)).getPawnType());
        assertEquals("sergeant",gameManager.getGameById("groep25test").getPawnAtPos(new Coords(3,4)).getPawnType());
        assertEquals("empty",gameManager.getGameById("groep25test").getPawnAtPos(new Coords(3,6)).getPawnType());
        assertEquals("sergeant",gameManager.getGameById("groep25test").getPawnAtPos(new Coords(3,5)).getPawnType());
    }

    @Test
    void testIfAttackWorks(){
        gameManager.connectToGame("original","blueTestToken","groep25test",returnBlueConfig());
        gameManager.connectToGame("original","redTestToken","groep25test",returnRedConfig());
        assertTrue(gameManager.applyGameRulesAndCheckIfAttackOrMove("groep25test",new Coords(6,4),new Coords(3,4),"blueTestToken"));
        AttackMove attack = gameManager.attackPlayer("groep25test",new Coords(6,4),new Coords(3,4),"blueTestToken");
        assertEquals("BLUE",attack.getPlayer());
        assertEquals("scout",attack.getAttack().getAttacker());
        assertEquals("sergeant",attack.getAttack().getDefender());
        assertEquals("defender",attack.getAttack().getWinner());
        assertEquals("empty",gameManager.getGameById("groep25test").getPawnAtPos(new Coords(6,4)).getPawnType());
        assertEquals("sergeant",gameManager.getGameById("groep25test").getPawnAtPos(new Coords(3,4)).getPawnType());
    }

    @Test
    void testIfGameDoesntExist(){
        gameManager.connectToGame("original","blueTestToken","groep25test",returnBlueConfig());
        gameManager.connectToGame("original","redTestToken","groep25test",returnRedConfig());
        assertThrows(StrategoGameRuleException.class,() -> {
            gameManager.getGameById("groep25tes");
        });
    }

    @Test
    void testIfTokenDoesntBelongToGame(){
        gameManager.connectToGame("original","blueTestToken","groep25test",returnBlueConfig());
        gameManager.connectToGame("original","redTestToken","groep25test",returnRedConfig());
        assertFalse(gameManager.checkIfTokenBelongsToGame(gameManager.getGameById("groep25test"),"TestToken"));
    }

    @Test
    void testGetMovesFromGame(){
        gameManager.connectToGame("original","blueTestToken","groep25test",returnBlueConfig());
        gameManager.connectToGame("original","redTestToken","groep25test",returnRedConfig());
        assertEquals(2,gameManager.getMovesFromGame("groep25test","blueTestToken").size());
    }

    @Test
    void testGetMovesError(){
        gameManager.connectToGame("original","blueTestToken","groep25test",returnBlueConfig());
        gameManager.connectToGame("original","redTestToken","groep25test",returnRedConfig());
        assertThrows(StrategoGameRuleException.class,() -> {
            gameManager.getMovesFromGame("groep25test","bluTestToken");
        });
    }

    @Test
    void testGiveUpMove(){
        gameManager.connectToGame("original","blueTestToken","groep25test",returnBlueConfig());
        gameManager.connectToGame("original","redTestToken","groep25test",returnRedConfig());
        gameManager.giveUpMove("groep25test");
        assertEquals("PLAYERHASGIVENUP",gameManager.getGameById("groep25test").getMoveList().get(2).getPlayer());
    }

}