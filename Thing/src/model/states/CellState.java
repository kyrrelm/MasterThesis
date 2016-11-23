package model.states;

import model.agent.Agent;
import model.agent.Agent.AgentType;
import model.cell.Cell.Type;
import model.cell.OpenCell.PheromoneColor;

/**
 * Created by kyrrelm on 19.10.2016.
 */
public class CellState {

    public final Type type;
    public final int apfValue;
    private final PheromoneColor color;
    private final int agentCount;
    private final AgentType agentType;

    public CellState(Type type) {
        this.type = type;
        this.apfValue = -1;
        this.color = null;
        this.agentCount = -1;
        this.agentType = AgentType.DEFAULT;
    }
    public CellState(Type type, int apfValue, PheromoneColor color, int agentCount, AgentType agentType) {
        this.type = type;
        this.apfValue = apfValue;
        this.color = color;
        this.agentCount = agentCount;
        this.agentType = agentType;
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
            String out;
            switch (agentType){
                case SCOUT:{
                    out = "S";
                    break;
                }
                case HARVESTER:{
                    out = "H";
                    break;
                }
                default:{
                    out = "A";
                }
            }
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
