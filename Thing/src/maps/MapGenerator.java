package maps;

import model.Cells.Cell;
import model.Cells.OpenCell;

/**
 * Created by Kyrre on 25.10.2016.
 */
public class MapGenerator {

    public static final char[][] OBSTACLE_TEST = new char[][]{
            {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
            {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','X',' ',' ',' ',' '},
            {' ',' ',' ',' ',' ','X','X','X','X',' ',' ','X',' ',' ',' ',' '},
            {' ',' ',' ',' ',' ','X','X',' ','X',' ',' ','X',' ',' ',' ',' '},
            {' ',' ',' ','X',' ','X',' ',' ','X',' ',' ','X',' ',' ',' ',' '},
            {' ',' ',' ',' ',' ','X',' ',' ',' ',' ','X','X',' ',' ',' ',' '},
            {' ',' ',' ','X',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
            {' ',' ','X','X',' ',' ','N',' ',' ',' ',' ',' ',' ',' ',' ',' '},
            {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
            {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
            {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','X','X',' ',' ',' ',' '},
            {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','X',' ',' ',' ',' ',' '},
            {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','X',' ',' ',' ',' ',' '},
            {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','X',' ',' ',' ',' ',' '},
            {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','X',' ',' ',' ',' ',' '},
            {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','X',' ',' ',' ',' ',' '},
            {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','X',' ',' ',' ',' ',' '},
            {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','X',' ',' ',' ',' ',' '}
    };


    public static Map genMap(char[][] map) {
        Cell[][] outputMap = new Cell[map[0].length][map.length];
        OpenCell nest = null;
        for (int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                char value = map[map.length-1-y][x];
                if (value == 'N'){
                    nest = new OpenCell(x,y, Cell.Type.NEST);
                    outputMap[x][y] = nest;
                }
                else if (value == 'X'){
                    outputMap[x][y] = new Cell(x,y, Cell.Type.OBSTACLE);
                }
                else {
                    outputMap[x][y] = new OpenCell(x,y, Cell.Type.FREE);
                }
            }
        }
        return new Map(outputMap, nest);
    }
}
