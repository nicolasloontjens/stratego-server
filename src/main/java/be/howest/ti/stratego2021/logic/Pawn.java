package be.howest.ti.stratego2021.logic;

import java.util.Locale;

public class Pawn {

    private final String playerToken;
    private final boolean isWater;
    private boolean isEmpty;
    private PawnTypes pawnType;

    public Pawn(String playerToken, boolean isWater, boolean isEmpty,String pawnType){
        this.playerToken = playerToken;
        this.isWater = isWater;
        this.isEmpty = isEmpty;
        this.pawnType = PawnTypes.valueOf(pawnType.toUpperCase(Locale.ROOT));
    }

    public boolean isWater() {
        return isWater;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
}
