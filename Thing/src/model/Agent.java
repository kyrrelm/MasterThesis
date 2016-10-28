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

    private Cell front;
    private Cell right;
    private Cell back;
    private Cell left;


    public void interact(){
        sense();
        if (avoidingObstacle){
            avoidObstacle();
            return;
        }
        updateValue(front,right,back,left);
        if (!(right instanceof OpenCell && ((OpenCell) right).hasApfValue())){
            rotateRight();
            if (front.getType() == Type.OBSTACLE){
                avoidObstacle();
                return;
            }
            move(front);
            return;
        }
        if (front instanceof OpenCell){
            move(front);
            return;
        }
        if(front.getType() == Type.OBSTACLE){
            avoidObstacle();
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
            move(front);
            return;
        }
        if (front instanceof OpenCell){
            move(front);
            return;
        }
        if (right instanceof OpenCell){
            rotateRight();
            move(front);
            return;
        }
        while (!(right instanceof OpenCell)){
            rotateRight();
        }
        rotateRight();
        move(front);

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
