package classes;

import classes.graph.Location;
import javafx.scene.shape.Rectangle;

import classes.enumerations.Image;
import javafx.scene.paint.ImagePattern;

import classes.utils.ResourcesUtils;

/**
 * Created by Dimitri on 21/10/2015.
 */

public class Character extends Location implements Runnable{
    private Integer characterIndex;
    private Rectangle shape;

    public Character(int x, int y) {
        super(x, y);
        characterIndex = ResourcesUtils.getInstance().getRandomBirdIndex();
        shape = new Rectangle(100, 100);
        shape.setFill(ResourcesUtils.getInstance().getBird(characterIndex));
        shape.setX(x);
        shape.setY(y);
    }

    public Character(int x, int y, Image image) {
        shape = new Rectangle(100, 100);
        shape.setFill(new ImagePattern(new javafx.scene.image.Image(image.toString())));
        shape.setX(x);
        shape.setY(y);
    }

    public Rectangle getShape() {
        return shape;
    }

    public void foodSeen(int x, int y){
        shape.setX(x);
        shape.setY(y);
        shape.setTranslateX(10);
    }

    @Override
    public void run() {

    }
}
