package be.howest.ti.stratego2021.logic;

import be.howest.ti.stratego2021.web.StrategoWebController;
import be.howest.ti.stratego2021.web.bridge.StrategoBridge;

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
    public void getStrategoVersions() {
        // needs to be implemented (update interface first)
    }

    @Override
    public void getStrategoVersion() {
        // needs to be implemented (update interface first)
    }

    @Override
    public void joinGame() {
        // needs to be implemented (update interface first)
    }

    @Override
    public void makeMove() {
        // needs to be implemented (update interface first)
    }

    @Override
    public void getMoves() {
        // needs to be implemented (update interface first)
    }

    @Override
    public void getGameState() {
        // needs to be implemented (update interface first)
    }
}
