package model.agent;

import model.World;
import model.cell.Cell;
import model.cell.OpenCell;
import sample.Settings;
import sample.Stats;

/**
 * Created by kyrrelm on 22.11.2016.
 */
public abstract class Agent {


    public enum Heading{
        NORTH,
        EAST,
        SOUTH,
        WEST;

    }




    public enum AgentType {
        HARVESTER,
        SCOUT,
        DEFAULT;
    }
    public final AgentType agentType;
    protected final World world;
    protected Heading heading;

    protected OpenCell currentCell;
    protected Cell front;

    protected Cell right;
    protected Cell back;
    protected Cell left;
    private int timestep;

    protected int load;
    protected int foodAmountAtLastLocation;
    protected int trailId;
    protected boolean avoidingObstacle;

    protected boolean climbingTrail;
    protected boolean returningToNest;
    protected boolean atHome;
    protected Agent(OpenCell currentCell, Heading heading, World world, AgentType agentType){
        this.currentCell = currentCell;
        this.heading = heading;
        this.world = world;
        this.agentType = agentType;

        this.front = null;
        this.right = null;
        this.back = null;
        this.left = null;

        this.timestep = -1;
        this.load = 0;
        this.foodAmountAtLastLocation = -1;
        this.trailId = -1;

        this.avoidingObstacle = false;
        this.climbingTrail = false;
        this.atHome = false;
        this.returningToNest = false;
    }

    public void interact(int timestep){
        this.timestep = timestep;
        sense();
        if (behave()){
            return;
        }
        if (front.getType() == Cell.Type.OBSTACLE){
            avoidObstacleSmasa();
            return;
        }
        avoidBorder();
    }

    protected abstract boolean behave();

    protected boolean avoidBorder() {
        if (front.getType() == Cell.Type.BORDER || right.getType() == Cell.Type.BORDER){
            while (!(front instanceof OpenCell) || right.getType() == Cell.Type.BORDER){
                rotateRight();
            }
            move((OpenCell) front);
            return true;
        }
        if (left.getType() == Cell.Type.BORDER && front instanceof OpenCell && ((OpenCell) front).hasApfValue()){
            move((OpenCell) front);
            return true;
        }
        return false;
    }


    protected void avoidObstacleSmasa() {
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
        if (avoidBorder()){
            avoidingObstacle = false;
            return;
        }
        while (!(front instanceof OpenCell)){
            rotateRight();
        }
        move((OpenCell) front);
    }

    protected void unload() {
        Stats.getInstance().depositFood(load, timestep);
        load = 0;
    }

    public void setTrailId(int id) {
        trailId = id;
    }

    protected void sense() {
        senseFront();
        senseRight();
        senseBack();
        senseLeft();
    }

    protected void rotateLeft() {
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


    protected void rotateRight() {
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

    protected boolean moveToCell(OpenCell cell){
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

    protected void senseFront(){
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

    protected void senseRight(){
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

    protected void senseBack() {
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

    protected void senseLeft() {
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

    /**
     *
     * @param cells front, right, back, left
     */
    protected void updateValue(Cell... cells) {
        if (Settings.USING_APF_VALUE){
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

    private void apfUpdate(Cell... cells){
        int min = Integer.MAX_VALUE-1;
        for (Cell c: cells) {
            if ((c instanceof OpenCell) && ((OpenCell) c).hasApfValue()){
                min = Math.min(min ,((OpenCell) c).getApfValue());
            }
        }
        currentCell.setApfValue(min+1);
    }

    protected boolean handleTrail(boolean removeBrown) {
        OpenCell trail = senseAndReturnTrail(front,right,back,left);
        return climbTrail(trail, removeBrown);
    }

    protected boolean climbTrail(OpenCell trail, boolean removeBrown) {
        climbingTrail = true;
        if (trail == null){
            if (foodAmountAtLastLocation == 0 && removeBrown && senseAndRemoveBrown(front, right, back, left)){
                return true;
            }
            climbingTrail = false;
            return false;
        }
        moveToCell(trail);
        if (foodAmountAtLastLocation == 0){
            trail.removeTrail(trailId);
            trail.removeColor(OpenCell.PheromoneColor.BROWN);
        }
        return true;
    }

    protected boolean senseAndRemoveBrown(Cell... cells) {
        for (Cell cell: cells){
            if (cell instanceof OpenCell && ((OpenCell) cell).containsColor(OpenCell.PheromoneColor.BROWN)){
                moveToCell((OpenCell) cell);
                ((OpenCell) cell).removeColor(OpenCell.PheromoneColor.BROWN);
                return true;
            }
        }
        return false;
    }

    protected OpenCell senseAndReturnTrail(Cell... cells) {
        for (Cell cell: cells) {
            if (cell instanceof OpenCell && ((OpenCell) cell).hasTrail(trailId)){
                return (OpenCell) cell;
            }
        }
        return null;
    }

    protected void returnToNest(OpenCell existingTrail) {
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

    protected boolean goToNest() {
        if (front.getType() == Cell.Type.NEST){
            move((OpenCell) front);
            atHome = true;
            return true;
        }
        if (left.getType() == Cell.Type.NEST){
            rotateLeft();
            move((OpenCell) front);
            atHome = true;
            return true;
        }
        if (right.getType() == Cell.Type.NEST){
            rotateRight();
            move((OpenCell) front);
            atHome = true;
            return true;
        }
        return false;
    }


    /**
     * Not moving to front will break system
     * @param toCell
     */
    protected void move(OpenCell toCell) {
        currentCell.removeAgent(this);
        currentCell = toCell;
        currentCell.placeAgent(this);
        Stats.getInstance().consumeEnergy(1);
        sense();
        updateValue(front,right,back,left);
    }
}
