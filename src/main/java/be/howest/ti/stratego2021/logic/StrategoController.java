package be.howest.ti.stratego2021.logic;

import be.howest.ti.stratego2021.web.StrategoWebController;
import be.howest.ti.stratego2021.web.tokens.RandomGeneratedTextTokens;
import be.howest.ti.stratego2021.web.bridge.MakeMovePosBody;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * StrategoController is the default implementation for the StrategoWebController interface,
 * however, it should NOT be aware that it is used in the context of a webserver:
 *
 * This class and all other classes in the logic-package (or future sub-packages)
 * should use 100% plain old Java Objects (POJOs). The use of Json, JsonObject or
 * Strings that contain encoded/json data should be avoided here.
 * Do not be afraid to create your own Java classes if needed.
 *
 * Note: Json and JsonObject can (and should) be used in the web-package however.
 *
 * (please update these comments in the final version)
 */
public class StrategoController implements StrategoWebController {

    private static final Logger LOGGER = Logger.getLogger(StrategoController.class.getName());

    GameManager gameManager = new GameManager();
    RandomGeneratedTextTokens tokenGen = new RandomGeneratedTextTokens();

    @Override
    public void getDemo() {
        // needs to be removed in the final version
    }

    @Override
    public void postDemo() {
        // needs to be removed in the final version
    }

    @Override
    public String[] getStrategoVersions() {
        return new String[]{
                "original", "infiltrator","duel", "mini","tiny"
        };
    }

    @Override
    public Version getStrategoVersion(String filter) {
        return new Version(filter,PieceCount.valueOf(filter.toUpperCase(Locale.ROOT)));
    }


    @Override
    public String joinGame(String version, List<List<String>> startConfiguration, String gameID){
        String currentToken = "";
        if(gameManager.checkForExistingGames(version)){
            currentToken = tokenGen.createToken(gameID,"RED",gameManager.getGamesCounter());
        }else{
            currentToken = tokenGen.createToken(gameID,"BLUE",gameManager.getGamesCounter());
        }
        gameManager.connectToGame(version,currentToken,tokenGen.token2gameId(currentToken),startConfiguration);
        return currentToken;
    }

    @Override
    public MakeMovePosBody makeMove(Coords src, Coords tar, String infiltrate) {
        return new MakeMovePosBody(src, tar, infiltrate);
    }

    @Override
    public Game getGameFromID(String gameID){
        return gameManager.getGameById(gameID);
    }

    @Override
    public boolean validateIfTokenBelongsToGame(Game game, String token){
        return gameManager.checkIfTokenBelongsToGame(game, token);
    }

    @Override
    public List<Move> getMoves() {
        List<Move> res = new ArrayList<>();
        res.add(new Move("test",new Coords(1,2),new Coords(2,3)));
        return res;
    }

    @Override
    public List<List<String>> getGameState() {
        List<List<String>> res = null;
        return res;
    }
}
