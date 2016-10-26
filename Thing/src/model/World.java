package model;


import maps.Map;
import maps.MapGenerator;
import model.Cells.Cell;
import model.Cells.OpenCell;
import model.states.WorldState;

import java.util.ArrayList;

/**
 * Created by Kyrre on 17.10.2016.
 */
public class World {
    private ArrayList<Agent> agents;
    private final int sizeX;
    private final int sizeY;
    private Border border;
    private Cell[][] grid;
    private OpenCell nest;

    private void init(){
        this.border = new Border();
        this.agents = new ArrayList<>();
    }

    public World(Map map) {
        init();
        this.sizeX = map.sizeX;
        this.sizeY = map.sizeY;
        this.grid = map.grid;
        this.nest = map.nest;
        generateAgents(1);
    }

    public World(int width, int height) {
        init();
        this.sizeX = width;
        this.sizeY = height;
        this.grid = new Cell[sizeX][sizeY];
        this.nest = new OpenCell(sizeX/2, sizeY/2, Cell.Type.NEST);
        generateDefaultMap();
        generateAgents(1);
    }

    public ArrayList<WorldState> runSim(int numberOfTicks){
        ArrayList<WorldState> worldStates = new ArrayList<>();
        worldStates.add(new WorldState(grid));
        for (int i = 0; i < numberOfTicks; i++) {
            worldStates.add(tick());
        }
        return worldStates;
    }

    private WorldState tick(){
        agents.forEach(Agent::interact);
        //System.out.println("World:");
        //System.out.println(this);
        return new WorldState(grid);
    }

    private void generateDefaultMap() {
        grid[nest.getX()][nest.getY()] = nest;
        //grid[2][2] = new OpenCell(2, 2, Cells.Type.NEST);
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if (grid[x][y] == null){
                    grid[x][y] = new OpenCell(x,y, Cell.Type.FREE);
                }
            }
        }
        grid[nest.getX()+1][nest.getY()+2] = new OpenCell(nest.getX()+1, nest.getY()+2, Cell.Type.FOOD);
    }



    private void generateAgents(int amount){
        agents.add(new Agent((OpenCell) grid[nest.getX()][nest.getY()], Agent.Heading.NORTH, this));
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
