package model;


import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.concurrent.ThreadFactory;

/**
 * Created by Kyrre on 17.10.2016.
 */
public class World {
    static int WORLD_SIZE = 10;
    private ArrayList<Agent> agents;
    private int sizeX = WORLD_SIZE;
    private int sizeY = WORLD_SIZE;
    private Border border;
    private Cell[][] grid;

    public Cell getCell(int x, int y) {
        if (x <= 0 || x >= sizeX || y <= 0 || y >= sizeY)
            return border;
        return grid[x][y];
    }

    public World() {
        this.grid = new Cell[sizeX][sizeY];
        this.border = new Border();
        agents = new ArrayList<>();
        generateWorld();
        generateAgents(1);
        System.out.println("World constructor:");
        System.out.println(this);
    }

    public ArrayList<WorldState> runSim(int numberOfTicks){
        ArrayList<WorldState> worldStates = new ArrayList<>();
        for (int i = 0; i < numberOfTicks; i++) {
            worldStates.add(tick());
        }
        return worldStates;
    }

    private WorldState tick(){
        agents.forEach(Agent::interact);
        System.out.println("World:");
        System.out.println(this);
        return new WorldState(grid);
    }



    private void generateWorld() {
        grid[sizeX/2][sizeY/2] = new OpenCell(sizeX/2, sizeY/2, Cell.Type.NEST);
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if (grid[x][y] == null){
                    grid[x][y] = new OpenCell(x,y, Cell.Type.FREE);
                }
            }
        }
    }

    private void generateAgents(int amount){
        agents.add(new Agent((OpenCell) grid[(sizeX/2)-1][sizeX/2], Agent.Heading.NORTH, this));
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
