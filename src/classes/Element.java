package classes;

import classes.enumerations.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


/**
 * Created by Dimitri on 05/11/2015.
 */
public class Element extends Thread implements Location{
    Integer x, y;
    private Rectangle shape;


    public Element(int x, int y, int shapeSize) {
        shape = new Rectangle(shapeSize, shapeSize);
        this.x = x;
        this.y = y;
        shape.setX(x);
        shape.setY(y);
        start();
    }

    public Element(int x, int y, int shapeSize, Image image) {
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
    public Integer getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
        shape.setX(x);
    }

    @Override
    public Integer getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
        shape.setY(y);
    }
}
