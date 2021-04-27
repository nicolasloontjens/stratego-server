package be.howest.ti.stratego2021.logic;

public class Infiltration extends Move{
    private final String expected;
    private final String actual;
    private final boolean success;

    public Infiltration(String player, int srccol, int srcrow, int tarcol, int tarrow, String expected, String actual, boolean success) {
        super(player, srccol, srcrow, tarcol, tarrow);
        this.expected = expected;
        this.actual = actual;
        this.success = success;
    }

    public String getExpected() {
        return expected;
    }

    public String getActual() {
        return actual;
    }

    public boolean isSuccess() {
        return success;
    }
}
