package be.howest.ti.stratego2021.web.bridge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReturnBoardGetBodyTest {

    @Test
    void testGetPlayer(){
        ReturnBoardGetBody pawn = new ReturnBoardGetBody("blue","infiltrator");
        assertEquals("blue",pawn.getPlayer());
    }

    @Test
    void testGetRank(){
        ReturnBoardGetBody pawn = new ReturnBoardGetBody("blue","infiltrator");
        assertEquals("infiltrator",pawn.getRank());
    }

}