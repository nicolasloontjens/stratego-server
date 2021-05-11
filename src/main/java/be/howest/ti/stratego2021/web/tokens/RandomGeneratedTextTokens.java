package be.howest.ti.stratego2021.web.tokens;

import be.howest.ti.stratego2021.web.exceptions.InvalidTokenException;
import java.util.Random;

public class RandomGeneratedTextTokens implements TokenManager{

    Random rand = new Random();

    @Override
    public String createToken(String gameId,String version, String player, int gameCount) {
        int randomplayernr = rand.nextInt(999999999) + 9999;
        return gameId + version +  gameCount + '-' + Integer.toHexString(randomplayernr) + player;
    }

    @Override
    public String token2gameId(String token) {
        return parseToken(token, 0);
    }

    @Override
    public String token2player(String token) {
        if(token.substring(token.length()-3).equals("RED")){
            return "red";
        }
        else{
            return "blue";
        }
    }

    private String parseToken(String token, int part) {
        String[] parts = token.split("-");

        if (parts.length != 2) {
            throw new InvalidTokenException();
        }
        return parts[part];
    }

}
