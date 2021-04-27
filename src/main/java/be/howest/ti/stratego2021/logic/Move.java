package be.howest.ti.stratego2021.logic;

public class Move {
    private final String player;
    private final int srccol;
    private final int srcrow;
    private final int tarcol;
    private final int tarrow;

    public Move(String player, int srccol, int srcrow, int tarcol, int tarrow){
        this.player = player;
        this.srccol = srccol;
        this.srcrow = srcrow;
        this.tarcol = tarcol;
        this.tarrow = tarrow;
    }

    public String getPlayer() {
        return player;
    }

    public int getSrccol(){
        return srccol;
    }

    public int getSrcrow() {
        return srcrow;
    }

    public int getTarcol() {
        return tarcol;
    }

    public int getTarrow() {
        return tarrow;
    }
}
