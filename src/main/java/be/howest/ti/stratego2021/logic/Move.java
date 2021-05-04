package be.howest.ti.stratego2021.logic;

public class Move {
    private final String player;
    private final Coords src;
    private final Coords tar;
    private final Attack attack;
    private final Infiltration infiltration;

    public Move(String player, Coords src, Coords tar){
        this.player = player;
        this.src = src;
        this.tar = tar;
        attack = null;
        infiltration = null;
    }
    public Move(String player, Coords src, Coords tar, String attacker, String defender, String winner){
        this.player = player;
        this.src = src;
        this.tar = tar;
        infiltration = null;
        attack = new Attack(attacker, defender, winner);
    }
    public Move(String player, Coords src, Coords tar,String guess, String actual, boolean success){
        this.player = player;
        this.src = src;
        this.tar = tar;
        attack = null;
        infiltration = new Infiltration(guess, actual, success);
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

    public Attack getAttack(){
        return attack;
    }

    public Infiltration getInfiltration(){
        return infiltration;
    }
}
