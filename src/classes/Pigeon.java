package classes;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * Created by Dimitri on 21/10/2015.
 */
public class Pigeon extends Thread{
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

        super.start();
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

    public void foodSeen(int x, int y){
        body.setCenterX(x);
        body.setCenterY(y);

        body.setTranslateX();
    }

    public void foodEaten(int x, int y){

    }

}
