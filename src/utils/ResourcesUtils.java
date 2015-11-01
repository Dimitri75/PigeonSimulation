package utils;

import enumerations.Image;
import javafx.scene.paint.ImagePattern;

import javax.annotation.Resource;
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
    private Map<Integer, Image> birdsDictionnary;

    public static ResourcesUtils getInstance(){
        if (INSTANCE == null)
            INSTANCE = new ResourcesUtils();

        return INSTANCE;
    }

    private ResourcesUtils(){
        goodFoodDictionnary = new HashMap();
        goodFoodDictionnary.put(0, Image.GOOD_CUPAKE_1);
        goodFoodDictionnary.put(1, Image.GOOD_CUPAKE_2);
        goodFoodDictionnary.put(2, Image.GOOD_CUPAKE_3);

        badFoodDictionnary = new HashMap<>();
        badFoodDictionnary.put(0, Image.BAD_CUPAKE_1);
        badFoodDictionnary.put(1, Image.BAD_CUPAKE_2);
        badFoodDictionnary.put(2, Image.BAD_CUPAKE_3);

        birdsDictionnary = new HashMap<>();
        birdsDictionnary.put(0, Image.BLUE_BIRD);
    }

    public Integer getRandomFoodIndex(){
        if (goodFoodDictionnary.size() != badFoodDictionnary.size())
            return null;

        Random ran = new Random();
        return ran.nextInt(goodFoodDictionnary.size());
    }

    public Integer getRandomBirdIndex(){
        Random ran = new Random();
        return ran.nextInt(birdsDictionnary.size());
    }

    public ImagePattern getBird(Integer index){
        javafx.scene.image.Image image = new javafx.scene.image.Image(birdsDictionnary.get(index).toString());
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
