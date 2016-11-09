package sample;

import maps.MapGenerator;
import model.Cells.OpenCell.PheromoneColor;

/**
 * Created by kyrrelm on 08.11.2016.
 */
public class Settings {

    public static final int FOOD_COEFFICIENT = 10;

    public static final int AGENT_CAPACITY = 4;

    public static final char[][] MAP = MapGenerator.SMASA_FOOD;

    public static String giveColor(PheromoneColor color) {
        switch (color){
            case YELLOW:{
             return "-fx-background-color: yellow";
                
            }
        }
        return null;
    }
}
