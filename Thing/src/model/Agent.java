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
        this.heading = heading;
        this.world = world;
    }

    public void interact(){
        Cell right = senseRight();
        if (right.getType() != Type.NEST || !(right.getType() == Type.FREE && ((OpenCell)right).hasApfValue())){
            rotateRight();
        }
        move(right);
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

}
