package model;

import model.Cell.Type;

/**
 * Created by Kyrre on 17.10.2016.
 */
public class Agent {


    public enum Heading{
        NORTH,
        EAST,
        SOUTH,
        WEST;

    }

    private final World world;
    private Heading heading;
    private OpenCell currentCell;

    public Agent(OpenCell currentCell, Heading heading, World world) {
        this.currentCell = currentCell;
        currentCell.placeAgent(this);
        this.heading = heading;
        this.world = world;
    }

    public void interact(){
        Cell front = senseFront();
        Cell right = senseRight();
        Cell back = senseBack();
        Cell left = senseLeft();
        updateValue(front,right,back,left);
        if (right.getType() != Type.NEST && !(right.getType() == Type.FREE && ((OpenCell)right).hasApfValue())){
            rotateRight();
            move(right);
            return;
        }
        if (front.getType() != Type.OBSTACLE){
            move(front);
        }
    }

    private void updateValue(Cell... cells) {
        int min = Integer.MAX_VALUE;
        for (Cell c: cells) {
            if (c.getType() == Type.NEST){
                currentCell.setApfValue(1);
                return;
            }
            if (c.getType() == Type.FREE && ((OpenCell)c).hasApfValue()){
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
