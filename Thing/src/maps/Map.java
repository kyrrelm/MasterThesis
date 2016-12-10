package maps;

import model.cell.Cell;
import model.cell.OpenCell;

/**
 * Created by Kyrre on 25.10.2016.
 */
public class Map {

    public final Cell[][] grid;
    public final int sizeX;
    public final int sizeY;
    public final OpenCell nest;
    public final String name;
    public final int foodCount;
    public final int numberOfFoodSources;
    public final int minFood;
    public final int maxFood;

    public Map(String name, OpenCell nest, int foodCount, int numberOfFoodSources,int minFood,int maxFood,Cell[][] grid) {
        this.name = name;
        this.foodCount = foodCount;
        this.grid = grid;
        this.numberOfFoodSources = numberOfFoodSources;
        this.sizeX = grid.length;
        this.sizeY = grid[0].length;
        this.minFood = minFood;
        this.maxFood = maxFood;
        this.nest = nest;
    }
}
