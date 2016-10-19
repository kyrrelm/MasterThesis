package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.World;
import model.WorldState;
import sun.reflect.generics.tree.ReturnType;

import java.util.ArrayList;
import java.util.Random;

import static javafx.scene.input.KeyCode.T;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        int SIZE = 10;
        int height = SIZE;
        int width = SIZE;

        GridPane root = new GridPane();

        TextField[][] inputCells = new TextField[width][height];


        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){

                Random rand = new Random();
                int rand1 = rand.nextInt(2);

                // Create a new TextField in each Iteration
                TextField tf = new TextField();
                tf.setPrefHeight(50);
                tf.setPrefWidth(50);
                tf.setAlignment(Pos.CENTER);
                tf.setEditable(false);
                tf.setText("(" + y + ")");
                inputCells[x][y] = tf;
                // Iterate the Index using the loops
                root.setRowIndex(tf,y);
                root.setColumnIndex(tf,x);
                root.getChildren().add(tf);
            }
        }

        Scene scene = new Scene(root, 500, 500);
        primaryStage.setTitle("Random Binary Matrix (JavaFX)");
        primaryStage.setScene(scene);
        primaryStage.show();

        Simulator simulator = new Simulator(new World());
        simulator.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                ArrayList<WorldState> worldStates = (ArrayList<WorldState>) event.getSource().getValue();
                WorldState firstState = worldStates.get(0);
                System.out.println("WorldState:");
                System.out.println(firstState);

            }
        });
        Thread tr = new Thread(simulator);
        tr.setDaemon(true);
        tr.start();


    }


    public static void main(String[] args) {
        launch(args);
        //new World(300).runSim();
    }
}