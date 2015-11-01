package sample;

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
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Controller {
    @FXML
    private TextField nbPigeonTxt;
    @FXML
    private Button nbPigeonBtn;
    @FXML
    private Label errorLbl;
    @FXML
    private AnchorPane bodyPane;

    private Stack<Food> foods = new Stack<Food>();
    private ArrayList<Pigeon> pigeons = new ArrayList<>();


    @FXML
    public void nbPigeonChosen() {
        try {
            Pigeon pigeon;
            Random random = new Random();
            int nbPigeons = Integer.parseInt(nbPigeonTxt.getText());

            removeAllPigeons();
            errorLbl.setText("");

            for (int i = 0; i < nbPigeons; i++) {
                pigeon = new Pigeon(random.nextInt((1200) + 1), random.nextInt((600) + 1));
                bodyPane.getChildren().add(pigeon.getBody());
                pigeons.add(pigeon);
            }
        } catch (Exception e) {
            errorLbl.setText("Vous devez saisir un nombre entier");
        }
    }

    @FXML
    void verifyIfEnterKey(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            nbPigeonBtn.fire();
        }
    }

    public void removeAllPigeons(){
        for (Pigeon pigeon : pigeons){
            bodyPane.getChildren().remove(pigeon.getBody());
        }
        pigeons.clear();
    }

    @FXML
    public void putFood(MouseEvent e){
        if (!foods.empty()) {
            Food badFood = foods.peek();
            badFood.setFoodState(FoodState.BAD);
            badFood.getBody().setFill(Color.BLACK);
        }
        Food food = new Food((int) e.getSceneX(), (int) e.getSceneY());
        bodyPane.getChildren().add(food.getBody());
        foods.push(food);
    }
}