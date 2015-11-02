package sample;

import classes.CircularQueue;
import classes.Food;
import classes.Pigeon;
import enumerations.FoodState;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.*;

public class Controller {
    private static Integer WIDTH = 1100;
    private static Integer HEIGHT = 500;

    @FXML
    private TextField textField_nbPigeons;
    @FXML
    private Button button_nbPigeons;
    @FXML
    private Label label_error;
    @FXML
    private AnchorPane bodyPane;

    private CircularQueue<Food> foodCircularQueue = new CircularQueue(5);
    private List<Pigeon> pigeonList = new ArrayList<>();

    @FXML
    public void nbPigeonsChosen() {
        try {
            int nbPigeons = Integer.parseInt(textField_nbPigeons.getText());
            removeAllPigeons();
            label_error.setText("");

            Random random = new Random();
            Pigeon pigeon;
            for (int i = 0; i < nbPigeons; i++) {
                pigeon = new Pigeon(random.nextInt(WIDTH), random.nextInt(HEIGHT));
                bodyPane.getChildren().add(pigeon.getBody());
                pigeonList.add(pigeon);
            }
        } catch (NumberFormatException e) {
            label_error.setText("Vous devez saisir un nombre entier.");
        } catch (Exception e) {
            label_error.setText("Bravo ! Maintenant c'est cassé. :(");
        }
    }

    @FXML
    void onPressEnter(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            button_nbPigeons.fire();
        }
    }

    public void removeAllPigeons(){
        for (Pigeon pigeon : pigeonList){
            bodyPane.getChildren().remove(pigeon.getBody());
        }
        pigeonList.clear();
    }

    @FXML
    public void putFood(MouseEvent e){
        if (!foodCircularQueue.isEmpty()) {
            foodCircularQueue.peek().setFoodState(FoodState.BAD);
        }

        Food food = new Food((int) e.getSceneX(), (int) e.getSceneY());
        bodyPane.getChildren().add(food.getBody());

        Food excedent = foodCircularQueue.pushAndPopExcedent(food);
        if (excedent != null)
            bodyPane.getChildren().remove(excedent.getBody());

        /*for (Pigeon pigeon : pigeonList){
            pigeon.foodSeen(food.getLocation().getX(), food.getLocation().getY());
        }*/
    }
}

