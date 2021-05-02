package be.howest.ti.stratego2021.logic;

import java.util.Locale;

public class Pawn {

    private final String playerToken;
    private PawnTypes pawnType;

    public Pawn(String playerToken,String pawnType){
        this.playerToken = playerToken;
        this.pawnType = PawnTypes.valueOf(pawnType.toUpperCase(Locale.ROOT));
    }

    public boolean isWater() {
        return pawnType.name().equals("WATER");
    }

    public boolean isEmpty() {
        return pawnType.name().equals("EMPTY");
    }

    @Override
    public String toString() {
        return "" + pawnType;
    }
}
