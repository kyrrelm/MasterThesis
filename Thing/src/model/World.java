package model;


import maps.Map;
import model.agent.Agent;
import model.agent.Harvester;
import model.agent.Scout;
import model.cell.Cell;
import model.cell.OpenCell;
import model.states.WorldState;
import sample.Settings;
import sample.Stats;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Kyrre on 17.10.2016.
 */
public class World {
    private ArrayList<Scout> scouts;
    private final int sizeX;
    private final int sizeY;
    private Border border;
    private Cell[][] grid;
    private OpenCell nest;
    private ArrayList<Harvester> harvester;

    private void init(){
        this.border = new Border();
        this.scouts = new ArrayList<>();
        this.harvester = new ArrayList<>();
    }

    public World(Map map) {
        init();
        this.sizeX = map.sizeX;
        this.sizeY = map.sizeY;
        this.grid = map.grid;
        this.nest = map.nest;
        generateAgents();
    }

    public World(int width, int height) {
        init();
        this.sizeX = width;
        this.sizeY = height;
        this.grid = new Cell[sizeX][sizeY];
        this.nest = new OpenCell(sizeX/2, sizeY/2, Cell.Type.NEST);
        generateDefaultMap();
        generateAgents();
    }

    public LinkedList<WorldState> runSim(int numberOfTicks){
        LinkedList<WorldState> worldStates = new LinkedList<>();
        System.out.println("Starting sim...");
        Stats.getInstance().setNumberOfFoodSource(countFoodSources());
        WorldState wState = new WorldState(grid);
        if (Settings.RUN_GUI){
            worldStates.add(wState);
        }
        for (int i = 0; i < numberOfTicks; i++) {
             wState = tick(i);
            if (Settings.RUN_GUI && !Settings.SHOW_ONLY_FIRST){
                worldStates.add(wState);
            }
            if (Stats.getInstance().isDone()){
                break;
            }
            if (i%100 == 0){
                System.out.println("Tick "+i+"/"+numberOfTicks);
            }
        }
        if (Settings.SHOW_ONLY_FIRST && Settings.SHOW_LAST){
            worldStates.add(wState);
        }
        Stats.getInstance().print();
        Stats.getInstance().save();
        return worldStates;
    }

    private WorldState tick(int i){
        scouts.forEach(agent -> agent.interact(i));
        harvester.forEach(harvester -> harvester.interact(i));
        //System.out.println("World:");
        //System.out.println(this);
        if (!Settings.RUN_GUI){
            return null;
        }
        return new WorldState(grid);
    }

    private int countFoodSources(){
        int foodCount = 0;
        for (Cell[] row: grid) {
            for (Cell c: row) {
                if (c instanceof OpenCell){
                    if (((OpenCell) c).defaultType == Cell.Type.FOOD){
                        foodCount++;
                    }
                }
            }
        }
        return foodCount;
    }

    private void generateDefaultMap() {
        grid[nest.getX()][nest.getY()] = nest;
        //grid[2][2] = new OpenCell(2, 2, Cells.AgentType.NEST);
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if (grid[x][y] == null){
                    grid[x][y] = new OpenCell(x,y, Cell.Type.FREE);
                }
            }
        }
        grid[nest.getX()+1][nest.getY()+2] = new OpenCell(nest.getX()+1, nest.getY()+2, Cell.Type.FOOD);
    }



    private void generateAgents(){
        for (int i = 0; i < Settings.NUMBER_OF_SCOUTS; i++) {
            Agent.Heading heading;
            if (i%4 == 0){
                heading = Agent.Heading.NORTH;
            }
            else if (i%4 == 1){
                heading = Agent.Heading.EAST;
            }
            else if (i%4 == 2){
                heading = Agent.Heading.SOUTH;
            }
            else {
                heading = Agent.Heading.WEST;
            }
            scouts.add(new Scout((OpenCell) grid[nest.getX()][nest.getY()], heading, this));
        }
        for (int i = 0; i < Settings.NUMBER_OF_HARVESTERS; i++) {
            harvester.add(new Harvester((OpenCell) grid[nest.getX()][nest.getY()], Agent.Heading.NORTH, this));
        }
    }

    public Cell getCell(int x, int y) {
        if (x < 0 || x >= sizeX || y < 0 || y >= sizeY)
            return border;
        return grid[x][y];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = sizeY-1; y >= 0; y--) {
            for (int x = 0; x < sizeX; x++) {
                sb.append("----");
            }
            sb.append("\n");
            for (int x = 0; x < sizeX; x++) {
                sb.append("|");
                sb.append(grid[x][y]);
            }
            sb.append("|");
            sb.append("\n");
        }
        for (int x = 0; x < sizeX; x++) {
            sb.append("-");
        }
        return sb.toString();
    }
}
