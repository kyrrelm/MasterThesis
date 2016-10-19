package model;

import model.Cell.Type;

/**
 * Created by kyrrelm on 19.10.2016.
 */
public class CellState {

    public final Type type;

    public CellState(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        if (type == Type.OBSTACLE)
            return "X";
        if (type == Type.NEST)
            return "N";
        if (type == Type.AGENT)
            return "A";
        if (type == Type.FREE)
            return " ";
        return "!";
    }

}
