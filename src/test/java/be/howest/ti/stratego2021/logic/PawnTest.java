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
    @Test
    void testDrawLogic(){
        Pawn scout = new Pawn("blue","scout");
        Pawn scoutEnemy = new Pawn("red","scout");
        assertEquals(0,scout.compareTo(scoutEnemy));
    }
    @Test
    void testNormalRankingLogic(){
        Pawn major = new Pawn("blue","major");
        Pawn scout = new Pawn("red","scout");
        assertTrue(major.compareTo(scout)>0);
        assertTrue(scout.compareTo(major)<0);
    }

    @Test
    void testEquals(){
        Pawn pawn1 = new Pawn("blue","infiltrator");
        Pawn pawn2 = new Pawn("blue","infiltrator");
        assertEquals(pawn1, pawn2);
    }

}