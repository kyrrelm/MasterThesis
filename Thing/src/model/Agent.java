package model;

import model.Cells.Cell;
import model.Cells.Cell.Type;
import model.Cells.OpenCell;

/**
 * Created by Kyrre on 17.10.2016.
 */
public class Agent {


    public enum Heading{
        NORTH,
        EAST,
        SOUTH,
        WEST

    }

    private final World world;
    private Heading heading;
    private OpenCell currentCell;
    private boolean avoidingObstacle;

    public Agent(OpenCell currentCell, Heading heading, World world) {
        this.currentCell = currentCell;
        //currentCell.placeAgent(this);
        this.heading = heading;
        this.world = world;
        this.avoidingObstacle = false;
    }

    public void interact(){
        Cell front = senseFront();
        Cell right = senseRight();
        Cell back = senseBack();
        Cell left = senseLeft();
        if (avoidingObstacle){
            avoidObstacle(front,right,back,left);
            return;
        }
        updateValue(front,right,back,left);
        if (!(right instanceof OpenCell && ((OpenCell) right).hasApfValue())){
            rotateRight();
            if (right.getType() == Type.OBSTACLE){
                avoidObstacle(right,back,left,front);
                return;
            }
            move(right);
            return;
        }
        if (front instanceof OpenCell){
            move(front);
            return;
        }
        if(front.getType() == Type.OBSTACLE){
            avoidObstacle(front,right,back,left);
            return;
        }

    }


    private void avoidObstacle(Cell front, Cell right, Cell back, Cell left) {
        if(front.getType() == Type.OBSTACLE && !avoidingObstacle){
            rotateRight();
            avoidObstacle(right,back,left,front);
            return;
        }
        avoidingObstacle = true;
        if (left instanceof OpenCell){
            rotateLeft();
            move(left);
            return;
        }
        if (front instanceof OpenCell){
            move(front);
            return;
        }
        if (right instanceof OpenCell){
            rotateRight();
            move(right);
        }
        avoidingObstacle = true;
    }

    /**
     *
     * @param cells front, right, back, left
     */
    private void updateValue(Cell... cells) {
        int min = Integer.MAX_VALUE-1;
        for (Cell c: cells) {
            if ((c instanceof OpenCell) && ((OpenCell) c).hasApfValue()){
                min = Math.min(min ,((OpenCell) c).getApfValue());
            }
        }
        currentCell.setApfValue(min+1);
    }

    private void move(Cell toCell) {
        currentCell.removeAgent();
        currentCell = (OpenCell) toCell;
        currentCell.placeAgent(this);
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
    }


    public Cell senseFront(){
        if (heading == Heading.NORTH){
            return world.getCell(currentCell.getX(),currentCell.getY()+1);
        }
        else if (heading == Heading.EAST){
            return world.getCell(currentCell.getX()+1,currentCell.getY());
        }
        else if (heading == Heading.SOUTH){
            return world.getCell(currentCell.getX(),currentCell.getY()-1);
        }
        else if (heading == Heading.WEST){
            return world.getCell(currentCell.getX()-1,currentCell.getY());
        }
        return null;
    }

    public Cell senseRight(){
        if (heading == Heading.NORTH){
            return world.getCell(currentCell.getX()+1,currentCell.getY());
        }
        else if (heading == Heading.EAST){
            return world.getCell(currentCell.getX(),currentCell.getY()-1);
        }
        else if (heading == Heading.SOUTH){
            return world.getCell(currentCell.getX()-1,currentCell.getY());
        }
        else if (heading == Heading.WEST){
            return world.getCell(currentCell.getX(),currentCell.getY()+1);
        }
        return null;
    }

    private Cell senseBack() {
        if (heading == Heading.NORTH){
            return world.getCell(currentCell.getX(),currentCell.getY()-1);
        }
        else if (heading == Heading.EAST){
            return world.getCell(currentCell.getX()-1,currentCell.getY());
        }
        else if (heading == Heading.SOUTH){
            return world.getCell(currentCell.getX(),currentCell.getY()+1);
        }
        else if (heading == Heading.WEST){
            return world.getCell(currentCell.getX()+1,currentCell.getY());
        }
        return null;
    }

    private Cell senseLeft() {
        if (heading == Heading.NORTH){
            return world.getCell(currentCell.getX()-1,currentCell.getY());
        }
        else if (heading == Heading.EAST){
            return world.getCell(currentCell.getX(),currentCell.getY()+1);
        }
        else if (heading == Heading.SOUTH){
            return world.getCell(currentCell.getX()+1,currentCell.getY());
        }
        else if (heading == Heading.WEST){
            return world.getCell(currentCell.getX(),currentCell.getY()-1);
        }
        return null;
    }

}
