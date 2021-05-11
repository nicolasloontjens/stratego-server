package be.howest.ti.stratego2021.logic;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PieceCount {
    ORIGINAL (6, 1, 1,2, 3, 4, 4,4,5,8,1,0,1),
    INFILTRATOR (6,1,1, 2,3,4,4,4,5,7,1,1,1),
    DUEL (2,1,1,0,0,0,0,0,2,2,1,0,1),
    MINI (0,1,0,0,0,0,0,0,0,0,0,1,1),
    TINY (0,1,0,0,0,0,0,0,0,0,0,0,1);

    private final int bomb;
    private final int marshal;
    private final int general;
    private final int colonel;
    private final int major;
    private final int captain;
    private final int lieutenant;
    private final int sergeant;
    private final int miner;
    private final int scout;
    private final int spy;
    private final int infiltrator;
    private final int flag;

    PieceCount(int bomb, int marshal, int general, int colonel, int major, int captain, int lieutenant, int sergeant,
               int miner, int scout, int spy, int infiltrator, int flag) {
        this.bomb = bomb;
        this.marshal = marshal;
        this.general = general;
        this.colonel = colonel;
        this.major = major;
        this.captain = captain;
        this.lieutenant = lieutenant;
        this.sergeant = sergeant;
        this.miner = miner;
        this.scout = scout;
        this.spy = spy;
        this.infiltrator = infiltrator;
        this.flag = flag;
    }

    public int getBomb() {
        return bomb;
    }

    public int getMarshal() {
        return marshal;
    }

    public int getGeneral() {
        return general;
    }

    public int getColonel() {
        return colonel;
    }

    public int getMajor() {
        return major;
    }

    public int getCaptain() {
        return captain;
    }

    public int getLieutenant() {
        return lieutenant;
    }

    public int getSergeant() {
        return sergeant;
    }

    public int getMiner() {
        return miner;
    }

    public int getScout() {
        return scout;
    }

    public int getSpy() {
        return spy;
    }

    public int getInfiltrator() {
        return infiltrator;
    }

    public int getFlag() {
        return flag;
    }
}
