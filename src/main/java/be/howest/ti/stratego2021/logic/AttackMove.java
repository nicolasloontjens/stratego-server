package be.howest.ti.stratego2021.logic;

public class AttackMove extends Move {
    private final Attack attack;

    public AttackMove(String player, Coords src, Coords tar, String attacker, String defender, String winner){
        super(player,src,tar);
        this.attack = new Attack(attacker,defender,winner);
    }

    public Attack getAttack() {
        return attack;
    }
}
