package classes;

/**
 * Created by Dimitri on 21/10/2015.
 */
public class Location {
    int x;
    int y;

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

    @Override
    public String toString() {
        return "Location (" + x + ", " + y + ")";
    }
}
