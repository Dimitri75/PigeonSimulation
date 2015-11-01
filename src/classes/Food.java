package classes;

import enumerations.FoodState;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * Created by Dimitri on 21/10/2015.
 */
public class Food {
    private FoodState foodState;
    private Location location;
    private Rectangle body;

    public Food(int x, int y){
        this.setFoodState(FoodState.GOOD);
        setLocation(new Location(x, y));
        setBody(new Rectangle(x, y, 5, 5));
        getBody().setFill(Color.WHITE);
        getBody().setStrokeWidth(1);
    }

    public FoodState getFoodState() {
        return foodState;
    }

    public void setFoodState(FoodState foodState) {
        this.foodState = foodState;
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
}
