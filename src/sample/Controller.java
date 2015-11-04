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

    private static Integer PACE = 500;
    private Graph graph;
    private CircularQueue<Food> foodCircularQueue = new CircularQueue(5);   // TODO : Ajouter un listener sur la liste pour notifier les pigeons de chaque modification
    private List<Character> pigeonsList = new ArrayList<>();  // TODO : A l'ajout de nourriture, démarrer le trajet des pigeons
    private Character child;

    public Controller() {

    }

    public void initChild(){
        child = new Character(0, 0, Image.CHILD);
        anchorPane.getChildren().add(child.getShape());

        anchorPane.getScene().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            double x = child.getShape().getX();
            double y = child.getShape().getY();

            switch (event.getCode()){
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
        //initGraph();
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
        for (Character character : pigeonsList){
            anchorPane.getChildren().remove(character.getShape());
        }
        pigeonsList.clear();

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

        int x = ((int) e.getSceneX()) - (((int) e.getSceneX()) % PACE);
        int y = ((int) e.getSceneY()) - (((int) e.getSceneY()) % PACE);

        Food food = new Food(x, y);
        anchorPane.getChildren().add(food.getShape());

        Food excedent = foodCircularQueue.pushAndPopExcedent(food);
        if (excedent != null)
            anchorPane.getChildren().remove(excedent.getShape());
    }


}

