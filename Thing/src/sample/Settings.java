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
    public static final int HARVESTER_CAPACITY = 100;
    public static final double HARVESTER_ENERGY_USE = 100;

    //----------------------------- SET UP ---------------------------------------

    public static final int  NUMBER_OF_TICKS = 3000000;


    public static int NUMBER_OF_SCOUTS = 100;
    public static int NUMBER_OF_HARVESTERS = 0;

    public static final boolean USING_APF_VALUE = true;


    ////----------------- COMMON VARIABLES ---------------
    public static final boolean SCOUT_CAN_MOVE_LEFT = true;

    public static final boolean DIFFUSE_BROWN = true;

    public static boolean DYNAMIC_RECRUITMENT = true;

   //----------------------------------------------------------

    public static final boolean CONSTANT_RECRUITMENT = true;
    public static final int RECRUIT_SIZE = 10;


    //----------------- System ---------------
    public static boolean RUN_GUI = false;
    public static final boolean SHOW_ONLY_FIRST = false;
    public static final boolean SHOW_LAST = false;

    ////----------------- Map ---------------
    //public static final Map MAP = MapGenerator.APF_KILLER_FOOD;

    ////----------------- No obstacle ---------------
    /*public static final Map MAP = MapGenerator.genObstacleFreeMap("Dynamic", 30, 30,
            new OpenCell(4,4,20), new OpenCell(8,7, 20), new OpenCell(18,20, 20), new OpenCell(17,9, 20));
*/

    //-------------------------------------- 100 x 100 ------------------------------------------------

/*
    public static final Map MAP = MapGenerator.genObstacleFreeMap("NoObs100x100_Food1(1000)", 100, 100, new OpenCell(75,75, 1000));
*/

/*
    public static final Map MAP = MapGenerator.genObstacleFreeMap("NoObs100x100_Food10", 100, 100,
            new OpenCell(2,2, 100), new OpenCell(4,99, 100), new OpenCell(87,8, 100), new OpenCell(85,79, 100), new OpenCell(50,93, 100),
            new OpenCell(18,16, 100),new OpenCell(15,54, 100),new OpenCell(68,44, 100),new OpenCell(6,87, 100),new OpenCell(80,25, 100));
*/

/*
    public static final Map MAP = MapGenerator.genObstacleFreeMap("NoObs100x100_Food10(300)", 100, 100,
            new OpenCell(2,2, 300), new OpenCell(4,99, 300), new OpenCell(87,8, 300), new OpenCell(85,79, 300), new OpenCell(50,93, 300),
            new OpenCell(18,16, 300),new OpenCell(15,54, 300),new OpenCell(68,44, 300),new OpenCell(6,87, 300),new OpenCell(80,25, 300));
*/

    //-------------------------------------- 1000 x 1000 ------------------------------------------------


    /*Random distribution of food, no less then 100 from the edge with a manhattan distance? of min 5 to others */

/*
    public static final Map MAP = MapGenerator.genObstacleFreeMap("NoObs1000x1000_Food1(1000)", 1000, 1000, new OpenCell(750,750, 1000));
*/

/*
    public static final Map MAP = MapGenerator.genObstacleFreeMap("NoObs1000x1000_Food10", 1000, 1000,
    new OpenCell(140,180, 100), new OpenCell(300,440, 100), new OpenCell(870,150, 100), new OpenCell(850,790, 100), new OpenCell(500,830, 100),
    new OpenCell(170,900, 100),new OpenCell(150,740, 100),new OpenCell(680,440, 100),new OpenCell(160,570, 100),new OpenCell(550,450, 100));
*/


/*
    public static final Map MAP = MapGenerator.genObstacleFreeMap("NoObs1000x1000_Food10(1000)", 1000, 1000,
            new OpenCell(140,180, 1000), new OpenCell(300,440, 1000), new OpenCell(870,150, 1000), new OpenCell(850,790, 1000), new OpenCell(500,830, 1000),
            new OpenCell(170,900, 1000),new OpenCell(150,740, 1000),new OpenCell(680,440, 1000),new OpenCell(160,570, 1000),new OpenCell(550,450, 1000));
*/


