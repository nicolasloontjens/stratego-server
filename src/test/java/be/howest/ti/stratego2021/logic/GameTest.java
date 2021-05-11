package be.howest.ti.stratego2021.logic;

import be.howest.ti.stratego2021.logic.exceptions.StrategoGameRuleException;
import be.howest.ti.stratego2021.web.exceptions.ForbiddenAccessException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    List<List<String>> returnBlueConfig(){
        List<String> nullList = new ArrayList<>(Arrays.asList(null,null,null,null,null,null,null,null,null,null));
        List<String> pawnList = new ArrayList<>(Arrays.asList("flag","bomb","colonel","colonel","colonel","colonel","colonel","colonel","colonel","infiltrator"));
        return new ArrayList<>(Arrays.asList(nullList,nullList,nullList,nullList,nullList,nullList,pawnList,pawnList,pawnList,pawnList));
    }

    List<List<String>> returnRedConfig(){
        List<String> nullList = new ArrayList<>(Arrays.asList(null,null,null,null,null,null,null,null,null,null));
        List<String> pawnList = new ArrayList<>(Arrays.asList("flag","bomb","sergeant","sergeant","sergeant","sergeant","sergeant","sergeant","sergeant","infiltrator"));
        return new ArrayList<>(Arrays.asList(nullList,nullList,nullList,nullList,nullList,nullList,pawnList,pawnList,pawnList,nullList));
    }

    Game returnWorkingGame(){
        Game game = new Game("1",returnBlueConfig(),"blueTestToken","original");
        game.connectRedPlayer(returnRedConfig(),"redTestToken");
        return game;
    }

    @Test
    void testGetPawnAtPos(){
        Game game = new Game("1",returnBlueConfig(),"","original");
        assertFalse(game.isGameStarted());
        game.connectRedPlayer(returnRedConfig(),"");
        assertEquals("colonel", game.getPawnAtPos(new Coords(6,7)).getPawnType());
        assertEquals("water", game.getPawnAtPos(new Coords(5,3)).getPawnType());
    }

    @Test
    void testMovePawnChangesBoard(){
        Game game = returnWorkingGame();
        game.executeMove(new Coords(6,5),new Coords(5,5),"blueTestToken");
        assertNotEquals(returnWorkingGame().getBoard(),game.getBoard());
    }

    @Test
    void testTokenAuthForMove(){
        Game game = returnWorkingGame();
        assertThrows(StrategoGameRuleException.class,() -> {
            game.applyGameRulesAndCheckIfAttackOrMove(new Coords(6,5),new Coords(5,5),"notAvalidToken");
            game.executeMove(new Coords(6,5),new Coords(5,5),"notAvalidToken");
        });
    }

    @Test
    void testOutOfBoundsError(){
        Game game = returnWorkingGame();
        assertThrows(StrategoGameRuleException.class, () ->{
            game.applyGameRulesAndCheckIfAttackOrMove(new Coords(11,5),new Coords(5,5),"blueTestToken");
            game.executeMove(new Coords(11,5),new Coords(5,5),"blueTestToken");
        });
    }

    @Test
    void testIfWaterIsNotMoveable(){
        Game game = returnWorkingGame();
        assertThrows(StrategoGameRuleException.class, () ->{
            game.applyGameRulesAndCheckIfAttackOrMove(new Coords(5,5),new Coords(5,6),"blueTestToken");
            game.executeMove(new Coords(5,5),new Coords(5,6),"blueTestToken");
        });
    }

    @Test
    void testIfEnemyIsNotMoveable(){
        Game game = returnWorkingGame();
        assertThrows(StrategoGameRuleException.class, () ->{
            game.applyGameRulesAndCheckIfAttackOrMove(new Coords(3,5),new Coords(4,5),"blueTestToken");
            game.executeMove(new Coords(3,5),new Coords(4,5),"blueTestToken");
        });
    }

    @Test
    void checkIfTurnsWork(){
        Game game = returnWorkingGame();
        assertThrows(StrategoGameRuleException.class, () ->{
            game.applyGameRulesAndCheckIfAttackOrMove(new Coords(6,5),new Coords(5,5),"redTestToken");
            game.executeMove(new Coords(6,5),new Coords(5,5),"redTestToken");
        });
    }

    @Test
    void checkIfYouCanAttackYourOwnPawns(){
        Game game = returnWorkingGame();
        assertThrows(StrategoGameRuleException.class, () ->{
            game.applyGameRulesAndCheckIfAttackOrMove(new Coords(9,9),new Coords(8,9),"blueTestToken");
            game.executeAttack(new Coords(9,9),new Coords(8,9),"blueTestToken");
        });
    }

    @Test
    void checkReturnedMoveObject(){
        Game game = returnWorkingGame();
        Move move = game.executeMove(new Coords(6,4),new Coords(5,4),"blueTestToken");
        assertEquals("BLUE",move.getPlayer());
        assertEquals(new Coords(6,4),move.getSrc());
        assertEquals(new Coords(5,4),move.getTar());
        assertEquals("blueTestToken",game.getPawnAtPos(new Coords(5,4)).getPlayerToken());
    }

    @Test
    void checkIfAttackWorks(){
        Game game = returnWorkingGame();
        game.executeMove(new Coords(6,4),new Coords(5,4),"blueTestToken");
        game.executeMove(new Coords(3,4),new Coords(4,4),"redTestToken");
        AttackMove move = game.executeAttack(new Coords(5,4),new Coords(4,4),"blueTestToken");
        assertEquals("colonel",move.getAttack().getAttacker());
        assertEquals("sergeant",move.getAttack().getDefender());
        assertEquals("attacker",move.getAttack().getWinner());
        assertEquals("colonel",game.getPawnAtPos(new Coords(4,4)).getPawnType());
        assertEquals("empty",game.getPawnAtPos(new Coords(5,4)).getPawnType());
    }

    @Test
    void checkIfFlagIsNotMoveable(){
        Game game = returnWorkingGame();
        assertThrows(StrategoGameRuleException.class, () ->{
            game.applyGameRulesAndCheckIfAttackOrMove(new Coords(6,0),new Coords(5,0),"blueTestToken");
            game.executeMove(new Coords(6,0),new Coords(5,0),"blueTestToken");
        });
    }

    @Test
    void checkIfBombIsNotMoveable(){
        Game game = returnWorkingGame();
        assertThrows(StrategoGameRuleException.class, () ->{
            game.applyGameRulesAndCheckIfAttackOrMove(new Coords(6,1),new Coords(5,1),"blueTestToken");
            game.executeMove(new Coords(6,1),new Coords(5,1),"blueTestToken");
        });
    }

    @Test
    void checkIfInfiltratingIsPossible(){
        Game game = returnWorkingGame();
        game.executeMove(new Coords(6,9),new Coords(5,9),"blueTestToken");
        game.executeMove(new Coords(3,9),new Coords(4,9),"redTestToken");
        game.executeMove(new Coords(6,4),new Coords(5,4),"blueTestToken");
        game.executeMove(new Coords(4,9),new Coords(4,8),"redTestToken");
        game.executeMove(new Coords(5,9),new Coords(4,9),"blueTestToken");
        game.executeMove(new Coords(4,8),new Coords(5,8),"redTestToken");
        game.executeMove(new Coords(4,9),new Coords(3,9),"blueTestToken");
        game.executeMove(new Coords(5,8),new Coords(6,8),"redTestToken");

        InfiltrationMove move = game.infiltratePlayer(new Coords(3,9),new Coords(2,9),"blueTestToken","infiltrator");
        assertEquals("infiltrator",move.getInfiltration().getExpected());
        assertEquals("flag",move.getInfiltration().getActual());
        assertFalse(move.getInfiltration().isSuccess());
    }

    @Test
    void testInfiltrationEnemyTerritoryCheck(){
        Game game = returnWorkingGame();
        game.executeMove(new Coords(6,9),new Coords(5,9),"blueTestToken");
        game.executeMove(new Coords(3,9),new Coords(4,9),"redTestToken");
        game.executeMove(new Coords(6,4),new Coords(5,4),"blueTestToken");
        game.executeMove(new Coords(4,9),new Coords(4,8),"redTestToken");
        game.executeMove(new Coords(5,9),new Coords(4,9),"blueTestToken");
        game.executeMove(new Coords(4,8),new Coords(5,8),"redTestToken");
        assertThrows(StrategoGameRuleException.class, () ->{
            InfiltrationMove move = game.infiltratePlayer(new Coords(4,9),new Coords(2,9),"blueTestToken","infiltrator");
        });
    }

    @Test
    void testInfiltrationFriendlyFireCheck(){
        Game game = returnWorkingGame();
        game.executeMove(new Coords(6,9),new Coords(5,9),"blueTestToken");
        game.executeMove(new Coords(3,9),new Coords(4,9),"redTestToken");
        game.executeMove(new Coords(6,4),new Coords(5,4),"blueTestToken");
        game.executeMove(new Coords(4,9),new Coords(4,8),"redTestToken");
        game.executeMove(new Coords(5,9),new Coords(4,9),"blueTestToken");
        game.executeMove(new Coords(4,8),new Coords(5,8),"redTestToken");
        game.getBoard().setPawn(new Coords(2,9),new Pawn("blueTestToken","colonel"));
        assertThrows(StrategoGameRuleException.class, () ->{
            InfiltrationMove move = game.infiltratePlayer(new Coords(4,9),new Coords(2,9),"blueTestToken","colonel");
        });
    }

}