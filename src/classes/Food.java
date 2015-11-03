package classes;

import enumerations.FoodState;
import javafx.scene.shape.Rectangle;
import utils.ResourcesUtils;


/**
 * Created by Dimitri on 21/10/2015.
 */
public class Food {
    private FoodState foodState;
    private Rectangle shape;
    private Integer foodIndex;

    public Food(int x, int y){
        foodState = FoodState.GOOD;
        foodIndex = ResourcesUtils.getInstance().getRandomFoodIndex();
        shape = new Rectangle(65, 65);
        shape.setFill(ResourcesUtils.getInstance().getGoodFood(foodIndex));
        shape.setX(x);
        shape.setY(y);
    }

    public void setFoodState(FoodState foodState) {
        this.foodState = foodState;

        switch (foodState){
            case BAD :
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
