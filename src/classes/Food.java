package classes;

import classes.graph.Location;
import classes.enumerations.FoodState;
import javafx.scene.shape.Rectangle;
import classes.utils.ResourcesUtils;


/**
 * Created by Dimitri on 21/10/2015.
 */
public class Food extends Location{
    private FoodState foodState;
    private Rectangle shape;
    private Integer foodIndex;

    public Food(int x, int y){
        super(x, y);
        foodState = FoodState.GOOD;
        foodIndex = ResourcesUtils.getInstance().getRandomFoodIndex();
        shape = new Rectangle(50, 50);
        shape.setFill(ResourcesUtils.getInstance().getGoodFood(foodIndex));
        shape.setX(x);
        shape.setY(y);
    }

    public void setFoodState(FoodState foodState) {
        this.foodState = foodState;
        switch (foodState) {
            case BAD:
                shape.setFill(ResourcesUtils.getInstance().getBadFood(foodIndex));
                break;
            default:
                shape.setFill(ResourcesUtils.getInstance().getGoodFood(foodIndex));
                break;
        }
    }

    public Rectangle getShape() {
        return shape;
    }
}
