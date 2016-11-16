package sample;

/**
 * Created by kyrrelm on 09.11.2016.
 */
public class Stats {
    private static Stats ourInstance = new Stats();
    private int foodCount;

    public static Stats getInstance() {
        return ourInstance;
    }

    private Stats() {
        this.foodCount = 0;
    }

    public void depositFood(int quantity){
        this.foodCount += quantity;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public void log() {
        System.out.println("--------------- Info ---------------");
        System.out.println(Settings.getLog());
        System.out.println("--------------- Result ---------------");
        System.out.println("Food retrieved: "+ Stats.getInstance().getFoodCount());
    }
}
