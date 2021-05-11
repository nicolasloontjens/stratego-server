package be.howest.ti.stratego2021.logic;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.HashMap;
import java.util.Map;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PieceCount {
    ORIGINAL (6, 1, 1,2, 3, 4, 4,4,5,8,1,0,1),
    INFILTRATOR (6,1,1, 2,3,4,4,4,5,7,1,1,1),
    DUEL (2,1,1,0,0,0,0,0,2,2,1,0,1),
    MINI (0,1,0,0,0,0,0,0,0,0,0,1,1),
    TINY (0,1,0,0,0,0,0,0,0,0,0,0,1);

    private final Map<PawnTypes, Integer> counters = new HashMap<>();

    PieceCount(int bomb, int marshal, int general, int colonel, int major, int captain, int lieutenant, int sergeant,
               int miner, int scout, int spy, int infiltrator, int flag) {
        counters.put(PawnTypes.BOMB, bomb);
        counters.put(PawnTypes.MARSHAL, marshal);
        counters.put(PawnTypes.GENERAL, general);
        counters.put(PawnTypes.COLONEL, colonel);
        counters.put(PawnTypes.MAJOR, major);
        counters.put(PawnTypes.CAPTAIN, captain);
        counters.put(PawnTypes.LIEUTENANT, lieutenant);
        counters.put(PawnTypes.SERGEANT, sergeant);
        counters.put(PawnTypes.MINER, miner);
        counters.put(PawnTypes.SCOUT, scout);
        counters.put(PawnTypes.SPY, spy);
        counters.put(PawnTypes.INFILTRATOR, infiltrator);
        counters.put(PawnTypes.FLAG, flag);
    }


    public Map<PawnTypes, Integer> getCounters() {
        return counters;
    }
}
