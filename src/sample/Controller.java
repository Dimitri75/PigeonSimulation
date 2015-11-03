package sample;

import classes.CircularQueue;
import classes.Food;
import classes.Pigeon;
import enumerations.FoodState;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.*;

public class Controller {
    @FXML
    private TextField textField_nbPigeons;
    @FXML
    private Button button_nbPigeons;
    @FXML
    private Label label_error;
    @FXML
    private AnchorPane gridPane;

    private CircularQueue<Food> foodCircularQueue = new CircularQueue(5);
    private List<Pigeon> pigeonList = new ArrayList<>();

    public Controller(){
        //init();
    }

    public void init(){
        for (int i = 0; i < 100; i++) {
//            gridPane.addColumn(i);
        }
        for (int i = 0; i < 100; i++) {
//            gridPane.addRow(i);
        }
//        gridPane.
    }

    @FXML
    public void nbPigeonsChosen() {
        init();
        try {
            int nbPigeons = Integer.parseInt(textField_nbPigeons.getText());
            removeAllPigeons();
            label_error.setText("");

            Random random = new Random();
            Pigeon pigeon;
            for (int i = 0; i < nbPigeons; i++) {
                pigeon = new Pigeon(random.nextInt(1100), random.nextInt(500)); //TODO à changer
                gridPane.getChildren().add(pigeon.getBody());
                pigeonList.add(pigeon);
            }
        } catch (NumberFormatException e) {
            label_error.setText("Vous devez saisir un nombre entier.");
        } catch (Exception e) {
            label_error.setText("Bravo ! Maintenant c'est cassé. :(");
        }

//        canvas
//        GraphicsContext gc = gridPane.getGraphicsContext2D();
//
//        Image earth = new Image( "res/images/blue_bird.png" );
//        Image sun   = new Image( "res/images/brown_bird.png" );
//        Image space = new Image( "res/images/green_bird.png" );
//
//        final long startNanoTime = System.nanoTime();
//
//        new AnimationTimer()
//        {
//            public void handle(long currentNanoTime)
//            {
//                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
//
//                double x = 232 + 128 * Math.cos(t);
//                double y = 232 + 128 * Math.sin(t);
//
//                // background image clears canvas
//                gc.drawImage( space, 0, 0 );
//                gc.drawImage( earth, x, y );
//                gc.drawImage( sun, 196, 196 );
//            }
//        }.start();
    }

    @FXML
    void onPressEnter(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            button_nbPigeons.fire();
        }
    }

    public void removeAllPigeons(){
        for (Pigeon pigeon : pigeonList){
            gridPane.getChildren().remove(pigeon.getBody());
        }
        pigeonList.clear();
    }

    @FXML
    public void putFood(MouseEvent e){
        if (!foodCircularQueue.isEmpty()) {
            foodCircularQueue.peek().setFoodState(FoodState.BAD);
        }

        Food food = new Food((int) e.getSceneX(), (int) e.getSceneY());
        gridPane.getChildren().add(food.getBody());

        Food excedent = foodCircularQueue.pushAndPopExcedent(food);
        if (excedent != null)
            gridPane.getChildren().remove(excedent.getBody());

        for (Pigeon pigeon : pigeonList){
            pigeon.foodSeen(food.getLocation().getX(), food.getLocation().getY());
        }
    }

    public Node getNodeByRowColumnIndex(int row, int column, GridPane gridPane) {
        for(Node node : gridPane.getChildren()) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column)
                return node;
        }
        return null;
    }
}

