package model.states;

import model.Cells.Cell.Type;
import model.Cells.OpenCell.PheromoneColor;

/**
 * Created by kyrrelm on 19.10.2016.
 */
public class CellState {

    public final Type type;
    public final int apfValue;
    private final PheromoneColor color;
    private final int agentCount;

    public CellState(Type type) {
        this.type = type;
        this.apfValue = -1;
        this.color = null;
        this.agentCount = -1;
    }
    public CellState(Type type, int apfValue, PheromoneColor color, int agentCount) {
        this.type = type;
        this.apfValue = apfValue;
        this.color = color;
        this.agentCount = agentCount;
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
            String out = "A";
            if (agentCount > 1){
                out += agentCount;
            }
            return out;
        }
        if (type == Type.NEST) {
            return "N";
        }
        if (type == Type.FOOD) {
            return "F";
        }
        return "!";
    }

    public boolean hasColor() {
        return color != null;
    }

    public PheromoneColor getColor() {
        return color;
    }
}
