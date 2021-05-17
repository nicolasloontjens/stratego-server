package be.howest.ti.stratego2021.web.bridge;

import be.howest.ti.stratego2021.logic.Coords;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MakeMovePosBodyTest {

    @Test
    void testGetSrc(){
        MakeMovePosBody move = new MakeMovePosBody(new Coords(4,5),new Coords(5,5),"");
        assertEquals(new Coords(4,5),move.getSrc());
    }

    @Test
    void testGetTar(){
        MakeMovePosBody move = new MakeMovePosBody(new Coords(4,5),new Coords(5,5),"");
        assertEquals(new Coords(5,5),move.getTar());
    }

    @Test
    void testGetInfiltrate(){
        MakeMovePosBody move = new MakeMovePosBody(new Coords(4,5),new Coords(5,5),"");
        MakeMovePosBody move2 = new MakeMovePosBody(new Coords(4,5),new Coords(5,5),"test");
        assertEquals("",move.getInfiltrate());
        assertEquals("test",move2.getInfiltrate());
    }

}