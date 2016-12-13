package sample;

import utils.FileHandler;

/**
 * Created by kyrrelm on 09.11.2016.
 */
public class Stats {
    private static Stats ourInstance = new Stats();
    private int foodCount;
    private int timeOfCompletion;
    private double totalEnergyUsed ;
    private boolean done;
    private int numberOfFoodSources;
    private int foodFound;

    public static Stats getInstance() {
        return ourInstance;
    }

    private Stats() {
        this.done = false;
        this.foodCount = 0;
        this.timeOfCompletion = -1;
        this.totalEnergyUsed = 0;
        this.numberOfFoodSources = -1;
        this.foodFound = 0;
    }

    public void depositFood(int quantity, int timestep){
        this.foodCount += quantity;
        if (foodCount == Settings.MAP.foodCount){
            this.done = true;
            this.timeOfCompletion = timestep;
        }
    }

    public void discoverFood(){
        foodFound++;
    }

    public void setNumberOfFoodSource(int numberOfFoodSources){
        this.numberOfFoodSources = numberOfFoodSources;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public void print() {
        System.out.println(output());
    }

    public String output(){
        String output = "\n------------------ Info ------------------\n"+ Settings.getLog()+ "\n----------------- Result -----------------\n" + log() + "\n------------------------------------------\n";
        return output;
    }

    public String log(){
        String output = "Food retrieved: "+ Stats.getInstance().getFoodCount()
                + "\nFood sources found: "+foodFound + "/" + numberOfFoodSources
                +"\nTotal energy used: "+totalEnergyUsed
                +"\nAverage energy used: "+(double)totalEnergyUsed/((double) (Settings.NUMBER_OF_SCOUTS + Settings.NUMBER_OF_HARVESTERS))
                +"\nFood/Time ratio: "+(double)foodCount/(double)timeOfCompletion
                + "\nEnergy/Food ratio: "+ (double)totalEnergyUsed/(double)foodCount
                +"\nTime of completion: "+ timeOfCompletion;
        return output;
    }

    public void save() {
        FileHandler.writeToUniqueFile(output());
    }

    public void consumeEnergy(double energy) {
        this.totalEnergyUsed  += energy;
    }

    public boolean isDone() {
        return done;
    }
}
