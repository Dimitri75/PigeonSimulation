package enumerations;

/**
 * Created by Dimitri on 01/11/2015.
 */
public enum Image {
    GOOD_CUPAKE_1("res/images/good_cupcake1.png"),
    GOOD_CUPAKE_2("res/images/good_cupcake2.png"),
    GOOD_CUPAKE_3("res/images/good_cupcake3.png"),

    BAD_CUPAKE_1("res/images/bad_cupcake1.png"),
    BAD_CUPAKE_2("res/images/bad_cupcake2.png"),
    BAD_CUPAKE_3("res/images/bad_cupcake3.png"),

    BLUE_BIRD("res/images/blue_bird.png");

    private String name = "";

    Image (String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
