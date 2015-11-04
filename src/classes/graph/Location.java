package classes.graph;

/**
 * Created by Dimitri on 21/10/2015.
 */
public class Location {
    int x;
    int y;

    public Location(){}

    public Location(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    @Override
    public String toString() {
        return "Location (" + x + ", " + y + ")";
    }
}
