package be.howest.ti.stratego2021.web.bridge;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JoinGamePostBody {

    private final String version;
    private final Board startConfiguration;

    @JsonCreator
    public JoinGamePostBody(
            @JsonProperty String version,
            @JsonProperty Board startConfiguration) {
        this.version = version;
        this.startConfiguration = startConfiguration;
    }

    public String getVersion() {
        return version;
    }

    public Board getStartConfiguration() {
        return startConfiguration;
    }
}
