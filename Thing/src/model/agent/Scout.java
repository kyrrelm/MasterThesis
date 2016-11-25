package model.agent;

import model.World;
import model.cell.Cell;
import model.cell.OpenCell;
import sample.Settings;

import static model.cell.OpenCell.*;

/**
 * Created by Kyrre on 17.10.2016.
 */
public class Scout extends Agent{

    private static int idGenTrail = 0;
    private boolean followingBrown;
    private boolean recruitHarvesters;

    private static int genIdTrail(){
        return idGenTrail++;
    }

    private boolean returnAndColor;

    public Scout(OpenCell currentCell, Heading heading, World world) {
        super(currentCell, heading, world, AgentType.SCOUT);
        this.returnAndColor = false;
        this.followingBrown = false;
        this.recruitHarvesters = false;
    }

    protected boolean behave() {
        updateValue(front,right,back,left);
        if (recruitHarvesters){
            recruitHarvesters();
        }
        if(atHome){
            unload();
            atHome = false;
            if (handleTrail(Settings.SCOUT_REMOVE_TRAIL,true)){
                return true;
            }
        }
        if (followingBrown){
            if (followBrown()){
                return true;
            }
        }
        if (climbingTrail){
            if (climbTrail(senseAndReturnTrail(front,right,left), Settings.SCOUT_REMOVE_TRAIL, true)){
                return true;
            }
        }
        if (avoidingObstacle){
            avoidObstacleSmasa();
            return true;
        }
        if (returnAndColor){
            returnAndColor();
            return true;
        }
        if (returningToNest){
            returnToNest(senseAndReturnTrail(front,right,left), false);
            return true;
        }
        if (lookForFood()){
            return true;
        }


        if (currentCell.containsColor(PheromoneColor.BROWN)){
            if (right instanceof OpenCell){
                if (((OpenCell) right).containsColor(PheromoneColor.BROWN)){
                    diffuseBrown(left);
                    rotateRight();
                    followBrown();
                    return true;
                }
                else {
                    currentCell.removeColor(PheromoneColor.BROWN);
                }
            }else {
                currentCell.removeColor(PheromoneColor.BROWN);

            }
        }

        if (right instanceof OpenCell && !((OpenCell) right).hasApfValue()){
            rotateRight();
            move((OpenCell) front);
            return true;
        }
        if (front instanceof OpenCell){
//            if (((OpenCell) front).hasApfValue() && left instanceof OpenCell && !((OpenCell) left).hasApfValue()){
//                rotateLeft();
//                move((OpenCell) front);
//            }
            move((OpenCell) front);
            return true;
        }
        return false;
    }


    private boolean followBrown() {
        followingBrown = true;
        if (front instanceof OpenCell){
            if(front.getType() == Type.FOOD){
                move((OpenCell) front);
                followingBrown = false;
                return true;
            }
            else if (((OpenCell) front).containsColor(PheromoneColor.BROWN)){
                move((OpenCell) front);
                return true;
            }
        }
        followingBrown = false;
        return false;
        //TODO: If food disappears on the way
    }


    /**
     * Re-writing this to sense neigbours breaks trail stuff
     * @return
     */
    private boolean lookForFood() {
        if (currentCell.getType() == Type.FOOD){
            if (!currentCell.containsColor(PheromoneColor.BROWN)){
                diffuseBrown(currentCell, left);
            }
            this.load = currentCell.takeFood(Settings.SCOUT_CAPACITY);
            this.foodAmountAtLastLocation = currentCell.getFoodCount();
            trailId = currentCell.getFirstTrailId();
            OpenCell existingTrail = senseAndReturnTrail(front,right,back,left);
            if (existingTrail == null){
                returnAndColor();
            }
            else {
                returnToNest(existingTrail, false);
            }
            return true;
        }
        return false;
    }

    private void diffuseBrown(Cell... cells) {
        for (Cell c: cells) {
            if (c instanceof OpenCell){
                ((OpenCell) c).color(PheromoneColor.BROWN);
            }
        }
    }

    private void returnAndColor() {
        if (!returnAndColor){
            trailId = genIdTrail();
            currentCell.colorTrail(trailId);
        }
        returnAndColor = true;
        if(goToNest()){
            recruitHarvesters = true;
            returnAndColor = false;
            return;
        }
        OpenCell lowest = findLowest(front,right,back,left);
        moveToCell(lowest);
        lowest.colorTrail(trailId);
    }

    private void recruitHarvesters() {
        recruitHarvesters = false;
        if (currentCell.getType() == Type.NEST){
            currentCell.recruitHarvesters(trailId, Settings.RECRUIT_SIZE);
        }
    }

    @Override
    protected void move(OpenCell toCell) {
        super.move(toCell);
        updateValue(front,right,back,left);
    }
}