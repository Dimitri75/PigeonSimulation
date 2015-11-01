package classes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by Dimitri on 21/10/2015.
 */
public class Pigeon{
    private Location location;
    private Circle body;

    public Pigeon(int x, int y){
        location = new Location(x, y);

        body = new Circle(5);
        body.setCenterX(x);
        body.setCenterY(y);
        body.setFill(Color.TRANSPARENT);
        body.setStroke(Color.BLACK);
        body.setStrokeWidth(1);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Circle getBody() {
        return body;
    }

    public void setBody(Circle body) {
        this.body = body;
    }

}
