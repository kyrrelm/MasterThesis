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

    public Map(String name, OpenCell nest, int foodCount, Cell[][] grid) {
        this.name = name;
        this.foodCount = foodCount;
        this.grid = grid;
        this.sizeX = grid.length;
        this.sizeY = grid[0].length;
        this.nest = nest;
    }
}
