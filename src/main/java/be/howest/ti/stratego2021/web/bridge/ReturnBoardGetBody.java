package be.howest.ti.stratego2021.web.bridge;

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
}
