package classes;

import javafx.animation.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import utils.ResourcesUtils;


/**
 * Created by Dimitri on 21/10/2015.
 */

public class Pigeon extends Thread{

    private Location location;
    private Integer pigeonIndex;
    private Rectangle body;

    public Pigeon(int x, int y) {
        location = new Location(x, y);

        pigeonIndex = ResourcesUtils.getInstance().getRandomBirdIndex();
        body = new Rectangle(100, 100);
        body.setFill(ResourcesUtils.getInstance().getBird(pigeonIndex));
        body.setX(x);
        body.setY(y);
        super.start();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Rectangle getBody() {
        return body;
    }

    public void setBody(Rectangle body) {
        this.body = body;
    }

    public void foodSeen(int x, int y){
//        TranslateTransition tt = new TranslateTransition(Duration.millis(2000), body);
//        tt.fromXProperty().bind(body.translateXProperty());
//        tt.fromYProperty().bind(body.translateYProperty());
//        tt.setToX(x);
//        tt.setToY(y);
//        tt.play();

        PathElement[] path =
                {
                        new MoveTo(body.getX() + 50, body.getY() + 50),
                        new LineTo(x, y),
                };

        Path road = new Path();
        road.getElements().addAll(path);

        Path divider = new Path();
        divider.getStrokeDashArray().addAll(10.0, 10.0);
        divider.getElements().addAll(path);

        PathTransition anim = new PathTransition();
        anim.setNode(body);
        anim.setPath(road);
        //anim.setDuration(new Duration(6000));
        anim.setRate(0.1);

        Animation.Status status = anim.getStatus();
        if (status == Animation.Status.RUNNING &&
                status != Animation.Status.PAUSED)
            anim.pause();
        else
            anim.play();
    }

    public void foodEaten(int x, int y){

    }

}
