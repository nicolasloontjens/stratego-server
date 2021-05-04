package be.howest.ti.stratego2021.logic;

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

    public PieceCount getPieceCount() {
        return pieceCount;
    }
}
