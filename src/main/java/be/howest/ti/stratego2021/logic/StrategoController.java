package be.howest.ti.stratego2021.logic;

import be.howest.ti.stratego2021.web.StrategoWebController;

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
        // needs to be implemented (update interface first)
        return new String[]{
                "original", "infiltrator","duel", "mini","tiny"
        };
    }

    @Override
    public Version getStrategoVersion(String filter) {
        // needs to be implemented (update interface first)
        return new Version(filter,PieceCount.valueOf(filter.toUpperCase(Locale.ROOT)));
    }

    /*
    @Override
    public void joinGame(String version, Board startConfiguration) {
        // needs to be implemented (update interface first)
    }
    */
    @Override
    public void makeMove() {
        // needs to be implemented (update interface first)
    }

    @Override
    public List<Move> getMoves() {
        List<Move> res = new ArrayList<>();
        res.add(new Move("test",new Coords(1,2),new Coords(2,3)));
        return res;
    }

    @Override
    public void getGameState() {
        // needs to be implemented (update interface first)
    }
}
