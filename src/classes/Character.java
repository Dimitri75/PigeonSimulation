package classes;

import enumerations.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import utils.ResourcesUtils;

/**
 * Created by Dimitri on 21/10/2015.
 */

public class Character extends Thread{
    private Integer pigeonIndex;
    private Rectangle shape;

    public Character(int x, int y) {
        pigeonIndex = ResourcesUtils.getInstance().getRandomBirdIndex();
        shape = new Rectangle(100, 100);
        shape.setFill(ResourcesUtils.getInstance().getBird(pigeonIndex));
        shape.setX(x);
        shape.setY(y);
        super.start();
    }

    public Character(int x, int y, Image image) {
        shape = new Rectangle(100, 100);
        shape.setFill(new ImagePattern(new javafx.scene.image.Image(image.toString())));
        shape.setX(x);
        shape.setY(y);
        super.start();
    }

    public Rectangle getShape() {
        return shape;
    }

    public void foodSeen(int x, int y){
        shape.setX(x);
        shape.setY(y);
        shape.setTranslateX(10);
    }

    public void foodEaten(int x, int y){

    }

}
