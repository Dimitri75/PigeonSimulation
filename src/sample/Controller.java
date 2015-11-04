package sample;

import classes.graph.Graph;
import classes.list.CircularQueue;
import javafx.scene.layout.AnchorPane;

import classes.Food;
import classes.Character;
import classes.enumerations.FoodState;
import classes.enumerations.Image;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.*;

public class Controller {
    @FXML
    private Button button_start;
    @FXML
    private Label label_error;
    @FXML
    private AnchorPane anchorPane;

    private final static Integer PACE = 10;
    private final static Integer NB_PIGEONS = 10;

    private Boolean started = false;
    private Graph graph;
    private CircularQueue<Food> foodCircularQueue = new CircularQueue(3);   // TODO : Ajouter un listener sur la liste pour notifier les pigeons de chaque modification
    private List<Character> pigeonsList = new ArrayList<>();  // TODO : A l'ajout de nourriture, démarrer le trajet des pigeons
    private Character child;

    public Controller() {

    }

    public void initChild(){
        child = new Character(0, 0, Image.CHILD);
        anchorPane.getChildren().add(child.getShape());

        if (!started) {
            anchorPane.getScene().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                double x = child.getShape().getX();
                double y = child.getShape().getY();

                switch (event.getCode()) {
                    case UP:
                        if (y - PACE >= 0)
                            child.getShape().setY(y - PACE);
                        break;
                    case DOWN:
                        if (y + PACE + child.getShape().getHeight() <= anchorPane.getPrefHeight())
                            child.getShape().setY(y + PACE);
                        break;
                    case LEFT:
                        if (x - PACE >= 0)
                            child.getShape().setX(x - PACE);
                        break;
                    case RIGHT:
                        if (x + PACE + child.getShape().getWidth() <= anchorPane.getPrefWidth())
                            child.getShape().setX(x + PACE);
                        break;
                }
            });
        }
    }

    public void initPigeons(){
        try {
            Random random = new Random();
            Character character;
            for (int i = 0; i < NB_PIGEONS; i++) {
                character = new Character(0, 0);

                int randX = Integer.MAX_VALUE;
                int randY = Integer.MAX_VALUE;
                while (randX + character.getShape().getWidth() > anchorPane.getWidth() ||
                        randY + character.getShape().getHeight() > anchorPane.getHeight()){
                    randX = random.nextInt((int) anchorPane.getWidth());
                    randY = random.nextInt((int) anchorPane.getHeight());

                    randX -= randX % PACE - 2*PACE;
                    randY -= randY % PACE - 2*PACE;
                }
                character.getShape().setX(randX);
                character.getShape().setY(randY);


                anchorPane.getChildren().add(character.getShape());
                pigeonsList.add(character);
            }
        } catch (Exception e) {
            label_error.setText("Bravo ! Maintenant c'est cassé. :(");
        }
    }

    public void initGraph(){
        graph = new Graph((int)anchorPane.getWidth(), (int)anchorPane.getHeight(), PACE);   // TODO FIX : La génération des vertex et edges prend trop de temps + vérifier ce qui est généré
    }

    @FXML
    public void start() {
        clearAll();
        //initGraph();
        initPigeons();
        initChild();
        started = true;
    }

    @FXML
    void onPressEnter(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            clearAll();
            button_start.fire();
        }
    }

    public void clearAll(){
        label_error.setText("");

        for (Character character : pigeonsList)
            anchorPane.getChildren().remove(character.getShape());
        pigeonsList.clear();

        for (Food food : foodCircularQueue)
            anchorPane.getChildren().remove(food.getShape());
        foodCircularQueue.clear();

        if (child != null) {
            anchorPane.getChildren().remove(child.getShape());
            child = null;
        }
    }

    @FXML
    public void putFood(MouseEvent e) {
        if (started) {
            if (!foodCircularQueue.isEmpty()) {
                foodCircularQueue.peek().setFoodState(FoodState.BAD);
            }

            int x = ((int) e.getSceneX()) - (((int) e.getSceneX()) % PACE) - 2*PACE;
            int y = ((int) e.getSceneY()) - (((int) e.getSceneY()) % PACE) - 2*PACE;

            Food food = new Food(x, y);
            anchorPane.getChildren().add(food.getShape());

            Food excedent = foodCircularQueue.pushAndPopExcedent(food);
            if (excedent != null)
                anchorPane.getChildren().remove(excedent.getShape());
        }
    }
}

