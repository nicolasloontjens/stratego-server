package be.howest.ti.stratego2021.logic;

import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

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
        List<List<String>> res = new ArrayList<>();
        List<String> nullList = Collections.nCopies(10,null);
        List<String> pawnList = Collections.nCopies(10,"colonel");
        res.addAll(Collections.nCopies(6,nullList));
        res.addAll(Collections.nCopies(4,pawnList));
        board1.postBlueConfig(res);
        board1.getBoard().forEach(System.out::println);
    }

}