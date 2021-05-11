package be.howest.ti.stratego2021.logic;

import java.util.Map;

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
    public String toString() {
        return "Version{" +
                "name='" + name + '\'' +
                ", pieceCount=" + pieceCount +
                '}';
    }
}
