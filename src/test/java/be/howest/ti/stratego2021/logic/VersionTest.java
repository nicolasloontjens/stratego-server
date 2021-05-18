package be.howest.ti.stratego2021.logic;

import be.howest.ti.stratego2021.logic.exceptions.StrategoGameRuleException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class VersionTest {

    List<List<String>> returnTinyConfig(){
        List<String> nullList = new ArrayList<>(Arrays.asList(null,null,null,null,null,null,null,null,null,null));
        List<String> flagList = new ArrayList<>(Arrays.asList("flag",null,null,null,null,null,null,null,null,null));
        List<String> marshalList = new ArrayList<>(Arrays.asList(null,null,null,"marshal",null,null,null,null,null,null));
        return new ArrayList<>(Arrays.asList(nullList,nullList,nullList,nullList,nullList,nullList,flagList,nullList,marshalList,nullList));
    }

    List<List<String>> returnFalseTinyConfig(){
        List<String> nullList = new ArrayList<>(Arrays.asList(null,null,null,null,null,null,null,null,null,null));
        List<String> flagList = new ArrayList<>(Arrays.asList("flag",null,null,null,null,null,null,null,null,null));
        List<String> marshalList = new ArrayList<>(Arrays.asList(null,"marshal",null,"marshal",null,null,null,null,null,null));
        return new ArrayList<>(Arrays.asList(nullList,nullList,nullList,nullList,nullList,nullList,flagList,nullList,marshalList,nullList));
    }

    List<List<String>> returnEmptyConfig(){
        List<String> nullList = new ArrayList<>(Arrays.asList(null,null,null,null,null,null,null,null,null,null));
        return new ArrayList<>(Arrays.asList(nullList,nullList,nullList,nullList,nullList,nullList,nullList,nullList,nullList,nullList));
    }

    List<List<String>> returnTooLargeConfigRow(){
        List<String> nullList = new ArrayList<>(Arrays.asList(null,null,null,null,null,null,null,null,null,null));
        List<String> flagList = new ArrayList<>(Arrays.asList("flag",null,null,null,null,null,null,null,null,null));
        List<String> marshalList = new ArrayList<>(Arrays.asList(null,null,null,"marshal",null,null,null,null,null,null));
        return new ArrayList<>(Arrays.asList(nullList,nullList,nullList,nullList,nullList,nullList,flagList,nullList,marshalList,nullList,nullList, nullList,nullList,nullList));
    }

    List<List<String>> returnTooSmallConfigRow(){
        List<String> nullList = new ArrayList<>(Arrays.asList(null,null,null,null,null,null,null,null,null,null));
        List<String> flagList = new ArrayList<>(Arrays.asList("flag",null,null,null,null,null,null,null,null,null));
        List<String> marshalList = new ArrayList<>(Arrays.asList(null,null,null,"marshal",null,null,null,null,null,null));
        return new ArrayList<>(Arrays.asList(flagList,nullList,marshalList));
    }

    List<List<String>> returnTooLargeConfigCol(){
        List<String> nullList = new ArrayList<>(Arrays.asList(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null));
        List<String> flagList = new ArrayList<>(Arrays.asList("flag",null,null,null,null,null,null,null,null,null));
        List<String> marshalList = new ArrayList<>(Arrays.asList(null,null,null,"marshal",null,null,null,null,null,null));
        return new ArrayList<>(Arrays.asList(nullList,nullList,nullList,nullList,nullList,nullList,flagList,nullList,marshalList,nullList));
    }

    List<List<String>> returnTooSmallConfigCol(){
        List<String> nullList = new ArrayList<>(Arrays.asList(null,null));
        List<String> flagList = new ArrayList<>(Arrays.asList("flag",null,null,null,null,null,null,null,null,null));
        List<String> marshalList = new ArrayList<>(Arrays.asList(null,null,null,"marshal",null,null,null,null,null,null));
        return new ArrayList<>(Arrays.asList(nullList,nullList,nullList,nullList,nullList,nullList,flagList,nullList,marshalList,nullList));
    }

    @Test
    void checkIfVersionHasRightAmount(){
        GameManager gameManager = new GameManager();
        assertThrows(StrategoGameRuleException.class, ()-> {
            gameManager.checkConfig("tiny", returnFalseTinyConfig());
        });
    }

    @Test
    void checkIfRightVersion(){
        GameManager gameManager = new GameManager();
        assertThrows(StrategoGameRuleException.class, ()-> {
            gameManager.checkConfig("mini", returnTinyConfig());
        });
    }

    @Test
    void checkIfLessAmount(){
        GameManager gameManager = new GameManager();
        assertThrows(StrategoGameRuleException.class, ()-> {
            gameManager.checkConfig("tiny", returnEmptyConfig());
        });
    }

    @Test
    void checkIfConfigTooLarge(){
        GameManager gameManager = new GameManager();
        assertThrows(StrategoGameRuleException.class, ()-> {
            gameManager.checkConfig("tiny", returnTooLargeConfigRow());
        });
        assertThrows(StrategoGameRuleException.class, ()-> {
            gameManager.checkConfig("tiny", returnTooLargeConfigCol());
        });
    }

    @Test
    void checkIfConfigTooSmall(){
        GameManager gameManager = new GameManager();
        assertThrows(StrategoGameRuleException.class, ()-> {
            gameManager.checkConfig("tiny", returnTooSmallConfigRow());
        });
        assertThrows(StrategoGameRuleException.class, ()-> {
            gameManager.checkConfig("tiny", returnTooSmallConfigCol());
        });
    }

    @Test
    void testGetName(){
        Version version = new Version("original",PieceCount.valueOf("original".toUpperCase(Locale.ROOT)));
        assertEquals("original",version.getName());
    }

    @Test
    void testGetPieceCount(){
        Version version = new Version("original",PieceCount.valueOf("original".toUpperCase(Locale.ROOT)));
        assertEquals(PieceCount.valueOf("ORIGINAL").getCounters(),version.getPieceCount());
    }


}