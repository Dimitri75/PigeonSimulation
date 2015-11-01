package classes;

import javafx.scene.shape.Rectangle;

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
        body.setX(x);
        body.setY(y);
        body.setTranslateX(10);
    }

    public void foodEaten(int x, int y){

    }

}
