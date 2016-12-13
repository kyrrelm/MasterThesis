package sample;

import maps.Map;
import maps.MapGenerator;
import model.cell.OpenCell;
import model.cell.OpenCell.PheromoneColor;

/**
 * Created by kyrrelm on 08.11.2016.
 */
public class Settings {

    //-------------------- CONSTANTS -------------------------------
    public static final int SCOUT_CAPACITY = 1;
    public static final int FOOD_COEFFICIENT = 10;
    public static final boolean SCOUT_REMOVE_TRAIL = true;
    public static final boolean HARVESTER_REMOVE_TRAIL = true;

    //------------------------ HETEROGENEITY  ------------------------------------
    public static final int HARVESTER_CAPACITY = 5;
    public static final double HARVESTER_ENERGY_USE = 1;

    //----------------------------- SET UP ---------------------------------------

    public static final int  NUMBER_OF_TICKS = 10000;


    public static final int NUMBER_OF_SCOUTS = 30;
    public static final int NUMBER_OF_HARVESTERS = 70;

    public static final boolean USING_APF_VALUE = true;


    ////----------------- COMMON VARIABLES ---------------
    public static final boolean SCOUT_CAN_MOVE_LEFT = true;

    public static final boolean DIFFUSE_BROWN = false;

    public static boolean DYNAMIC_RECRUITMENT = true;

   //----------------------------------------------------------

    public static final boolean CONSTANT_RECRUITMENT = true;
    public static final int RECRUIT_SIZE = 10;


    //----------------- System ---------------
    public static boolean RUN_GUI = false;
    public static final boolean SHOW_ONLY_FIRST = true;
    public static final boolean SHOW_LAST = false;

    ////----------------- Map ---------------
    //public static final Map MAP = MapGenerator.APF_KILLER_FOOD;

    ////----------------- No obstacle ---------------
    /*public static final Map MAP = MapGenerator.genObstacleFreeMap("Dynamic", 30, 30,
            new OpenCell(4,4,20), new OpenCell(8,7, 20), new OpenCell(18,20, 20), new OpenCell(17,9, 20));
*/

    //-------------------------------------- 100 x 100 ------------------------------------------------

/*    public static final Map MAP = MapGenerator.genObstacleFreeMap("NoObs100x100_Food1(1000)NoMorph", 100, 100, new OpenCell(85,79, 1000));*/

    public static final Map MAP = MapGenerator.genObstacleFreeMap("NoObs100x100_Food10", 100, 100,
            new OpenCell(2,2, 100), new OpenCell(4,99, 100), new OpenCell(87,8, 100), new OpenCell(85,79, 100), new OpenCell(50,93, 100),
            new OpenCell(18,16, 100),new OpenCell(15,54, 100),new OpenCell(68,44, 100),new OpenCell(6,87, 100),new OpenCell(80,25, 100));
/*
    public static final Map MAP = MapGenerator.genObstacleFreeMap("NoObs100x100_Food10(300)", 100, 100,
            new OpenCell(2,2, 300), new OpenCell(4,99, 300), new OpenCell(87,8, 300), new OpenCell(85,79, 300), new OpenCell(50,93, 300),
            new OpenCell(18,16, 300),new OpenCell(15,54, 300),new OpenCell(68,44, 300),new OpenCell(6,87, 300),new OpenCell(80,25, 300));
*/

    //-------------------------------------- 1000 x 1000 ------------------------------------------------

/*    public static final Map MAP = MapGenerator.genObstacleFreeMap("NoObs1000x1000_Food10", 1000, 1000,
            new OpenCell(140,180, 100), new OpenCell(300,440, 100), new OpenCell(870,150, 100), new OpenCell(850,790, 100), new OpenCell(500,830, 100),
            new OpenCell(170,900, 100),new OpenCell(150,740, 100),new OpenCell(680,440, 100),new OpenCell(160,570, 100),new OpenCell(550,450, 100));*/



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

        String foodRange;
        if (MAP.maxFood == -1){
            foodRange = "N/A";
        }
        else {
            foodRange = MAP.minFood + " - " + MAP.maxFood;
        }

        String output =  "Map: "+ MAP.name
                + "\nTotal food count: " + MAP.foodCount
                + "\nNumber of food sources: " + MAP.numberOfFoodSources
                + "\nFood range: " + foodRange
                + "\nUsing pheromones: "+ !USING_APF_VALUE
                + "\nSCOUTS:"
                + "\nNumber of scouts: " + NUMBER_OF_SCOUTS
                + "\nScout capacity: "+ SCOUT_CAPACITY
                + "\nDiffuse brown: " + DIFFUSE_BROWN
                + "\nMove left: " + SCOUT_CAN_MOVE_LEFT
                + "\nHARVESTERS:"
                + "\nNumber of harvesters: "+ NUMBER_OF_HARVESTERS
                + "\nHarvester capacity: " + HARVESTER_CAPACITY
                + "\nHarvester recruit size: " + RECRUIT_SIZE
                + "\nDynamic recruitment: " + DYNAMIC_RECRUITMENT
                + "\nConstant recruitment: " + CONSTANT_RECRUITMENT;
        return output;
    }
}
