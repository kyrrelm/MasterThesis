package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import maps.Map;
import maps.MapGenerator;
import model.World;
import model.states.WorldState;

import java.security.Key;
import java.util.ArrayList;

public class Main extends Application {

    private static final int SIZE = 30;
    private static final int  NUMBER_OF_TICKS = 10000;
    private static javafx.util.Duration FREQUENCY = Duration.millis(500);

    private static int width = SIZE*2;
    private static int height = SIZE;

    private static int playBackIndex = 0;
    private static Label[][] outputCells;
    private static ArrayList<WorldState> worldStates = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception{

        Map map = MapGenerator.genMap(MapGenerator.OBSTACLE_TEST2);
        width = map.sizeX;
        height = map.sizeY;
        outputCells = new Label[width][height];
        Simulator simulator = initSimulation(map);

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


        Scene scene = new Scene(root, 1800, 1300);
        primaryStage.setTitle("Thing");
        primaryStage.setScene(scene);
        primaryStage.show();

        startSimulation(simulator);
        Timeline timeline = runPlayback();
        sliderSetup(root, timeline);
    }

    private void sliderSetup(GridPane root, Timeline timeline) {
        Slider slider = new Slider();
        slider.setMin(1);
        slider.setMax(100);
        slider.setValue(FREQUENCY.toMillis()/10);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);
        slider.setPrefWidth(300);
        root.setRowIndex(slider,height);
        root.setColumnIndex(slider,width);
        root.getChildren().addAll(slider);

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (!slider.isValueChanging()){
                    changeTimer(timeline, Duration.millis(newValue.doubleValue()*10));
                }
            }
        });

        slider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasChanging, Boolean isNowChanging) {
                if (! isNowChanging) {
                    changeTimer(timeline, Duration.millis(slider.getValue()*10));
                }
            }
        });
    }

    private Timeline runPlayback() {
        KeyFrame mainFrame = new KeyFrame(
                Duration.ZERO,
                actionEvent -> {
                    if (worldStates.size() > playBackIndex){
                        WorldState worldState = worldStates.get(playBackIndex);
                        for (int x = 0; x < worldState.cellStates.length; x++) {
                            for (int y = 0; y < worldState.cellStates[0].length; y++) {
                                if (worldState.cellStates[x][y].toString().equals("X")){
                                    outputCells[x][y].setStyle("-fx-background-color: gray");
                                    outputCells[x][y].setText("");
                                }
                                else {
                                    outputCells[x][y].setText(worldState.cellStates[x][y].toString());
                                }
                            }
                        }
                        playBackIndex++;
                    }
                }
        );
        KeyFrame pause = new KeyFrame(FREQUENCY);
        Timeline timeline = new Timeline(mainFrame,pause);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        return timeline;
    }

    private void changeTimer(Timeline timeline, final Duration timerInterval) {
        KeyFrame mainFrame = timeline.getKeyFrames().get(0);
        KeyFrame pause = new KeyFrame(timerInterval);
        timeline.stop();
        timeline.getKeyFrames().setAll(mainFrame, pause);
        timeline.play();
    }



    private Simulator initSimulation(Map map) {
        return new Simulator(new World(map), NUMBER_OF_TICKS);
        //return new Simulator(new World(width, height), NUMBER_OF_TICKS);
    }

    private void startSimulation(Simulator simulator){
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