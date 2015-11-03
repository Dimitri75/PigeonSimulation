package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Pigeon Simulation");
        Scene scene = new Scene(root, 1200, 700);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

//        primaryStage.setTitle("Timeline Example");
//
//        Group root = new Group();
//        Scene theScene = new Scene( root );
//        primaryStage.setScene(theScene);
//
//        Canvas canvas = new Canvas( 512, 512 );
//        root.getChildren().add( canvas );
//
//
//
//        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
