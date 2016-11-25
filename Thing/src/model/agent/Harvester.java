package model.agent;

import model.World;
import model.cell.Cell;
import model.cell.OpenCell;
import sample.Settings;

import static model.cell.Cell.*;

/**
 * Created by kyrrelm on 22.11.2016.
 */
public class Harvester extends Agent{

    private boolean senseAndRemoveBrown;

    public Harvester(OpenCell currentCell, Heading heading, World world) {
        super(currentCell, heading, world, AgentType.HARVESTER);
        currentCell.placeAgent(this);
        this.atHome = true;
        this.senseAndRemoveBrown = false;
    }

    @Override
    protected boolean behave() {
        if(atHome){
            unload();
            if (handleTrail(false,false)){
                atHome = false;
            }
            return true;
        }
        if (climbingTrail){
            if (climbTrail(senseAndReturnTrail(front,right,left),false,false)){
                return true;
            }
        }
        if (returningToNest){
            returnToNest(senseAndReturnTrail(front,right,left), Settings.HARVESTER_REMOVE_TRAIL);
            return true;
        }
        if (senseAndRemoveBrown){
            if (senseAndRemoveBrown(front,right,back,left)){
                return true;
            }
        }
        if (!pickUpFood()){
            foodAmountAtLastLocation = 0;
            if (senseAndRemoveBrown(front,right,back,left)){
                return true;
            }
            returnToNest(senseAndReturnTrail(front,right,back,left), Settings.HARVESTER_REMOVE_TRAIL);
            return true;
        }
        return true;
    }

    @Override
    protected boolean goToNest() {
        boolean atNest = super.goToNest();
        if (foodAmountAtLastLocation == 0){
            trailId = -1;
        }
        return atNest;
    }

    @Override
    protected boolean senseAndRemoveBrown(Cell... cells) {
        currentCell.removeColor(OpenCell.PheromoneColor.BROWN);
        boolean returnValue = super.senseAndRemoveBrown(cells);
        if (returnValue){
            senseAndRemoveBrown = true;
            currentCell.colorTrail(trailId);
            return true;
        }
        senseAndRemoveBrown = false;
        return false;
    }

    private boolean pickUpFood() {
        if (currentCell.getType() == Type.FOOD){
            this.load = currentCell.takeFood(Settings.HARVESTER_CAPACITY);
            this.foodAmountAtLastLocation = currentCell.getFoodCount();
            if (foodAmountAtLastLocation == 0 && senseAndRemoveBrown(front,right,left,back)){
                return true;
            }
            returnToNest(senseAndReturnTrail(front,right,back,left), Settings.HARVESTER_REMOVE_TRAIL);
            return true;
        }
        return false;
    }
}
