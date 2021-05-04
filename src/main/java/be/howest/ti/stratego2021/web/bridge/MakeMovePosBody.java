package be.howest.ti.stratego2021.web.bridge;

import be.howest.ti.stratego2021.logic.Coords;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MakeMovePosBody {

    private final Coords src;
    private final Coords tar;
    private final String infiltrate;

    @JsonCreator
    public MakeMovePosBody(
            @JsonProperty("src") Coords src,
            @JsonProperty("tar") Coords tar,
            @JsonProperty("infiltrate") String infiltrate) {
        this.src = src;
        this.tar = tar;
        this.infiltrate = infiltrate;
    }

    public Coords getSrc() {
        return src;
    }

    public Coords getTar() {
        return tar;
    }

    public String getInfiltrate() {
        return infiltrate;
    }
}
