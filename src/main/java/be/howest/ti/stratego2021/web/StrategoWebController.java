package be.howest.ti.stratego2021.web;

import be.howest.ti.stratego2021.logic.Move;
import be.howest.ti.stratego2021.logic.Version;

import java.util.List;

/**
 * StrategoWebController defines the boundary between the HTTP/Json webserver
 * and the actual implementation of the stratego game in Java.
 *
 * It is implemented as a 1 on 1 mapping to the endpoints of the API.
 * It is your job to update this interface:
 *  - remove all demo-methods
 *  - update all return-types and add (if needed) the expected arguments
 *    to the remaining methods.
 *
 *  IMPORTANT: When adding return-types and arguments you should NOT use Json, JsonObject, or
 *  Strings that contain json. Do not be afraid to create your own Java classes if needed.
 *
 *  (please update/remove these comments in the final version)
 */
public interface StrategoWebController {

    void getDemo();
    void postDemo();

    String[] getStrategoVersions();
    Version getStrategoVersion(String filter);

    String joinGame(String version, List<List<String>> startConfiguration, String gameID);

    void makeMove();
    List<Move> getMoves();

    void getGameState();
}
