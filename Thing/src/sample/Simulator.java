package sample;

import javafx.concurrent.Task;
import model.World;
import model.WorldState;

import java.util.ArrayList;

/**
 * Created by kyrrelm on 19.10.2016.
 */
public class Simulator extends Task<ArrayList<WorldState>> {

    ArrayList<WorldState> worldStates;
    World world;

    public Simulator(World world) {
        this.world = world;
    }

    @Override
    protected ArrayList<WorldState> call() throws Exception {
        return world.runSim(1);
    }
}