/*
    public static final Map MAP = MapGenerator.genObstacleFreeMap("NoObs1000x1000_Food100", 1000, 1000,
            new OpenCell(140,180, 100), new OpenCell(300,440, 100), new OpenCell(870,150, 100), new OpenCell(850,790, 100), new OpenCell(500,830, 100),
            new OpenCell(170,900, 100),new OpenCell(150,740, 100),new OpenCell(680,440, 100),new OpenCell(160,570, 100),new OpenCell(550,450, 100),

            new OpenCell(458,592, 100), new OpenCell(223,198, 100), new OpenCell(334,426, 100), new OpenCell(657,756, 100), new OpenCell(198,117, 100),
            new OpenCell(455,446, 100),new OpenCell(601,291, 100),new OpenCell(608,426, 100),new OpenCell(614,122, 100),new OpenCell(555,785, 100),

            new OpenCell(308,349, 100), new OpenCell(412,365, 100), new OpenCell(737,194, 100), new OpenCell(549,144, 100), new OpenCell(504,734, 100),
            new OpenCell(701,185, 100),new OpenCell(606,509, 100),new OpenCell(543,162, 100),new OpenCell(610,413, 100),new OpenCell(792,472, 100),

            new OpenCell(670,520, 100), new OpenCell(155,143, 100), new OpenCell(249,265, 100), new OpenCell(410,289, 100), new OpenCell(526,197, 100),
            new OpenCell(382,456, 100),new OpenCell(773,623, 100),new OpenCell(477,473, 100),new OpenCell(132,799, 100),new OpenCell(124,124, 100),

            new OpenCell(707,615, 100), new OpenCell(370,895, 100), new OpenCell(555,194, 100), new OpenCell(421,845, 100), new OpenCell(837,735, 100),
            new OpenCell(611,389, 100),new OpenCell(425,130, 100),new OpenCell(780,574, 100),new OpenCell(617,535, 100),new OpenCell(452,266, 100),

            new OpenCell(167,439, 100), new OpenCell(173,372, 100), new OpenCell(362,588, 100), new OpenCell(275,513, 100), new OpenCell(553,865, 100),
            new OpenCell(701,516, 100),new OpenCell(228,797, 100),new OpenCell(706,579, 100),new OpenCell(446,839, 100),new OpenCell(329,534, 100),

            new OpenCell(327,566, 100), new OpenCell(313,650, 100), new OpenCell(443,648, 100), new OpenCell(115,476, 100), new OpenCell(634,708, 100),
            new OpenCell(457,125, 100),new OpenCell(124,538, 100),new OpenCell(108,741, 100),new OpenCell(620,697, 100),new OpenCell(258,804, 100),

            new OpenCell(675,292, 100), new OpenCell(556,439, 100), new OpenCell(629,444, 100), new OpenCell(804,163, 100), new OpenCell(364,154, 100),
            new OpenCell(563,230, 100),new OpenCell(149,642, 100),new OpenCell(144,847, 100),new OpenCell(375,794, 100),new OpenCell(818,850, 100),

            new OpenCell(286,192, 100), new OpenCell(475,118, 100), new OpenCell(833,868, 100), new OpenCell(731,655, 100), new OpenCell(217,484, 100),
            new OpenCell(243,822, 100),new OpenCell(725,150, 100),new OpenCell(571,703, 100),new OpenCell(435,326, 100),new OpenCell(142,633, 100),

            new OpenCell(242,500, 100), new OpenCell(864,571, 100), new OpenCell(601,108, 100), new OpenCell(900,203, 100), new OpenCell(750,882, 100),
            new OpenCell(897,506, 100),new OpenCell(576,223, 100),new OpenCell(432,767, 100),new OpenCell(583,395, 100),new OpenCell(871,732, 100));
*/

