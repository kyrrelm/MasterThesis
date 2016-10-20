package model;

/**
 * Created by Kyrre on 17.10.2016.
 */
public class OpenCell extends Cell{

    private int apfValue;
    private Agent agent;

    public OpenCell(int x, int y, Type type) {
        super(x, y, type);
        apfValue = -1;
        agent = null;
    }

    public int getApfValue() {
        return apfValue;
    }

    public void setApfValue(int apfValue) {
        if (hasApfValue()){
            this.apfValue = Math.min(this.apfValue, apfValue);
        }
        else {
            this.apfValue = apfValue;
        }
    }

    public boolean containAgent(){
        if (agent == null)
            return false;
        return true;
    }

    public boolean placeAgent(Agent agent) {
        if (this.agent == null){
            this.agent = agent;
            this.type = Type.AGENT;
            return true;
        }
        return false;
    }

    public void removeAgent(){
        this.type = Type.FREE;
        this.agent = null;
    }



    @Override
    public String toString() {
        if (apfValue == -1)
            return super.toString();
        return String.valueOf(apfValue);
    }

    public boolean hasApfValue() {
        if (apfValue == -1)
            return false;
        return true;
    }

    @Override
    public CellState createCellState() {
        return new CellState(type,apfValue);
    }
}
