package be.howest.ti.stratego2021.logic;

public class Attack extends Move {
    private final String attacker;
    private final String defender;
    private final String winner;

    public Attack(String player, Coords src, Coords tar, String attacker, String defender, String winner){
        super(player,src,tar);
        this.attacker = attacker;
        this.defender = defender;
        this.winner = winner;
    }

    public String getAttacker() {
        return attacker;
    }

    public String getDefender() {
        return defender;
    }

    public String getWinner() {
        return winner;
    }
}
