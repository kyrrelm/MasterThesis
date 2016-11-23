package model.agent;

import model.World;
import model.cell.OpenCell;

/**
 * Created by kyrrelm on 22.11.2016.
 */
public class Harvester extends Agent{

    public Harvester(OpenCell currentCell, Heading heading, World world) {
        super(currentCell, heading, world, AgentType.HARVESTER);
        this.atHome = true;
    }

    @Override
    protected boolean behave() {
        if(atHome){
            unload();
            if (handleTrail()){
                atHome = false;
                return true;
            }
        }
        if (climbingTrail){
            if (climbTrail(senseAndReturnTrail(front,right,left))){
                return true;
            }
        }
        return false;
    }
}
