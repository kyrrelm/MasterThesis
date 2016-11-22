package model.agent;

import model.World;
import model.cell.Cell;
import model.cell.Cell.Type;
import model.cell.OpenCell;
import sample.Settings;

import static model.cell.OpenCell.*;

/**
 * Created by Kyrre on 17.10.2016.
 */
public class Scout extends Agent{

    private static int idGenTrail = 0;
    private boolean followingBrown;

    private static int genIdTrail(){
        return idGenTrail++;
    }

    private boolean returningToNest;
    private boolean returnAndColor;
    private boolean atHome;

    public Scout(OpenCell currentCell, Heading heading, World world) {
        super(currentCell, heading, world);
        this.returnAndColor = false;
        this.atHome = false;
        this.returningToNest = false;
        this.followingBrown = false;
    }

    protected void behave() {
        updateValue(front,right,back,left);
        if(atHome){
            unload();
            atHome = false;
            if (handleTrail()){
                return;
            }
        }
        if (followingBrown){
            if (followBrown()){
                return;
            }
        }
        if (climbingTrail){
            if (climbTrail(senseAndReturnTrail(front,right,left))){
                return;
            }
        }
        if (avoidingObstacle){
            avoidObstacleSmasa();
            return;
        }
        if (returnAndColor){
            returnAndColor();
            return;
        }
        if (returningToNest){
            returnToNest(senseAndReturnTrail(front,right,left));
            return;
        }


        if (lookForFood()){
            return;
        }


        if (currentCell.containsColor(PheromoneColor.BROWN)){
            if (right instanceof OpenCell){
                if (((OpenCell) right).containsColor(PheromoneColor.BROWN)){
                    diffuseBrown(left);
                    rotateRight();
                    followBrown();
                    return;
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
            return;
        }
        if (front instanceof OpenCell){
            move((OpenCell) front);
            return;
        }
        if (front.getType() == Type.OBSTACLE){
            avoidObstacleSmasa();
        }
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
     * Re-writing this to snse neigbours breaks trail stuff
     * @return
     */
    private boolean lookForFood() {
        if (currentCell.getType() == Type.FOOD){
            if (!currentCell.containsColor(PheromoneColor.BROWN)){
                diffuseBrown(currentCell, left);
            }
            this.load = currentCell.takeFood(Settings.AGENT_CAPACITY);
            this.foodAmountAtLastLocation = currentCell.getFoodCount();
            trailId = currentCell.getFirstTrailId();
            OpenCell existingTrail = senseAndReturnTrail(front,right,back,left);
            if (existingTrail == null){
                returnAndColor();
            }
            else {
                returnToNest(existingTrail);
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

    private void returnToNest(OpenCell existingTrail) {
        returningToNest = true;
        if (existingTrail != null){
            moveToCell(existingTrail);
            return;
        }
        if (goToNest()){
            returningToNest = false;
            return;
        }
        System.out.println("Something wrong in returnToNest");
    }

    private void returnAndColor() {
        if (!returnAndColor){
            trailId = genIdTrail();
            currentCell.colorTrail(trailId);
        }
        returnAndColor = true;
        if(goToNest()){
            returnAndColor = false;
            return;
        }
        OpenCell lowest = findLowest(front,right,back,left);
        moveToCell(lowest);
        lowest.colorTrail(trailId);
    }

    private boolean goToNest() {
        if (front.getType() == Type.NEST){
            move((OpenCell) front);
            atHome = true;
            return true;
        }
        if (left.getType() == Type.NEST){
            rotateLeft();
            move((OpenCell) front);
            atHome = true;
            return true;
        }
        if (right.getType() == Type.NEST){
            rotateRight();
            move((OpenCell) front);
            atHome = true;
            return true;
        }
        return false;
    }

    private OpenCell findLowest(Cell... cells) {
        OpenCell lowest = null;
        for (Cell c: cells){
            if (c instanceof OpenCell && ((OpenCell) c).hasApfValue()){
                if (lowest == null){
                    lowest = (OpenCell) c;
                }else if (lowest.getApfValue() > ((OpenCell) c).getApfValue()){
                    lowest = (OpenCell) c;
                }
            }
        }
        return lowest;
    }

}
