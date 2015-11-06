package classes;

import classes.enumerations.FoodState;
import classes.utils.ResourcesUtils;


/**
 * Created by Dimitri on 21/10/2015.
 */
public class Food extends MapElement {
    private int foodImageIndex;
    private FoodState foodState;

    public Food(int x, int y, int shapeSize){
        super(x, y, shapeSize);
        foodState = FoodState.GOOD;
        foodImageIndex = ResourcesUtils.getInstance().getRandomFoodIndex();
        getShape().setFill(ResourcesUtils.getInstance().getGoodFood(foodImageIndex));
    }

    public void setFoodState(FoodState foodState) {
        this.foodState = foodState;
        switch (foodState) {
            case BAD:
                getShape().setFill(ResourcesUtils.getInstance().getBadFood(foodImageIndex));
                break;
            default:
                getShape().setFill(ResourcesUtils.getInstance().getGoodFood(foodImageIndex));
                break;
        }
    }
}
