package be.howest.ti.stratego2021.web.bridge;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JoinGamePostBodyTest {

    List<List<String>> returnConfig(){
        List<String> nullList = new ArrayList<>(Arrays.asList(null,null,null,null,null,null,null,null,null,null));
        List<String> pawnList = new ArrayList<>(Arrays.asList("flag","bomb","colonel","colonel","scout","colonel","colonel","colonel","colonel","infiltrator"));
        return new ArrayList<>(Arrays.asList(nullList,nullList,nullList,nullList,nullList,nullList,pawnList,pawnList,pawnList,pawnList));
    }

    @Test
    void testGetConfig(){
        JoinGamePostBody postBody = new JoinGamePostBody("original",returnConfig());
        assertEquals(returnConfig(),postBody.getStartConfiguration());
    }

    @Test
    void testGetVersion(){
        JoinGamePostBody postBody = new JoinGamePostBody("original",returnConfig());
        assertEquals("original", postBody.getVersion());
    }

}