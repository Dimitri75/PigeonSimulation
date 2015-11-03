package enumerations;

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

    BLUE_BIRD("res/images/blue_bird.png"),
    RED_BIRD("res/images/red_bird.png"),
    BROWN_BIRD("res/images/brown_bird.png"),
    GREEN_BIRD("res/images/green_bird.png"),

    CHILD("res/images/isaac.png");

    private String name = "";

    Image (String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
