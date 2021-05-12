package be.howest.ti.stratego2021.logic;

import be.howest.ti.stratego2021.logic.exceptions.StrategoGameRuleException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VersionTest {

    List<List<String>> returnTinyConfig(){
        List<String> nullList = new ArrayList<>(Arrays.asList(null,null,null,null,null,null,null,null,null,null));
        List<String> flagList = new ArrayList<>(Arrays.asList("flag",null,null,null,null,null,null,null,null,null));
        List<String> marshalList = new ArrayList<>(Arrays.asList(null,"marshal",null,"marshal",null,null,null,null,null,null));
        return new ArrayList<>(Arrays.asList(nullList,nullList,nullList,nullList,nullList,nullList,flagList,nullList,marshalList,nullList));
    }

    @Test
    void checkIfVersionHasRightAmount(){
        GameManager gameManager = new GameManager();
        assertThrows(StrategoGameRuleException.class, ()-> {
            gameManager.checkConfig("tiny", returnTinyConfig());
        });
    }

}