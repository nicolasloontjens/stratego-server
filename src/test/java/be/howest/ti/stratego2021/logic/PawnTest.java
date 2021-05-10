package be.howest.ti.stratego2021.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PawnTest {

    @Test
    void testMinerBombLogic(){
        Pawn miner = new Pawn("blue","miner");
        Pawn scout = new Pawn("blue","scout");
        Pawn bomb = new Pawn("red","bomb");
        assertTrue(scout.compareTo(bomb)<0);
        assertEquals(1,miner.compareTo(bomb));
    }
    @Test
    void testSpyMarshalLogic(){
        Pawn spy = new Pawn("red","spy");
        Pawn marshal = new Pawn("blue","marshal");
        Pawn scout = new Pawn("blue","scout");
        assertEquals(1,spy.compareTo(marshal));
        assertTrue(spy.compareTo(scout)<0);
    }

}