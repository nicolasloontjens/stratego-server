package be.howest.ti.stratego2021.web.tokens;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomGeneratedTextTokensTest {

    @Test
    void testTokenGen(){
        RandomGeneratedTextTokens tokens = new RandomGeneratedTextTokens();
        String token1 = tokens.createToken("g25test","original","blue",420);
        System.out.println(tokens.token2gameId(token1));
        assertEquals("g25testoriginal420", tokens.token2gameId(token1));
    }

    @Test
    void testGetPLayer(){
        RandomGeneratedTextTokens tokens = new RandomGeneratedTextTokens();
        String token1 = tokens.createToken("g25test","original","blue",420);
        assertEquals("blue", tokens.token2player(token1));
    }

}