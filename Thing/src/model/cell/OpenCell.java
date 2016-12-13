package model.cell;

import model.agent.Agent;
import model.agent.Harvester;
import model.states.CellState;
import sample.Settings;
import sample.Stats;

import java.util.HashSet;

/**
 * Created by Kyrre on 17.10.2016.
 */
public class OpenCell extends Cell {

    private int foodCount;
    private int apfValue;
    private HashSet<Agent> agents;
    public final Type defaultType;
    private HashSet<PheromoneColor> colors;
    private HashSet<Integer> trailIds;
    private boolean isVisited;
    private int foodSourceId;

    public enum PheromoneColor {
        DEFAULT,

        BROWN,
        YELLOW,
        AGENT;

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
        isVisited = false;
        this.foodSourceId = -1;
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
    }

    public int getFoodCount() {
        return foodCount;
    }

    public boolean placeAgent(Agent agent) {
        if (this.defaultType == Type.FOOD && !isVisited){
            Stats.getInstance().discoverFood();
        }
        isVisited = true;
        return this.agents.add(agent);
    }

    public void removeAgent(Agent agent){
        this.agents.remove(agent);
    }

    public boolean isVisited() {
        return isVisited;
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

    public boolean removeColor(PheromoneColor color){
        return colors.remove(color);
    }

    public void setFoodSourceId(int foodSourceId) {
        if (defaultType == Type.FOOD){
            this.foodSourceId = foodSourceId;
        }
    }

    public int getFoodSourceId() {
        return foodSourceId;
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
        PheromoneColor color = PheromoneColor.DEFAULT;
        Agent.AgentType agentType = Agent.AgentType.DEFAULT;
        if (!colors.isEmpty()){
            color = (PheromoneColor) colors.toArray()[0];
        }
        if (!agents.isEmpty()){
            tmp = Type.AGENT;
            color = PheromoneColor.AGENT;
            for (Agent a: agents) {
                if (agentType == Agent.AgentType.DEFAULT){
                    agentType = a.agentType;
                }else if (agentType != a.agentType){
                    agentType = Agent.AgentType.DEFAULT;
                    break;
                }
            }
            if (agents.size() == 1){
                agentType = ((Agent)agents.toArray()[0]).agentType;
            }
        }
        return new CellState(tmp,apfValue,color, agents.size(), agentType);
    }

    //NEST
    public void recruitHarvesters(int trailId, int foodAmountAtLastLocation) {
        if (type != Type.NEST){
            return;
        }
        int count = 0;
        int max = Settings.RECRUIT_SIZE;
        if (Settings.DYNAMIC_RECRUITMENT){
            max = (int) Math.ceil((double)calculateRecruitmentSize(trailId, foodAmountAtLastLocation));
        }
        for (Agent agent: agents) {
            if (agent.agentType == Agent.AgentType.HARVESTER){
                if (count < max){
                    if (((Harvester) agent).recruit(trailId)){
                        count++;
                    }
                }
                else {
                    break;
                }
            }
        }
    }


    private int calculateRecruitmentSize(int trailId, int foodAmountAtLastLocation){
        int needed = (int) Math.ceil(((double) foodAmountAtLastLocation/(double) Settings.HARVESTER_CAPACITY));
        return needed-Nest.getInstance().checkRecruitment(trailId);
    }

}