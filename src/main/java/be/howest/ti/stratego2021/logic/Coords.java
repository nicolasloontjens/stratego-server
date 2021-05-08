package be.howest.ti.stratego2021.logic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Coords {
    private int col;
    private int row;

    @JsonCreator
    public Coords(
            @JsonProperty("row")int row,
            @JsonProperty("col")int col){
        this.col = col;
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coords coords = (Coords) o;
        return col == coords.col && row == coords.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(col, row);
    }
}
