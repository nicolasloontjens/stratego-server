package be.howest.ti.stratego2021.logic;

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

    @Test
    void testGetPawnAtPos(){
        Game game = new Game("1",returnBlueConfig(),"","original");
        assertFalse(game.isGameStarted());
        game.connectRedPlayer(returnRedConfig(),"");
        assertEquals("colonel", game.getPawnAtPos(new Coords(6,7)).getPawnType());
        assertEquals("water", game.getPawnAtPos(new Coords(5,3)).getPawnType());
    }

}