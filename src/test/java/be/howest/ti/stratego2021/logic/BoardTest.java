package be.howest.ti.stratego2021.logic;

import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

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
    void testBoardConstructor(){
        Board board1 = new Board();
        board1.getBoard().forEach(System.out::println);
        assertTrue(board1.getBoard().get(4).get(3).isWater());
        assertTrue(board1.getBoard().get(5).get(1).isEmpty());
    }

    @Test
    void testPostBlueConfig(){
        Board board1 = new Board();
        board1.postBlueConfig(returnBlueConfig());
        board1.getBoard().forEach(System.out::println);
    }

    @Test
    void testPostRedConfig(){
        Board board1 = new Board();
        board1.postBlueConfig(returnBlueConfig());
        board1.postRedConfig(returnRedConfig());
        board1.getBoard().forEach(System.out::println);
    }

}