package model;

import model.Cells.Cell;
import model.Cells.Cell.Type;
import model.Cells.OpenCell;
import model.Cells.OpenCell.PheromoneColor;
import sample.Settings;
import sample.Stats;

/**
 * Created by Kyrre on 17.10.2016.
 */
public class Agent {


    private static final boolean USING_APF_VALUE = true;
    private boolean returnAndColor;


    public enum Heading{
        NORTH,
        EAST,
        SOUTH,
        WEST;
    }

    private final World world;

    private Heading heading;
    private OpenCell currentCell;
    private boolean avoidingObstacle;
    private int load;

    public Agent(OpenCell currentCell, Heading heading, World world) {
        this.currentCell = currentCell;
        this.heading = heading;
        this.world = world;
        this.avoidingObstacle = false;
        this.returnAndColor = false;
        this.load = 0;

    }

    private Cell front;
    private Cell right;
    private Cell back;
    private Cell left;


    public void interact(){
        sense();

        if (avoidingObstacle){
            avoidObstacleSmasa();
            return;
        }
        if (returnAndColor){
            returnAndColor();
            return;
        }

        if (lookForFood()){
            return;
        }

        updateValue(front,right,back,left);

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

    private boolean lookForFood() {
        if (currentCell.getType() == Type.FOOD){
            this.load = currentCell.takeFood(Settings.AGENT_CAPACITY);
            returnAndColor();
            return true;
        }
        return false;
    }

    private void returnAndColor() {
        returnAndColor = true;
        if (front.getType() == Type.NEST){
            returnAndColor = false;
            move((OpenCell) front);
            unload();
            return;
        }
        if (left.getType() == Type.NEST){
            returnAndColor = false;
            rotateLeft();
            move((OpenCell) front);
            unload();
            return;
        }
        if (right.getType() == Type.NEST){
            returnAndColor = false;
            rotateRight();
            move((OpenCell) front);
            unload();
            return;
        }
        OpenCell lowest = findLowest(front,right,back,left);
        moveToCell(lowest);
        lowest.color(PheromoneColor.YELLOW);
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
        currentCell.removeAgent();
        currentCell = toCell;
        currentCell.placeAgent(this);
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
