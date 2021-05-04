package be.howest.ti.stratego2021.logic;

import java.util.Comparator;

public class ComparePawns implements Comparator<attackerRank, defenderRank>{
    public PawnTypes attackerRank;
    public PawnTypes defenderRank;


    @Override
    public int compare(attackerRank o1, attackerRank o2) {
        return 0;
    }
}

