package be.howest.ti.stratego2021.logic;

public class Move {
    private final String player;
    private final Coords src;
    private final Coords tar;

    public Move(String player, Coords src, Coords tar){
        this.player = player;
        this.src = src;
        this.tar = tar;
    }

    public String getPlayer() {
        return player;
    }

    public Coords getSrc(){
        return src;
    }

    public Coords getTar(){
        return tar;
    }

    

}
