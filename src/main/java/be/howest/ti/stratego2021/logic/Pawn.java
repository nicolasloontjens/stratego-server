package be.howest.ti.stratego2021.logic;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;

public class Pawn implements Comparable<Pawn>{

    private final String playerToken;
    private final PawnTypes pawnType;

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

    public String getPlayerToken(){
        return playerToken;
    }

    public String getPawnType() {
        return pawnType.name().toLowerCase(Locale.ROOT);
    }

    @Override
    public String toString() {
        return "" + pawnType + " " + playerToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pawn pawn = (Pawn) o;
        return playerToken.equals(pawn.playerToken) && pawnType == pawn.pawnType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerToken, pawnType);
    }

    @Override
    public int compareTo(@NotNull Pawn other) {
        if(getPawnType().equals("spy")&&other.getPawnType().equals("marshal")){
            return 1;
        }
        if(getPawnType().equals("miner")&&other.getPawnType().equals("bomb")){
            return 1;
        }
        return this.pawnType.getRank() - other.pawnType.getRank();
    }
}
