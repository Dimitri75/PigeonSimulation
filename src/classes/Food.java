package classes;

import enumerations.FoodState;

/**
 * Created by Dimitri on 21/10/2015.
 */
public class Food {
    private FoodState foodState;
    private Location location;

    public Food(){
        this.foodState = FoodState.GOOD;
    }
}
