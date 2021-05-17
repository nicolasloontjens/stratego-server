package be.howest.ti.stratego2021.web.bridge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReturnBoardPawnTest {

    @Test
    void checkGetPlayer(){
        ReturnBoardPawn pawn = new ReturnBoardPawn("blue");
        assertEquals("blue",pawn.getPlayer());
    }

}