package model;

import model.Cells.Cell;
import model.Cells.Cell.Type;
import model.Cells.OpenCell;
import sample.Settings;
import sample.Stats;

import static model.Cells.OpenCell.*;

/**
 * Created by Kyrre on 17.10.2016.
 */
public class Agent {

    private static int idGenTrail = 0;
    private int foodAmountAtLastLocation;
    private boolean followingBrown;

    private static int genIdTrail(){
        return idGenTrail++;
    }

    private static final boolean USING_APF_VALUE = true;

    public enum Heading{
        NORTH,
        EAST,
        SOUTH,
        WEST;

    }
    private final World world;
    private Heading heading;


    private OpenCell currentCell;

    private boolean returningToNest;
    private boolean climbingTrail;
    private boolean avoidingObstacle;
    private boolean returnAndColor;
    private boolean atHome;
    private int load;
    private int trailId;

    public Agent(OpenCell currentCell, Heading heading, World world) {
        this.currentCell = currentCell;
        this.heading = heading;
        this.world = world;
        this.avoidingObstacle = false;
        this.returnAndColor = false;
        this.atHome = false;
        this.climbingTrail = false;
        this.returningToNest = false;
        this.followingBrown = false;
        this.load = 0;
        this.foodAmountAtLastLocation = 0;
        this.trailId = -1;

    }

    private Cell front;
    private Cell right;
    private Cell back;
    private Cell left;


