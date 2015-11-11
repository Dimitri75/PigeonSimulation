package sample;

import classes.Character;
import classes.Food;
import classes.MapElement;
import classes.enumerations.FoodState;
import classes.enumerations.Image;
import classes.graph.Graph;
import classes.graph.Vertex;
import classes.list.CircularQueue;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.util.*;

import static classes.Character.ACTION_DONE;

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

    private Timer timer;
    private Boolean started = false;
    private Graph graph;
    private CircularQueue<Food> foodCircularQueue = new CircularQueue(3);
    private CircularQueue<Food> badFoodCircularQueue = new CircularQueue(3);
    private List<Character> pigeonsList = new ArrayList<>();
    private Character child;
    private Map<Character, Thread> pigeonThreads = new HashMap<>();
    private List<MapElement> obstaclesList = new ArrayList<>();

    public Controller() {

    }

    public void initChild() {
        child = new Character(0, 0, PACE, Image.CHILD);
        anchorPane.getChildren().add(child.getShape());

        if (!started) {
            anchorPane.getScene().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                if (!anchorPane.isFocused())
                    anchorPane.requestFocus();

                int x = (int) child.getShape().getX();
                int y = (int) child.getShape().getY();

                switch (event.getCode()) {
                    case UP:
                        if (y - PACE >= 0 && checkIfNoObstacles(x, y - PACE))
                            child.setY(y - PACE);
                        tryNotifyPigeon(child.getX(), child.getY());
                        break;
                    case DOWN:
                        if (y + PACE + child.getShape().getHeight() <= anchorPane.getPrefHeight()
                                && checkIfNoObstacles(x, y + PACE))
                            child.setY(y + PACE);
                        tryNotifyPigeon(child.getX(), child.getY());
                        break;
                    case LEFT:
                        if (x - PACE >= 0 && checkIfNoObstacles(x - PACE, y))
                            child.setX(x - PACE);
                        tryNotifyPigeon(child.getX(), child.getY());
                        break;
                    case RIGHT:
                        if (x + PACE + child.getShape().getWidth() <= anchorPane.getPrefWidth()
                                && checkIfNoObstacles(x + PACE, y))
                            child.setX(x + PACE);
                        tryNotifyPigeon(child.getX(), child.getY());
                        break;
                    case SPACE:
                        notifyAllPigeons();
                        break;
                }
            });
        }
    }

    public void notifyAllPigeons(){
        stopMovement();
        for (Character pigeon : pigeonsList)
            notifyPigeon(pigeon);
    }

    public boolean tryNotifyPigeon(int x, int y){
        Character pigeon;
        if ((pigeon = getPigeonFromLocation(x, y)) != null){
            notifyPigeon(pigeon);
            return true;
        }
        return false;
    }

    public void notifyPigeon(Character pigeon){
        if (pigeonThreads.containsKey(pigeon))
            pigeonThreads.get(pigeon).interrupt();
        pigeonThreads.remove(pigeon);

        Vertex start = graph.getVertexByLocation(pigeon.getX(), pigeon.getY());
        Vertex destination = graph.getRandomVertex();

        pigeon.initPath(graph, start, destination);

        Thread thread = new Thread(pigeon);
        thread.start();

        pigeonThreads.put(pigeon, thread);
    }

    public Character getPigeonFromLocation(int x, int y){
        for (Character pigeon : pigeonsList)
            if (pigeon.getX() == x && pigeon.getY() == y)
                return pigeon;
        return null;
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
        for (int y = 2; y < maxY; y *= 2) {
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
        button_start.setFocusTraversable(false);
        PACE = (slider_size.getValue() < 10) ? 10 : (int) slider_size.getValue();
        initObstacles();
        initPigeons();
        initChild();
        initGraph();
        started = true;
    }

    @FXML
    void button_start_onKeyEvent(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            clearAll();
            button_start.fire();
        }
        else
            anchorPane.requestFocus();
    }

    public void clearAll() {
        label_error.setText("");

        for (Character character : pigeonsList)
            anchorPane.getChildren().remove(character.getShape());
        pigeonsList.clear();

        for (Food food : foodCircularQueue)
            anchorPane.getChildren().remove(food.getShape());
        foodCircularQueue.clear();

        for (Food food : badFoodCircularQueue)
            anchorPane.getChildren().remove(food.getShape());
        badFoodCircularQueue.clear();

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
                startChasingFood();
            }
        }
    }


    public void startChasingFood() {
        try {
            startTimer();
            for (Character pigeon : pigeonsList) {
                Vertex destination = graph.getVertexByLocation(foodCircularQueue.peek().getX(), foodCircularQueue.peek().getY());
                Vertex start = graph.getVertexByLocation(pigeon.getX(), pigeon.getY());

                pigeon.initPath(graph, start, destination);

                Thread thread = new Thread(pigeon);
                thread.start();

                pigeonThreads.put(pigeon, thread);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startScattering() {
        //TODO les pigeons qui se dispersent
    }

    public void stopMovement() {
        if (!pigeonThreads.isEmpty()) {
            for (Map.Entry<Character, Thread> threadEntry : pigeonThreads.entrySet())
                threadEntry.getValue().interrupt();

            pigeonThreads.clear();
        }
    }

    public boolean checkIfNoObstacles(int x, int y) {
        for (MapElement obstacle : obstaclesList)
            if (obstacle.getX() == x && obstacle.getY() == y)
                return false;
        return true;
    }


    public void startTimer() {
        if (timer != null) {
            timer.purge();
            timer.cancel();
        }

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (ACTION_DONE && !foodCircularQueue.isEmpty()) {
                            stopMovement();

                            Food badFood;
                            Food chasedFood = foodCircularQueue.pop();

                            if (chasedFood.getFoodState().equals(FoodState.GOOD))
                                anchorPane.getChildren().remove(chasedFood.getShape());
                            else if ((badFood = badFoodCircularQueue.pushAndPopExcedent(chasedFood)) != null)
                                anchorPane.getChildren().remove(badFood.getShape());

                            ACTION_DONE = false;
                            timer.cancel();

                            if (!foodCircularQueue.isEmpty())
                                startChasingFood();
                        }
                    }
                });
            }
        }, 0, 250);
    }
}

