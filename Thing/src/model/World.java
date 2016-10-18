package model;


import javafx.application.Platform;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.concurrent.ThreadFactory;

/**
 * Created by Kyrre on 17.10.2016.
 */
public class World {
    static int WORLD_SIZE = 16;
    private ArrayList<Agent> agents;
    private int sizeX = WORLD_SIZE;
    private int sizeY = WORLD_SIZE;
    private Border border;
    private Cell[][] grid;
    private int time;

    public Cell getCell(int x, int y) {
        if (x <= 0 || x >= sizeX || y <= 0 || y >= sizeY)
            return border;
        return grid[x][y];
    }

    public World(int time) {
        this.time = time;
        this.grid = new Cell[sizeX][sizeY];
        this.border = new Border();
        agents = new ArrayList<>();
        generateWorld();
        generateAgents(1);
        System.out.println(this);
    }

    public Task runSim(){
        Task<Long> loop = new Task<Long>() {
            @Override protected Long call() throws Exception {
                long a=0;
                long b=1;
                for (long i = 0; i < time; i++){
                    updateValue(a);
                    tick();
                }
                return a;
            }


        };
        Thread thread = new Thread(loop);
        thread.setDaemon(true);
        thread.start();

        return loop;
    }

    private void tick(){
        for (Agent a : agents) {
            a.interact();
        }
        System.out.println(this);
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
