package model.agent;

import model.World;
import model.cell.OpenCell;

/**
 * Created by kyrrelm on 22.11.2016.
 */
public class Harvester extends Agent{

    protected Harvester(OpenCell currentCell, Heading heading, World world) {
        super(currentCell, heading, world);
    }

    @Override
    protected void behave() {

    }
}
