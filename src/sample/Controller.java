package sample;

import classes.Character;
import classes.Food;
import classes.MapElement;
import classes.enumerations.FoodState;
import classes.enumerations.Image;
import classes.graph.Graph;
import classes.graph.Vertex;
import classes.list.CircularQueue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller {
    @FXML
    private Slider slider_size;
    @FXML
    private Button button_start;
    @FXML
    private Label label_error;
    @FXML
    private AnchorPane anchorPane;

    private Integer PACE;

    private Boolean started = false;
    private Graph graph;
    private CircularQueue<Food> foodCircularQueue = new CircularQueue(3);
    private List<Character> pigeonsList = new ArrayList<>();
    private Character child;
    private List<Thread> pigeonThreads = new ArrayList<>();
    private List<MapElement> obstaclesList = new ArrayList<>();

    public Controller() {

    }

    public void initChild() {
        child = new Character(0, 0, PACE, Image.CHILD);
        anchorPane.getChildren().add(child.getShape());

        if (!started) {
            anchorPane.getScene().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                int x = (int) child.getShape().getX();
                int y = (int) child.getShape().getY();

                switch (event.getCode()) {
                    case UP:
                        if (y - PACE >= 0 && checkIfNoObstacles(x, y - PACE))
                            child.setY(y - PACE);
                        break;
                    case DOWN:
                        if (y + PACE + child.getShape().getHeight() <= anchorPane.getPrefHeight()
                                && checkIfNoObstacles(x, y + PACE))
                            child.setY(y + PACE);
                        break;
                    case LEFT:
                        if (x - PACE >= 0 && checkIfNoObstacles(x - PACE, y))
                            child.setX(x - PACE);
                        break;
                    case RIGHT:
                        if (x + PACE + child.getShape().getWidth() <= anchorPane.getPrefWidth()
                                && checkIfNoObstacles(x + PACE, y))
                            child.setX(x + PACE);
                        break;
                }
            });
        }
    }

    public void initObstacles() {
        MapElement obstacle;
        int maxX = (int) (anchorPane.getPrefWidth() / PACE);
        int maxY = (int) (anchorPane.getPrefHeight() / PACE) - 1;
        for (int x = 2; x < maxX; x *= 2) {
            for (int y = 3; y < maxY; y++) {
                if (y % x != 0) {
                    obstacle = new MapElement(x * PACE, y * PACE, PACE, Image.OBSTACLE);
                    anchorPane.getChildren().add(obstacle.getShape());
                    obstaclesList.add(obstacle);
                }
            }
        }
        for (int y = 2; y < maxY; y*=2) {
            for (int x = 3; x < maxX; x++) {
                if (x % 5 == 0 || x % 5 == 1) {
                    obstacle = new MapElement(x * PACE, y * PACE, PACE, Image.OBSTACLE);
                    anchorPane.getChildren().add(obstacle.getShape());
                    obstaclesList.add(obstacle);
                }
            }
        }
    }

    public void initPigeons() {
        try {
            Random random = new Random();
            Character character;
            int nbPigeons = (int) (anchorPane.getPrefHeight() / PACE);
            for (int i = 0; i < nbPigeons; i++) {
                character = new Character(0, 0, PACE);

                int randX = -1;
                int randY = -1;

                while (randX + character.getShape().getWidth() > anchorPane.getWidth() ||
                        randY + character.getShape().getHeight() > anchorPane.getHeight() ||
                        !checkIfNoObstacles(randX, randY) ||
                        randX < 0 || randY < 0) {
                    randX = random.nextInt((int) anchorPane.getWidth());
                    randY = random.nextInt((int) anchorPane.getHeight());

                    randX -= randX % PACE;
                    randY -= randY % PACE;
                }
                character.setX(randX);
                character.setY(randY);


                anchorPane.getChildren().add(character.getShape());
                pigeonsList.add(character);
            }
        } catch (Exception e) {
            label_error.setText("Bravo ! Maintenant c'est cassé. :(");
        }
    }

    public void initGraph() {
        graph = new Graph((int) anchorPane.getWidth(), (int) anchorPane.getHeight(), obstaclesList, PACE);   // TODO : vérifier ce qui est généré
    }

    @FXML
    public void start() {
        clearAll();
        slider_size.setFocusTraversable(false);
        PACE = (slider_size.getValue() < 10) ? 10 : (int)slider_size.getValue();
        initObstacles();
        initPigeons();
        initChild();
        initGraph();
        started = true;
    }

    @FXML
    void onPressEnter(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            clearAll();
            button_start.fire();
        }
    }

    public void clearAll() {
        label_error.setText("");

        for (Character character : pigeonsList)
            anchorPane.getChildren().remove(character.getShape());
        pigeonsList.clear();

        for (Food food : foodCircularQueue)
            anchorPane.getChildren().remove(food.getShape());
        foodCircularQueue.clear();

        for (MapElement obstacle : obstaclesList)
            anchorPane.getChildren().remove(obstacle.getShape());
        obstaclesList.clear();

        if (child != null) {
            anchorPane.getChildren().remove(child.getShape());
            child = null;
        }
    }

    @FXML
    public void putFood(MouseEvent e) {
        if (started) {
            int x = (int) e.getSceneX() - (int) e.getSceneX() % PACE;
            int y = (int) e.getSceneY() - (int) e.getSceneY() % PACE;

            if (checkIfNoObstacles(x, y)) {
                if (!foodCircularQueue.isEmpty()) {
                    foodCircularQueue.peek().setFoodState(FoodState.BAD);
                }

                Food food = new Food(x, y, PACE);
                anchorPane.getChildren().add(food.getShape());

                Food excedent = foodCircularQueue.pushAndPopExcedent(food);
                if (excedent != null)
                    anchorPane.getChildren().remove(excedent.getShape());

                stopMovement();

                Character.FOOD_TO_EAT = food;

                startChasingFood();
            }
        }
    }


    public void startChasingFood() {
        try {
            for (Character pigeon : pigeonsList) {
                Vertex destination = graph.getVertexByLocation(foodCircularQueue.peek().getX(), foodCircularQueue.peek().getY());
                Vertex start = graph.getVertexByLocation(pigeon.getX(), pigeon.getY());

                pigeon.initPath(graph, start, destination);

                Thread thread = new Thread(pigeon);
                thread.start();

                pigeonThreads.add(thread);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startScattering(){
        //TODO les pigeons qui se dispersent
    }

    public void stopMovement() {
        if (!pigeonThreads.isEmpty()) {
            for (Thread thread : pigeonThreads)
                thread.interrupt();

            pigeonThreads.clear();
        }
    }

    public boolean checkIfNoObstacles(int x, int y) {
        for (MapElement obstacle : obstaclesList)
            if (obstacle.getX() == x && obstacle.getY() == y)
                return false;
        return true;
    }


}

