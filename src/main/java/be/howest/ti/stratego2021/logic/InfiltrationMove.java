package be.howest.ti.stratego2021.logic;

public class InfiltrationMove extends Move{
    private final Infiltration infiltration;

    public InfiltrationMove(String player, Coords src, Coords tar, String expected, String actual, boolean success) {
        super(player,src,tar);
        this.infiltration = new Infiltration(expected,actual,success);
    }

    public Infiltration getInfiltration() {
        return infiltration;
    }
}
