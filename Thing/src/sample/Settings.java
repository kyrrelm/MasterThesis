package sample;

import maps.Map;
import maps.MapGenerator;
import model.Cells.OpenCell.PheromoneColor;

/**
 * Created by kyrrelm on 08.11.2016.
 */
public class Settings {

    public static final int FOOD_COEFFICIENT = 10;

    public static final int AGENT_CAPACITY = 4;

    public static final int NUMBER_OF_AGENTS = 10;

    public static final boolean USING_APF_VALUE = true;

    public static final Map MAP = MapGenerator.APF_KILLER_FOOD;

    public static String giveColor(PheromoneColor color) {
        switch (color){
            case YELLOW:{
             return "-fx-background-color: yellow;" +
                     "-fx-border-color: lightgray;";
            }
            case BROWN:{
                return "-fx-background-color: sandybrown;" +
                        "-fx-border-color: lightgray;";
            }
            case AGENT:{
                return "-fx-background-color: lightblue;" +
                        "-fx-border-color: lightgray;";
            }
            case DEFAULT:{
                return "-fx-background-color: white;" +
                        "-fx-border-color: lightgray;";
            }
        }
        return null;
    }

    public static String getLog() {
        String output =  "Map: "+ MAP.name + "\nTotal food count: " + MAP.foodCount
                +"\nFood range: " + FOOD_COEFFICIENT + " - " + FOOD_COEFFICIENT*9 + "\nNumber of agents: "
                + NUMBER_OF_AGENTS +"\nAgent capacity: "+ AGENT_CAPACITY + "\nUsing pheromones: "+ !USING_APF_VALUE;
        return output;
    }
}
