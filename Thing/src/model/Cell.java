package model;

public abstract class Cell {

    /**
     * Created by Kyrre on 17.10.2016.
     */
    public enum Type{
        OBSTACLE,
        FOOD,
        NEST,
        FREE,
        AGENT,
        BORDER;
    }

    public Cell(int x, int y, Type type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    protected int x;
    protected int y;
    protected Type type;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        if (type == Type.OBSTACLE)
            return "X";
        if (type == Type.NEST)
            return "N";
        if (type == Type.AGENT)
            return "A";
        return " ";
    }
}