/*
    public static final Map MAP = MapGenerator.genObstacleFreeMap("NoObs1000x1000_Food100(1000)", 1000, 1000,
            new OpenCell(140,180, 1000), new OpenCell(300,440, 1000), new OpenCell(870,150, 1000), new OpenCell(850,790, 1000), new OpenCell(500,830, 1000),
            new OpenCell(170,900, 1000),new OpenCell(150,740, 1000),new OpenCell(680,440, 1000),new OpenCell(160,570, 1000),new OpenCell(550,450, 1000),

            new OpenCell(458,592, 1000), new OpenCell(223,198, 1000), new OpenCell(334,426, 1000), new OpenCell(657,756, 1000), new OpenCell(198,117, 1000),
            new OpenCell(455,446, 1000),new OpenCell(601,291, 1000),new OpenCell(608,426, 1000),new OpenCell(614,122, 1000),new OpenCell(555,785, 1000),

            new OpenCell(308,349, 1000), new OpenCell(412,365, 1000), new OpenCell(737,194, 1000), new OpenCell(549,144, 1000), new OpenCell(504,734, 1000),
            new OpenCell(701,185, 1000),new OpenCell(606,509, 1000),new OpenCell(543,162, 1000),new OpenCell(610,413, 1000),new OpenCell(792,472, 1000),

            new OpenCell(670,520, 1000), new OpenCell(155,143, 1000), new OpenCell(249,265, 1000), new OpenCell(410,289, 1000), new OpenCell(526,197, 1000),
            new OpenCell(382,456, 1000),new OpenCell(773,623, 1000),new OpenCell(477,473, 1000),new OpenCell(132,799, 1000),new OpenCell(124,124, 1000),

            new OpenCell(707,615, 1000), new OpenCell(370,895, 1000), new OpenCell(555,194, 1000), new OpenCell(421,845, 1000), new OpenCell(837,735, 1000),
            new OpenCell(611,389, 1000),new OpenCell(425,130, 1000),new OpenCell(780,574, 1000),new OpenCell(617,535, 1000),new OpenCell(452,266, 1000),

            new OpenCell(167,439, 1000), new OpenCell(173,372, 1000), new OpenCell(362,588, 1000), new OpenCell(275,513, 1000), new OpenCell(553,865, 1000),
            new OpenCell(701,516, 1000),new OpenCell(228,797, 1000),new OpenCell(706,579, 1000),new OpenCell(446,839, 1000),new OpenCell(329,534, 1000),

            new OpenCell(327,566, 1000), new OpenCell(313,650, 1000), new OpenCell(443,648, 1000), new OpenCell(115,476, 1000), new OpenCell(634,708, 1000),
            new OpenCell(457,125, 1000),new OpenCell(124,538, 1000),new OpenCell(108,741, 1000),new OpenCell(620,697, 1000),new OpenCell(258,804, 1000),

            new OpenCell(675,292, 1000), new OpenCell(556,439, 1000), new OpenCell(629,444, 1000), new OpenCell(804,163, 1000), new OpenCell(364,154, 1000),
            new OpenCell(563,230, 1000),new OpenCell(149,642, 1000),new OpenCell(144,847, 1000),new OpenCell(375,794, 1000),new OpenCell(818,850, 1000),

            new OpenCell(286,192, 1000), new OpenCell(475,118, 1000), new OpenCell(833,868, 1000), new OpenCell(731,655, 1000), new OpenCell(217,484, 1000),
            new OpenCell(243,822, 1000),new OpenCell(725,150, 1000),new OpenCell(571,703, 1000),new OpenCell(435,326, 1000),new OpenCell(142,633, 1000),

            new OpenCell(242,500, 1000), new OpenCell(864,571, 1000), new OpenCell(601,108, 1000), new OpenCell(900,203, 1000), new OpenCell(750,882, 1000),
            new OpenCell(897,506, 1000),new OpenCell(576,223, 1000),new OpenCell(432,767, 1000),new OpenCell(583,395, 1000),new OpenCell(871,732, 1000));
*/

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
                + "\nDiffuse brown: " + DIFFUSE_BROWN
                + "\nMove left: " + SCOUT_CAN_MOVE_LEFT
                + "\nHARVESTERS:"
                + "\nNumber of harvesters: "+ NUMBER_OF_HARVESTERS
                + "\nHarvester capacity: " + HARVESTER_CAPACITY
                + "\nHarvester energy: " + HARVESTER_ENERGY_USE
                + "\nHarvester recruit size: " + RECRUIT_SIZE
                + "\nDynamic recruitment: " + DYNAMIC_RECRUITMENT
                + "\nConstant recruitment: " + CONSTANT_RECRUITMENT;
        return output;
    }
}