    public void interact(){

        sense();

        updateValue(front,right,back,left);


        if(atHome){
            unload();
            atHome = false;
            if (handleTrail()){
                return;
            }
        }
        if (followingBrown){
            followBrown();
            return;
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

        //BORDER------------------------------------------------------------------------------
        if (front.getType() == Type.BORDER || right.getType() == Type.BORDER){
            while (!(front instanceof OpenCell) || right.getType() == Type.BORDER){
                rotateRight();
            }
            move((OpenCell) front);
            return;
        }
        if (left.getType() == Type.BORDER && front instanceof OpenCell && ((OpenCell) front).hasApfValue()){
            move((OpenCell) front);
            return;
        }
        //------------------------------------------------------------------------------------

    }

    private void followBrown() {
        followingBrown = true;
        if (front instanceof OpenCell){
            if(front.getType() == Type.FOOD){
                move((OpenCell) front);
                followingBrown = false;
            }
            else if (((OpenCell) front).containsColor(PheromoneColor.BROWN)){
                move((OpenCell) front);
            }
        }
        //TODO: If food disappears on the way
    }

    private boolean handleTrail() {
        OpenCell trail = senseAndReturnTrail(front,right,back,left);
        return climbTrail(trail);
    }

    private boolean climbTrail(OpenCell trail) {
        climbingTrail = true;
        if (trail == null){
            climbingTrail = false;
            return false;
        }
        moveToCell(trail);
        if (foodAmountAtLastLocation == 0){
            trail.removeTrail(trailId);
        }
        return true;
    }

    private OpenCell senseAndReturnTrail(Cell... cells) {
        for (Cell cell: cells) {
            if (cell instanceof OpenCell && ((OpenCell) cell).hasTrail(trailId)){
                return (OpenCell) cell;
            }
        }
        return null;
    }

    /**
     * Re-writing this to snse neigbours breaks trail stuff
     * @return
     */
    private boolean lookForFood() {
        if (currentCell.getType() == Type.FOOD){
            diffuseBrown(currentCell, left);
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

    private void unload() {
        Stats.getInstance().depositFood(load);
        load = 0;
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


    private void avoidObstacleSmasa() {
        while (!(front instanceof OpenCell) && !avoidingObstacle){
            rotateRight();
        }
        avoidingObstacle = true;
        if (front instanceof OpenCell && !((OpenCell) front).hasApfValue()){
            avoidingObstacle = false;
            move((OpenCell) front);
            return;
        }
        if (right instanceof OpenCell && !((OpenCell) right).hasApfValue()){
            avoidingObstacle = false;
            rotateRight();
            move((OpenCell) front);
            return;
        }
        if (left instanceof OpenCell && !((OpenCell) left).hasApfValue()){
            avoidingObstacle = false;
            rotateLeft();
            move((OpenCell) front);
            return;
        }
        if (front instanceof OpenCell){
            move((OpenCell) front);
            return;
        }
        while (!(front instanceof OpenCell)){
            rotateRight();
        }
        move((OpenCell) front);
    }


    public void moveToWaveFront(){
        if (front instanceof OpenCell){
            if (((OpenCell)front).hasApfValue() && left instanceof OpenCell && !((OpenCell) left).hasApfValue()){
                rotateLeft();
                move((OpenCell) front);
                return;
            }
            move((OpenCell) front);
            return;
        }
    }

    private void sense() {
        senseFront();
        senseRight();
        senseBack();
        senseLeft();
    }


    private void avoidObstacle() {
        if(front.getType() == Type.OBSTACLE && !avoidingObstacle){
            rotateRight();
            avoidObstacle();
            return;
        }
        avoidingObstacle = true;
        if (left instanceof OpenCell){
            rotateLeft();
            move((OpenCell) front);
            return;
        }
        if (front instanceof OpenCell){
            move((OpenCell) front);
            return;
        }
        if (right instanceof OpenCell){
            rotateRight();
            move((OpenCell) front);
            return;
        }
        while (!(right instanceof OpenCell)){
            rotateRight();
        }
        rotateRight();
        move((OpenCell) front);

    }

    /**
     *
     * @param cells front, right, back, left
     */
    private void updateValue(Cell... cells) {
       if (USING_APF_VALUE){
           apfUpdate(cells);
       }
       else {
           updatePheromone();
       }
    }

    private int pheromone = 1;
    private void updatePheromone() {
        currentCell.setApfValue(pheromone++);
    }

    /**
     * Not moving to front will break system
     * @param toCell
     */
    private void move(OpenCell toCell) {
        currentCell.removeAgent(this);
        currentCell = toCell;
        currentCell.placeAgent(this);
        sense();
        updateValue(front,right,back,left);
    }

    private void apfUpdate(Cell... cells){
        int min = Integer.MAX_VALUE-1;
        for (Cell c: cells) {
            if ((c instanceof OpenCell) && ((OpenCell) c).hasApfValue()){
                min = Math.min(min ,((OpenCell) c).getApfValue());
            }
        }
        currentCell.setApfValue(min+1);
    }

    private void rotateLeft() {
        if (heading == Heading.NORTH){
            heading = Heading.WEST;
        }
        else if (heading == Heading.EAST){
            heading = Heading.NORTH;
        }
        else if (heading == Heading.SOUTH){
            heading = Heading.EAST;
        }
        else if (heading == Heading.WEST){
            heading = Heading.SOUTH;
        }
        Cell oldFront = front;
        front = left;
        left = back;
        back = right;
        right = oldFront;
    }

    private void rotateRight() {
        if (heading == Heading.NORTH){
            heading = Heading.EAST;
        }
        else if (heading == Heading.EAST){
            heading = Heading.SOUTH;
        }
        else if (heading == Heading.SOUTH){
            heading = Heading.WEST;
        }
        else if (heading == Heading.WEST){
            heading = Heading.NORTH;
        }
        Cell oldFront = front;
        front = right;
        right = back;
        back = left;
        left = oldFront;
    }


    public void senseFront(){
        if (heading == Heading.NORTH){
            front = world.getCell(currentCell.getX(),currentCell.getY()+1);
        }
        else if (heading == Heading.EAST){
            front = world.getCell(currentCell.getX()+1,currentCell.getY());
        }
        else if (heading == Heading.SOUTH){
            front = world.getCell(currentCell.getX(),currentCell.getY()-1);
        }
        else if (heading == Heading.WEST){
            front = world.getCell(currentCell.getX()-1,currentCell.getY());
        }
    }

    public void senseRight(){
        if (heading == Heading.NORTH){
            right = world.getCell(currentCell.getX()+1,currentCell.getY());
        }
        else if (heading == Heading.EAST){
            right = world.getCell(currentCell.getX(),currentCell.getY()-1);
        }
        else if (heading == Heading.SOUTH){
            right = world.getCell(currentCell.getX()-1,currentCell.getY());
        }
        else if (heading == Heading.WEST){
            right = world.getCell(currentCell.getX(),currentCell.getY()+1);
        }
    }

    private boolean moveToCell(OpenCell cell){
        if (cell.equals(front)){
            move((OpenCell) front);
            return true;
        }
        if (cell.equals(left)){
            rotateLeft();
            move((OpenCell) front);
            return true;
        }
        if (cell.equals(right)){
            rotateRight();
            move((OpenCell) front);
            return true;
        }
        if (cell.equals(back)){
            rotateRight();
            rotateRight();
            move((OpenCell) front);
            return true;
        }
        return false;
    }

    private void senseBack() {
        if (heading == Heading.NORTH){
            back = world.getCell(currentCell.getX(),currentCell.getY()-1);
        }
        else if (heading == Heading.EAST){
            back = world.getCell(currentCell.getX()-1,currentCell.getY());
        }
        else if (heading == Heading.SOUTH){
            back = world.getCell(currentCell.getX(),currentCell.getY()+1);
        }
        else if (heading == Heading.WEST){
            back = world.getCell(currentCell.getX()+1,currentCell.getY());
        }
    }

    private void senseLeft() {
        if (heading == Heading.NORTH){
            left = world.getCell(currentCell.getX()-1,currentCell.getY());
        }
        else if (heading == Heading.EAST){
            left = world.getCell(currentCell.getX(),currentCell.getY()+1);
        }
        else if (heading == Heading.SOUTH){
            left = world.getCell(currentCell.getX()+1,currentCell.getY());
        }
        else if (heading == Heading.WEST){
            left = world.getCell(currentCell.getX(),currentCell.getY()-1);
        }
    }

}
