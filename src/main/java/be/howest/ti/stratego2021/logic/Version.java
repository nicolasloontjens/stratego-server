package be.howest.ti.stratego2021.logic;

import java.util.Map;
import java.util.Objects;

public class Version {

    private final String name;
    private final PieceCount pieceCount;

    public Version(String name, PieceCount version){
        this.name = name;
        pieceCount = version;
    }

    public String getName() {
        return name;
    }

    public Map<PawnTypes, Integer> getPieceCount() {
        return pieceCount.getCounters();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Version version = (Version) o;
        return name.equals(version.name) && pieceCount == version.pieceCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, pieceCount);
    }
}
