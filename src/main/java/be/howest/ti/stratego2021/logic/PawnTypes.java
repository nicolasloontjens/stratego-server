package be.howest.ti.stratego2021.logic;

import java.util.Locale;

public enum PawnTypes {
    WATER(13),
    BOMB(11),
    MARSHAL(10),
    GENERAL(9),
    COLONEL(8),
    MAJOR(7),
    CAPTAIN(6),
    LIEUTENANT(5),
    SERGEANT(4),
    MINER(3),
    SCOUT(2),
    INFILTRATOR(1),
    SPY(1),
    FLAG(0),
    EMPTY(0);

    private final int rank;

    PawnTypes(int rank){
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
