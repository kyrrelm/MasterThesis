package model.Cells;

import model.Agent;
import model.Cells.Cell;
import model.states.CellState;

import java.util.HashSet;

/**
 * Created by Kyrre on 17.10.2016.
 */
public class OpenCell extends Cell {

    private int foodCount;
    private int apfValue;
    private Agent agent;
    private Type defaultType;
    private HashSet<PhermoneColor> colors;

    public enum PhermoneColor {
        YELLOW;
    }

    public OpenCell(int x, int y, Type type) {
        super(x, y, type);
        apfValue = -1;
        if (type == Type.NEST){
            apfValue = 0;
        }
        agent = null;
        defaultType = this.type;
        foodCount = 0;
        colors = new HashSet<>();
    }

    public OpenCell(int x, int y, Type type, int foodCount) {
        this(x, y, type);
        this.foodCount = foodCount;
    }

    public int getApfValue() {
        return apfValue;
    }

    public void setApfValue(int apfValue) {
        if (hasApfValue()){
            this.apfValue = Math.min(this.apfValue, apfValue);
        }
        else {
            this.apfValue = apfValue;
        }
    }

    public boolean containAgent(){
        if (agent == null)
            return false;
        return true;
    }

    public boolean placeAgent(Agent agent) {
        if (this.agent == null){
            this.agent = agent;
            this.type = Type.AGENT;
            return true;
        }
        return false;
    }

    public void removeAgent(){
        this.type = defaultType;
        this.agent = null;
    }



    @Override
    public String toString() {
        if (apfValue == -1 || type == Type.NEST)
            return super.toString();
        return String.valueOf(apfValue);
    }

    public boolean hasApfValue() {
        if (apfValue == -1)
            return false;
        return true;
    }

    public boolean hasFood(){
        return foodCount > 0;
    }

    public int takeFood(int amount){
        int tmp = Math.min(amount, foodCount);
        foodCount = Math.max(foodCount-amount, 0);
        if (foodCount == 0){
            type = Type.FREE;
        }
        return tmp;
    }

    public void color(PhermoneColor color){
        this.colors.add(color);
    }


    @Override
    public CellState createCellState() {
        return new CellState(type,apfValue,foodCount);
    }
}
