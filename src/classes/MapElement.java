package classes;

import classes.enumerations.Image;
import classes.interfaces.Location;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


/**
 * Created by Dimitri on 05/11/2015.
 */
public class MapElement extends Thread implements Location {
    int x, y;
    private Rectangle shape;


    public MapElement(int x, int y, int shapeSize) {
        shape = new Rectangle(shapeSize, shapeSize);
        this.x = x;
        this.y = y;
        shape.setX(x);
        shape.setY(y);
        start();
    }

    public MapElement(int x, int y, int shapeSize, Image image) {
        shape = new Rectangle(shapeSize, shapeSize);
        this.x = x;
        this.y = y;
        shape.setX(x);
        shape.setY(y);
        getShape().setFill(new ImagePattern(new javafx.scene.image.Image(image.toString())));
        start();
    }

    public Rectangle getShape() {
        return shape;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
        shape.setX(x);
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
        shape.setY(y);
    }
}
