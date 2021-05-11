package be.howest.ti.stratego2021.logic;

import be.howest.ti.stratego2021.logic.exceptions.StrategoGameRuleException;
import be.howest.ti.stratego2021.web.tokens.RandomGeneratedTextTokens;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StrategoControllerTest {

    @Test
    void demoTest() {
        assertEquals(3, 1+2);
    }

    @Test
    void getVersionsTest(){
        StrategoController controller = new StrategoController();
        String[] versions = new String[]{"original", "infiltrator","duel", "mini","tiny"};
        assertArrayEquals(versions,controller.getStrategoVersions());
    }

    @Test
    void getOriginalVersion(){
        StrategoController controller = new StrategoController();
        Version versionToCheck = new Version("original",PieceCount.valueOf("original".toUpperCase(Locale.ROOT)));
        assertEquals(versionToCheck,controller.getStrategoVersion("original"));
    }

    @Test
    void getInfiltratorVersion(){
        StrategoController controller = new StrategoController();
        Version versionToCheck = new Version("infiltrator",PieceCount.valueOf("infiltrator".toUpperCase(Locale.ROOT)));
        assertEquals(versionToCheck,controller.getStrategoVersion("infiltrator"));
    }

    @Test
    void getDuelVersion(){
        StrategoController controller = new StrategoController();
        Version versionToCheck = new Version("duel",PieceCount.valueOf("duel".toUpperCase(Locale.ROOT)));
        assertEquals(versionToCheck,controller.getStrategoVersion("duel"));
    }

    @Test
    void getMiniVersion(){
        StrategoController controller = new StrategoController();
        Version versionToCheck = new Version("mini",PieceCount.valueOf("mini".toUpperCase(Locale.ROOT)));
        assertEquals(versionToCheck,controller.getStrategoVersion("mini"));
    }

    @Test
    void getTinyVersion(){
        StrategoController controller = new StrategoController();
        Version versionToCheck = new Version("tiny",PieceCount.valueOf("tiny".toUpperCase(Locale.ROOT)));
        assertEquals(versionToCheck,controller.getStrategoVersion("tiny"));
    }

    List<List<String>> returnTinyConfig(){
        List<String> nullList = new ArrayList<>(Arrays.asList(null,null,null,null,null,null,null,null,null,null));
        List<String> pawnList = new ArrayList<>(Arrays.asList("marshal","flag",null,null,null,null,null,null,null,null));
        return new ArrayList<>(Arrays.asList(nullList,nullList,nullList,nullList,nullList,nullList,pawnList,nullList,nullList,nullList));
    }

    List<List<String>> returnMiniConfig(){
        List<String> nullList = new ArrayList<>(Arrays.asList(null,null,null,null,null,null,null,null,null,null));
        List<String> pawnList = new ArrayList<>(Arrays.asList("marshal","flag","infiltrator",null,null,null,null,null,null,null));
        return new ArrayList<>(Arrays.asList(nullList,nullList,nullList,nullList,nullList,nullList,pawnList,nullList,nullList,nullList));
    }



    @Test
    void joinGameTestTiny(){
        StrategoController controller = new StrategoController();
        RandomGeneratedTextTokens tokenManager = new RandomGeneratedTextTokens();
        String answer =  controller.joinGame("tiny",returnTinyConfig(),"gr25test");
        assertEquals("gr25testtiny0",tokenManager.token2gameId(answer));

    }

    @Test
    void joinGameTestMini(){
        StrategoController controller = new StrategoController();
        RandomGeneratedTextTokens tokenManager = new RandomGeneratedTextTokens();
        String answer = controller.joinGame("mini",returnMiniConfig(),"gr25test");
        assertEquals("gr25testmini0",tokenManager.token2gameId(answer));
        assertThrows(StrategoGameRuleException.class, () ->{
            controller.joinGame("original",returnMiniConfig(),"gr25test");
        });
        String answer2 =  controller.joinGame("tiny",returnTinyConfig(),"gr25test");
        assertEquals("gr25testtiny0",tokenManager.token2gameId(answer2));

        String answer3 = controller.joinGame("mini",returnMiniConfig(),"gr25test");
        assertEquals("gr25testmini0",tokenManager.token2gameId(answer3));

        String answer4 =  controller.joinGame("tiny",returnTinyConfig(),"gr25test");
        assertEquals("gr25testtiny0",tokenManager.token2gameId(answer4));
    }

}