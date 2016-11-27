package sample;

import maps.Map;
import maps.MapGenerator;
import model.cell.OpenCell.PheromoneColor;

/**
 * Created by kyrrelm on 08.11.2016.
 */
public class Settings {

    public static final int FOOD_COEFFICIENT = 10;

    public static final int SCOUT_CAPACITY = 4;
    public static final int HARVESTER_CAPACITY = 20;


    public static final int NUMBER_OF_SCOUTS = 8;
    public static final int NUMBER_OF_HARVESTERS = 2;

    public static final int RECRUIT_SIZE = 1;
    public static final boolean CONSTANT_RECRUITMENT = false;
    public static final boolean DIFFUSE_BROWN = false;

    public static final boolean USING_APF_VALUE = true;


    public static final Map MAP = MapGenerator.APF_KILLER_FOOD;
    //----------------- Heterogeneous ---------------
    public static final boolean SCOUT_REMOVE_TRAIL = true;
    public static final boolean HARVESTER_REMOVE_TRAIL = true;


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
                +"\nFood range: " + FOOD_COEFFICIENT + " - " + FOOD_COEFFICIENT*9
                + "\nNumber of scouts: " + NUMBER_OF_SCOUTS
                +"\nScout capacity: "+ SCOUT_CAPACITY
                + "\nNumber of harvesters: "+ NUMBER_OF_HARVESTERS
                + "\nHarvester capacity: " + HARVESTER_CAPACITY
                + "\nHarvester recruit size: " + RECRUIT_SIZE
                + "\nUsing pheromones: "+ !USING_APF_VALUE;
        return output;
    }
}
