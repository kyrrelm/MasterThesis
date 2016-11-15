package model.Cells;

import model.Agent;
import model.states.CellState;

import java.util.HashSet;

/**
 * Created by Kyrre on 17.10.2016.
 */
public class OpenCell extends Cell {

    private int foodCount;
    private int apfValue;
    private HashSet<Agent> agents;
    private Type defaultType;
    private HashSet<PheromoneColor> colors;
    private HashSet<Integer> trailIds;

    public enum PheromoneColor {
        DEFAULT,
        BROWN,
        YELLOW;

    }


    public OpenCell(int x, int y, Type type) {
        super(x, y, type);
        apfValue = -1;
        if (type == Type.NEST){
            apfValue = 0;
        }
        agents = new HashSet<>();
        defaultType = this.type;
        foodCount = 0;
        colors = new HashSet<>();
        trailIds = new HashSet<>();
    }
    public OpenCell(int x, int y, int foodCount) {
        this(x, y, Type.FOOD);
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

    public boolean containsAgent(){
        return !agents.isEmpty();
//        if (agent == null)
//            return false;
//        return true;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public boolean placeAgent(Agent agent) {
        return this.agents.add(agent);
//        if (this.agent == null){
//            this.agent = agent;
//            return true;
//        }
//        return false;
    }

    public void removeAgent(Agent agent){
        this.agents.remove(agent);
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

    public boolean color(PheromoneColor color){
        return colors.add(color);
    }

    public void colorTrail(int id) {
        this.trailIds.add(id);
        this.colors.add(PheromoneColor.YELLOW);
    }

    public boolean containsColor(PheromoneColor color) {
        return colors.contains(color);
    }

    public int getFirstTrailId() {
        if (trailIds.isEmpty()){
            return -1;
        }
        return (int) trailIds.toArray()[0];
    }

    public boolean hasTrail(int id){
        return trailIds.contains(id);
    }

    public void removeTrail(int id) {
        trailIds.remove(id);
        if (trailIds.isEmpty()){
            colors.remove(PheromoneColor.YELLOW);
        }
    }

    @Override
    public CellState createCellState() {
        Type tmp = this.type;
        if (!this.agents.isEmpty()){
            tmp = Type.AGENT;
        }
        PheromoneColor color = PheromoneColor.DEFAULT;
        if (!colors.isEmpty()){
            color = (PheromoneColor) colors.toArray()[0];
        }
        return new CellState(tmp,apfValue,color, agents.size());
    }
}