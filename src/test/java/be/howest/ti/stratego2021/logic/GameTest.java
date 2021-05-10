package be.howest.ti.stratego2021.logic;

import be.howest.ti.stratego2021.web.exceptions.ForbiddenAccessException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    List<List<String>> returnBlueConfig(){
        List<String> nullList = new ArrayList<>(Arrays.asList(null,null,null,null,null,null,null,null,null,null));
        List<String> pawnList = new ArrayList<>(Arrays.asList("colonel","colonel","colonel","colonel","colonel","colonel","colonel","colonel","colonel","colonel"));
        return new ArrayList<>(Arrays.asList(nullList,nullList,nullList,nullList,nullList,nullList,pawnList,pawnList,pawnList,pawnList));
    }

    List<List<String>> returnRedConfig(){
        List<String> nullList = new ArrayList<>(Arrays.asList(null,null,null,null,null,null,null,null,null,null));
        List<String> pawnList = new ArrayList<>(Arrays.asList("sergeant","sergeant","sergeant","sergeant","sergeant","sergeant","sergeant","sergeant","sergeant","sergeant"));
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
        game.movePlayer(new Coords(6,5),new Coords(5,5),"blueTestToken");
        assertNotEquals(returnWorkingGame().getBoard(),game.getBoard());
    }

    @Test
    void testTokenAuthForMove(){
        Game game = returnWorkingGame();
        assertThrows(ForbiddenAccessException.class,() -> {
            game.movePlayer(new Coords(6,5),new Coords(5,5),"notAvalidToken");
        });
    }

    @Test
    void testOutOfBoundsError(){
        Game game = returnWorkingGame();
        assertThrows(IllegalArgumentException.class, () ->{
            game.movePlayer(new Coords(11,5),new Coords(5,5),"blueTestToken");
        });
    }

}