package maps;

import model.Cells.Cell;
import model.Cells.OpenCell;

/**
 * Created by Kyrre on 25.10.2016.
 */
public class Map {

    public final Cell[][] grid;
    public final int sizeX;
    public final int sizeY;
    public final OpenCell nest;

    public Map(Cell[][] grid, OpenCell nest) {
        this.grid = grid;
        this.sizeX = grid.length;
        this.sizeY = grid[0].length;
        this.nest = nest;
    }
}
