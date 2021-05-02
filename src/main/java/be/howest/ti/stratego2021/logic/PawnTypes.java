package be.howest.ti.stratego2021.logic;

public enum PawnTypes {
    WATER(13),
    INFILTRATOR(12),
    BOMB(11),
    MARSHALL(10),
    GENERAL(9),
    COLONEL(8),
    MAJOR(7),
    CAPTAIN(6),
    LIEUTENANT(5),
    SERGEANT(4),
    MINER(3),
    SCOUT(2),
    SPY(1),
    FLAG(0),
    EMPTY(0);

    private int rank;

    PawnTypes(int rank){
        this.rank = rank;
    }

}
