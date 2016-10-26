package model.states;

import model.Cells.Cell;

/**
 * Created by kyrrelm on 19.10.2016.
 */
public class WorldState {

    public final CellState[][] cellStates;
    private final int sizeX;
    private final int sizeY;

    public WorldState(Cell[][] grid) {
        sizeX = grid.length;
        sizeY = grid[0].length;
        cellStates = new CellState[sizeX][sizeY];
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                cellStates[x][y] = grid[x][y].createCellState();
            }
        }
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
                sb.append(cellStates[x][y]);
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
