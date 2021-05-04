package be.howest.ti.stratego2021.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VersionTest {

    @Test
    void checkIfVersionHasRightAmount(){
        Version original = new Version("original",PieceCount.valueOf("ORIGINAL"));
        assertEquals(1,original.getPieceCount().getGeneral());
        assertEquals(8,original.getPieceCount().getScout());
        Version tiny = new Version("original", PieceCount.valueOf("TINY"));
        assertEquals(1,tiny.getPieceCount().getMarshall());
        assertEquals(0,tiny.getPieceCount().getInfiltrator());
    }

}