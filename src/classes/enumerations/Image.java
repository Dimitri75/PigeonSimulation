package classes.enumerations;

/**
 * Created by Dimitri on 01/11/2015.
 */
public enum Image {
    GOOD_CUPAKE_0("res/images/good_cupcake0.png"),
    GOOD_CUPAKE_1("res/images/good_cupcake1.png"),
    GOOD_CUPAKE_2("res/images/good_cupcake2.png"),
    GOOD_CUPAKE_3("res/images/good_cupcake3.png"),
    GOOD_CUPAKE_4("res/images/good_cupcake4.png"),

    BAD_CUPAKE_0("res/images/bad_cupcake0.png"),
    BAD_CUPAKE_1("res/images/bad_cupcake1.png"),
    BAD_CUPAKE_2("res/images/bad_cupcake2.png"),
    BAD_CUPAKE_3("res/images/bad_cupcake3.png"),
    BAD_CUPAKE_4("res/images/bad_cupcake4.png"),

    RIGHT_BLUE_BIRD("res/images/right_blue_bird.png"),
    RIGHT_RED_BIRD("res/images/right_red_bird.png"),
    RIGHT_BROWN_BIRD("res/images/right_brown_bird.png"),
    RIGHT_GREEN_BIRD("res/images/right_green_bird.png"),

    LEFT_BLUE_BIRD("res/images/left_blue_bird.png"),
    LEFT_RED_BIRD("res/images/left_red_bird.png"),
    LEFT_BROWN_BIRD("res/images/left_brown_bird.png"),
    LEFT_GREEN_BIRD("res/images/left_green_bird.png"),

    CHILD("res/images/isaac.png");

    private String name = "";

    Image (String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
