package classes.utils;

import classes.enumerations.Image;
import classes.enumerations.Position;
import javafx.scene.paint.ImagePattern;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Dimitri on 01/11/2015.
 */
public class ResourcesUtils {
    public static ResourcesUtils INSTANCE = null;

    private Map<Integer, Image> goodFoodDictionnary;
    private Map<Integer, Image> badFoodDictionnary;
    private Map<Integer, Image> left_birdsDictionnary;
    private Map<Integer, Image> right_birdsDictionnary;

    public static ResourcesUtils getInstance(){
        if (INSTANCE == null)
            INSTANCE = new ResourcesUtils();

        return INSTANCE;
    }

    private ResourcesUtils(){
        goodFoodDictionnary = new HashMap();
        goodFoodDictionnary.put(0, Image.GOOD_CUPAKE_0);
        goodFoodDictionnary.put(1, Image.GOOD_CUPAKE_1);
        goodFoodDictionnary.put(2, Image.GOOD_CUPAKE_2);
        goodFoodDictionnary.put(3, Image.GOOD_CUPAKE_3);
        goodFoodDictionnary.put(4, Image.GOOD_CUPAKE_4);

        badFoodDictionnary = new HashMap<>();
        badFoodDictionnary.put(0, Image.BAD_CUPAKE_0);
        badFoodDictionnary.put(1, Image.BAD_CUPAKE_1);
        badFoodDictionnary.put(2, Image.BAD_CUPAKE_2);
        badFoodDictionnary.put(3, Image.BAD_CUPAKE_3);
        badFoodDictionnary.put(4, Image.BAD_CUPAKE_4);

        left_birdsDictionnary = new HashMap<>();
        left_birdsDictionnary.put(0, Image.LEFT_BLUE_BIRD);
        left_birdsDictionnary.put(1, Image.LEFT_RED_BIRD);
        left_birdsDictionnary.put(2, Image.LEFT_BROWN_BIRD);
        left_birdsDictionnary.put(3, Image.LEFT_GREEN_BIRD);

        right_birdsDictionnary = new HashMap<>();
        right_birdsDictionnary.put(0, Image.RIGHT_BLUE_BIRD);
        right_birdsDictionnary.put(1, Image.RIGHT_RED_BIRD);
        right_birdsDictionnary.put(2, Image.RIGHT_BROWN_BIRD);
        right_birdsDictionnary.put(3, Image.RIGHT_GREEN_BIRD);
    }

    public Integer getRandomFoodIndex(){
        if (goodFoodDictionnary.size() != badFoodDictionnary.size())
            return null;

        Random ran = new Random();
        return ran.nextInt(goodFoodDictionnary.size());
    }

    public Integer getRandomBirdIndex(){
        Random ran = new Random();
        return ran.nextInt(left_birdsDictionnary.size());
    }

    public ImagePattern getBird(Integer index, Position position){
        javafx.scene.image.Image image;

        if (position.equals(Position.LEFT))
            image = new javafx.scene.image.Image(left_birdsDictionnary.get(index).toString());
        else
            image = new javafx.scene.image.Image(right_birdsDictionnary.get(index).toString());

        return new ImagePattern(image);
    }

    public ImagePattern getGoodFood(Integer index){
        javafx.scene.image.Image image = new javafx.scene.image.Image(goodFoodDictionnary.get(index).toString());
        return new ImagePattern(image);
    }

    public ImagePattern getBadFood(Integer index){
        javafx.scene.image.Image image = new javafx.scene.image.Image(badFoodDictionnary.get(index).toString());
        return new ImagePattern(image);
    }
}
