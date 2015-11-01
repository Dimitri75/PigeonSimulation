package classes;

import enumerations.FoodState;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import utils.ResourcesUtils;


/**
 * Created by Dimitri on 21/10/2015.
 */
public class Food {
    private Location location;
    private FoodState foodState;
    private Rectangle body;
    private Integer foodIndex;

    public Food(int x, int y){
        location = new Location(x, y);
        foodState = FoodState.GOOD;

        foodIndex = ResourcesUtils.getInstance().getRandomFoodIndex();
        body = new Rectangle(50, 50);
        body.setFill(ResourcesUtils.getInstance().getGoodFood(foodIndex));
        body.setX(x);
        body.setY(y);
    }

    public FoodState getFoodState() {
        return foodState;
    }

    public void setFoodState(FoodState foodState) {
        this.foodState = foodState;
        body.setFill(ResourcesUtils.getInstance().getBadFood(foodIndex));
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
