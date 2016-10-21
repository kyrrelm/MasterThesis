package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.World;
import model.states.WorldState;

import java.util.ArrayList;

public class Main extends Application {

    private static final int SIZE = 30;
    private static final int  NUMBER_OF_TICKS = 120;
    private static final javafx.util.Duration FREQUENCY = Duration.millis(500);

    private static int width = SIZE*2;
    private static int height = SIZE;

    private static int playBackIndex = 0;
    private static Label[][] outputCells = new Label[width][height];
    private static ArrayList<WorldState> worldStates = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception{

        GridPane root = new GridPane();

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){

                Label outputCell = new Label("-");
                outputCell.setPrefHeight(20);
                outputCell.setPrefWidth(20);
                outputCell.setAlignment(Pos.CENTER);
                outputCell.setStyle("-fx-border-color: lightgray;");
                // Create a new TextField in each Iteration
//                TextField tf = new TextField();
//                tf.setPrefHeight(50);
//                tf.setPrefWidth(50);
//                tf.setAlignment(Pos.CENTER);
//                tf.setEditable(false);
//                tf.setText("---");

                outputCells[x][y] = outputCell;


                // Iterate the Index using the loops
                root.setRowIndex(outputCell,height-1-y);
                root.setColumnIndex(outputCell,x);
                root.getChildren().add(outputCell);
            }
        }

        Scene scene = new Scene(root, 900, 500);
        primaryStage.setTitle("Random Binary Matrix (JavaFX)");
        primaryStage.setScene(scene);
        primaryStage.show();

        initSimulation();
        runPlayback();


    }

    private void runPlayback() {
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new EventHandler<ActionEvent>() {
                            @Override public void handle(ActionEvent actionEvent) {
                                if (worldStates.size() > playBackIndex){
                                    WorldState worldState = worldStates.get(playBackIndex);
                                    for (int x = 0; x < worldState.cellStates.length; x++) {
                                        for (int y = 0; y < worldState.cellStates[0].length; y++) {
                                            outputCells[x][y].setText(worldState.cellStates[x][y].toString());
                                        }
                                    }
                                    playBackIndex++;
                                }
                            }
                        }
                ),
                new KeyFrame(
                        FREQUENCY
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void initSimulation() {
        Simulator simulator = new Simulator(new World(width, height), NUMBER_OF_TICKS);
        simulator.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                ArrayList<WorldState> newWorldStates = (ArrayList<WorldState>) event.getSource().getValue();
                worldStates.addAll(newWorldStates);
//                int count = 0;
//                for (WorldState w : worldStates) {
//                    System.out.println("WorldState "+count+++":");
//                    System.out.println(w);
//                }
            }
        });
        simulator.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                simulator.getException().printStackTrace();
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