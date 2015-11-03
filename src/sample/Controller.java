package sample;

import classes.*;
import classes.Character;
import enumerations.FoodState;
import enumerations.Image;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.*;

public class Controller {
    @FXML
    private Button button_start;
    @FXML
    private Label label_error;
    @FXML
    private AnchorPane anchorPane;

    private CircularQueue<Food> foodCircularQueue = new CircularQueue(5);
    private List<Character> characterList = new ArrayList<>();
    private Character child;

    public Controller() {

    }

    public void initChild(){
        child = new Character(0, 0, Image.CHILD);
        anchorPane.getChildren().add(child.getShape());


        anchorPane.getScene().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            int pace = 5;
            double x = child.getShape().getX();
            double y = child.getShape().getY();

            switch (event.getCode()){
                case UP:
                    if (y - pace > 0)
                        child.getShape().setY(y - pace);
                    break;
                case DOWN:
                    if (y + pace + child.getShape().getHeight() < anchorPane.getPrefHeight())
                        child.getShape().setY(y + pace);
                    break;
                case LEFT:
                    if (x - pace > 0)
                        child.getShape().setX(x - pace);
                    break;
                case RIGHT:
                    if (x + pace + child.getShape().getWidth() < anchorPane.getPrefWidth())
                        child.getShape().setX(x + pace);
                    break;
            }
        });
    }

    public void initPigeons(){
        try {
            removeAllCharacters();
            label_error.setText("");

            int nbPigeons = 8;
            Random random = new Random();
            Character character;
            for (int i = 0; i < nbPigeons; i++) {
                character = new Character(random.nextInt((int) anchorPane.getWidth() - 100), random.nextInt((int) anchorPane.getHeight() - 100));
                anchorPane.getChildren().add(character.getShape());
                characterList.add(character);
            }
        } catch (Exception e) {
            label_error.setText("Bravo ! Maintenant c'est cassé. :(");
        }
    }

    @FXML
    public void start() {
        initPigeons();
        initChild();
    }

    @FXML
    void onPressEnter(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            button_start.fire();
        }
    }

    public void removeAllCharacters(){
        for (Character character : characterList){
            anchorPane.getChildren().remove(character.getShape());
        }
        characterList.clear();

        if (child != null) {
            anchorPane.getChildren().remove(child.getShape());
            child = null;
        }
    }

    @FXML
    public void putFood(MouseEvent e){
        if (!foodCircularQueue.isEmpty()) {
            foodCircularQueue.peek().setFoodState(FoodState.BAD);
        }

        Food food = new Food((int) e.getSceneX(), (int) e.getSceneY());
        anchorPane.getChildren().add(food.getBody());

        Food excedent = foodCircularQueue.pushAndPopExcedent(food);
        if (excedent != null)
            anchorPane.getChildren().remove(excedent.getBody());

        /*for (Character pigeon : characterList){
            pigeon.foodSeen(food.getLocation().getX(), food.getLocation().getY());
        }*/
    }
}

