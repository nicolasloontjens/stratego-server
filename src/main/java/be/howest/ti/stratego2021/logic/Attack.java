package be.howest.ti.stratego2021.logic;

public class Attack extends Move{
    private final String attacker;
    private final String defender;
    private final String winner;

    public Attack(String player, int srccol, int srcrow, int tarcol, int tarrow, String attacker, String defender, String winner) {
        super(player, srccol, srcrow, tarcol, tarrow);
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
