package be.howest.ti.stratego2021.web.bridge;

import java.util.Objects;

public class ReturnBoardGetBody extends ReturnBoardPawn {

    private final String rank;

    public ReturnBoardGetBody(String player, String rank){
        super(player);
        this.rank = rank;
    }

    public ReturnBoardGetBody(String player){
        super(player);
        this.rank = null;
    }

    public String getRank() {
        return rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ReturnBoardGetBody that = (ReturnBoardGetBody) o;
        return Objects.equals(rank, that.rank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rank);
    }

    @Override
    public String toString() {
        return "ReturnBoardGetBody{" +
                "rank='" + rank + '\'' +
                '}';
    }
}
