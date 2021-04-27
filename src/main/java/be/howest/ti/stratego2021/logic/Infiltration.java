package be.howest.ti.stratego2021.logic;

public class Infiltration{
    private final String expected;
    private final String actual;
    private final boolean success;

    public Infiltration(String expected, String actual, boolean success) {
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
