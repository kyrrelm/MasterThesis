package model.states;

import model.Cells.Cell.Type;

/**
 * Created by kyrrelm on 19.10.2016.
 */
public class CellState {

    public final Type type;
    public final int apfValue;

    public CellState(Type type) {
        this.type = type;
        this.apfValue = -1;
    }
    public CellState(Type type, int apfValue, int foodCount) {
        this.type = type;
        this.apfValue = apfValue;
    }

    @Override
    public String toString() {
        if (type == Type.FREE){
            if (apfValue != -1)
                return String.valueOf(apfValue);
            return " ";
        }
        if (type == Type.FOOD){
            return "F";
        }
        if (type == Type.OBSTACLE) {
            return "X";
        }
        if (type == Type.AGENT) {
            return "A";
        }
        if (type == Type.NEST) {
            return "N";
        }
        if (type == Type.FOOD) {
            return "F";
        }
        return "!";
    }

}
