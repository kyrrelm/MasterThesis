package sample;

import javafx.concurrent.Task;
import model.World;
import model.states.WorldState;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by kyrrelm on 19.10.2016.
 */
public class Simulator extends Task<LinkedList<WorldState>> {

    ArrayList<WorldState> worldStates;
    World world;
    private final int numberOfTicks;

    public Simulator(World world, int numberOfTicks) {
        this.world = world;
        this.numberOfTicks = numberOfTicks;
    }

    @Override
    protected LinkedList<WorldState> call() throws Exception {
        //return world.runSim(numberOfTicks);
        world.runSim10(); return null;
    }
}
