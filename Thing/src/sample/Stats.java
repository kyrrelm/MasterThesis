package sample;

/**
 * Created by kyrrelm on 09.11.2016.
 */
public class Stats {
    private static Stats ourInstance = new Stats();
    private int foodCount;
    private int timeOfCompletion;

    public static Stats getInstance() {
        return ourInstance;
    }

    private Stats() {
        this.foodCount = 0;
        this.timeOfCompletion = -1;
    }

    public void depositFood(int quantity, int timestep){
        this.foodCount += quantity;
        if (foodCount == Settings.MAP.foodCount){
            this.timeOfCompletion = timestep;
        }
    }

    public int getFoodCount() {
        return foodCount;
    }

    public void print() {
        System.out.println("--------------- Info ---------------");
        System.out.println(Settings.getLog());
        System.out.println("--------------- Result ---------------");
        System.out.println(log());

    }

    public String log(){
        String output = "Food retrieved: "+ Stats.getInstance().getFoodCount()+"\nTime of completion: "+ timeOfCompletion;
        return output;
    }
}
