package be.howest.ti.stratego2021.web.bridge;

import be.howest.ti.stratego2021.logic.Board;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class JoinGamePostBody {

    private final String version;
    private final List<List<String>> startConfiguration;

    @JsonCreator
    public JoinGamePostBody(
            @JsonProperty("version") String version,
            @JsonProperty("startConfiguration") List<List<String>> startConfiguration
    ) {
        this.version = version;
        this.startConfiguration = startConfiguration;
    }

    public String getVersion() {
        return version;
    }

    public List<List<String>> getStartConfiguration() {
        return startConfiguration;
    }
}
