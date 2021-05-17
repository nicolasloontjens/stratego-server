package be.howest.ti.stratego2021.web.bridge;

import java.util.Objects;

public class ReturnBoardPawn {

    private final String player;

    public ReturnBoardPawn(String player){
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReturnBoardPawn that = (ReturnBoardPawn) o;
        return Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player);
    }

    @Override
    public String toString() {
        return "ReturnBoardPawn{" +
                "player='" + player + '\'' +
                '}';
    }
}
