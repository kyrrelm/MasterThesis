package model.agent;

import model.World;
import model.cell.OpenCell;
import sample.Settings;

import static model.cell.Cell.*;

/**
 * Created by kyrrelm on 22.11.2016.
 */
public class Harvester extends Agent{

    public Harvester(OpenCell currentCell, Heading heading, World world) {
        super(currentCell, heading, world, AgentType.HARVESTER);
        currentCell.placeAgent(this);
        this.atHome = true;
    }

    @Override
    protected boolean behave() {
        if(atHome){
            unload();
            if (handleTrail(false)){
                atHome = false;
                return true;
            }
        }
        if (climbingTrail){
            if (climbTrail(senseAndReturnTrail(front,right,left), false)){
                return true;
            }
        }
        if (returningToNest){
            returnToNest(senseAndReturnTrail(front,right,left));
            return true;
        }
        return pickUpFood();
    }

    private boolean pickUpFood() {
        if (currentCell.getType() == Type.FOOD){
            this.load = currentCell.takeFood(Settings.HARVESTER_CAPACITY);
            this.foodAmountAtLastLocation = currentCell.getFoodCount();
            returnToNest(senseAndReturnTrail(front,right,back,left));
            return true;
        }
        return false;
    }
}
