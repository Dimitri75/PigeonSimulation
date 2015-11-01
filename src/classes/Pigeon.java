package classes;

<<<<<<< HEAD
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
=======
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import utils.ResourcesUtils;
>>>>>>> develop

/**
 * Created by Dimitri on 21/10/2015.
 */
<<<<<<< HEAD
public class Pigeon extends Thread{
=======
public class Pigeon {
>>>>>>> develop
    private Location location;
    private Integer pigeonIndex;
    private Rectangle body;

    public Pigeon(int x, int y) {
        location = new Location(x, y);

<<<<<<< HEAD
        body = new Circle(5);
        body.setCenterX(x);
        body.setCenterY(y);
        body.setFill(Color.TRANSPARENT);
        body.setStroke(Color.BLACK);
        body.setStrokeWidth(1);

        super.start();
=======
        pigeonIndex = ResourcesUtils.getInstance().getRandomBirdIndex();
        body = new Rectangle(100, 100);
        body.setFill(ResourcesUtils.getInstance().getBird(pigeonIndex));
        body.setX(x);
        body.setY(y);
>>>>>>> develop
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
        body.setCenterX(x);
        body.setCenterY(y);

        body.setTranslateX();
    }

    public void foodEaten(int x, int y){

    }

}
