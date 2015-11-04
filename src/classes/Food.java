package classes;

import classes.graph.Vertex;
import enumerations.FoodState;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import utils.ResourcesUtils;


/**
 * Created by Dimitri on 21/10/2015.
 */
public class Food extends Vertex{
    private FoodState foodState;
    private Rectangle body;
    private Integer foodIndex;

    public Food(int x, int y){
        super(x, y);
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

    public Rectangle getBody() {
        return body;
    }

    public void setBody(Rectangle body) {
        this.body = body;
    }
}
