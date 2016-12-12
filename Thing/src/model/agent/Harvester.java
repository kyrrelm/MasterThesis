package model.agent;

import model.World;
import model.cell.Cell;
import model.cell.Nest;
import model.cell.OpenCell;
import sample.Settings;
import sample.Stats;

import static model.cell.Cell.*;

/**
 * Created by kyrrelm on 22.11.2016.
 */
public class Harvester extends Agent{

    private boolean senseAndRemoveBrown;
    private boolean returningToNestWithoutTrail;

    public Harvester(OpenCell currentCell, Heading heading, World world) {
        super(currentCell, heading, world, AgentType.HARVESTER);
        currentCell.placeAgent(this);
        this.atHome = true;
        this.senseAndRemoveBrown = false;
        this.returningToNestWithoutTrail = false;
    }

    @Override
    protected boolean behave() {
        if(atHome){
            unload();
            checkIfNeed();
            if (handleTrail(false,false)){
                atHome = false;
            }else {
                goIdle();
            }
            return true;
        }
        if (climbingTrail){
            if (climbTrail(senseAndReturnTrail(front,right,left),false,false)){
                return true;
            }
        }
        if (returningToNest){
            if (!returnToNest(senseAndReturnTrail(front,right,left), Settings.HARVESTER_REMOVE_TRAIL)){
                returningToNest = false;
                returnToNestWithoutTrail();
                return true;
            }
            return true;
        }
        if (returningToNestWithoutTrail){
            returnToNestWithoutTrail();
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
            if (!returnToNest(senseAndReturnTrail(front,right,back,left), Settings.HARVESTER_REMOVE_TRAIL)){
                returnToNestWithoutTrail();
                return true;
            }
        }
        return true;
    }

    private void checkIfNeed() {
        if (foodAmountAtLastLocation == -1 || !Settings.DYNAMIC_RECRUITMENT){
            return;
        }
        int needed = (int) Math.ceil((double) foodAmountAtLastLocation/(double) Settings.HARVESTER_CAPACITY);
        int inRotation = Nest.getInstance().checkRecruitment(trailId);
        if (needed < inRotation){
            goIdle();
        }
    }

    private void goIdle() {
        Nest.getInstance().dismiss(trailId,1);
        foodAmountAtLastLocation = - 1;
        trailId = -1;
    }

    private void returnToNestWithoutTrail() {
        returningToNestWithoutTrail = true;
        foodAmountAtLastLocation = 0;
        if(goToNest()){
            returningToNestWithoutTrail = false;
            return;
        }
        OpenCell lowest = findLowest(front,right,back,left);
        moveToCell(lowest);
    }

    @Override
    protected boolean goToNest() {
        boolean atNest = super.goToNest();
        if (Settings.DYNAMIC_RECRUITMENT){
            //trailId = -1;
        }
        if (foodAmountAtLastLocation == 0){
            goIdle();
        }
        return atNest;
    }

    public boolean recruit(int id) {
        if (trailId == -1){
            trailId = id;
            Nest.getInstance().recruit(trailId, 1);
            return true;
        }
        return false;
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

    @Override
    public String toString() {
        return "TrailId: "+trailId;
    }

    @Override
    protected void move(OpenCell toCell) {
        super.move(toCell);
        Stats.getInstance().consumeEnergy(Settings.HARVESTER_ENERGY_USE);
    }
}

