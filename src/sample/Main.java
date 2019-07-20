package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {

    private static final int SCENEWIDTHX = 600;
    private static final int SCENEHEIGHTX = 300;
    private static final int ACTIONBUTTONLAYOUTX = 57;
    private static final int INFOBUTTONLAYOUTX = 120;
    private static final int INZOOMLABELLAYOUTX = 180;
    private static final int OUTZOOMLAYOUTX = 260;
    private static final int BACKBUTTONLAYOUTX = 0;
    private static final int BACKBUTTONLLAYOUTY = 0;

    private String greenStyle = "-fx-background-color:\n" +
            "        linear-gradient(#f0ff35, #a9ff00),\n" +
            "                radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%);\n" +
            "        -fx-background-radius: 6, 5;\n" +
            "        -fx-background-insets: 0, 1;\n" +
            "        -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );\n" +
            "        -fx-text-fill: #395306;";

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("map.fxml"));
        FXMLLoader secondLoader = new FXMLLoader(Main.class.getResource("sample.fxml"));
        Pane root =  loader.load();
        Pane secondRoot = secondLoader.load();
        primaryStage.setTitle("PathFinder");
        //Scene without path
        Scene scene = new Scene(root, SCENEWIDTHX, SCENEHEIGHTX);
        //Scene with path
        Scene scene1 = new Scene(secondRoot, SCENEWIDTHX, SCENEHEIGHTX);
        //Second scene back button
        Button backButton = new Button("Back E");
        //Getting first scene back
        backButton.setOnAction(e -> primaryStage.setScene(scene));
        backButton.setStyle(greenStyle);
        backButton.setLayoutY(BACKBUTTONLLAYOUTY);
        backButton.setLayoutX(BACKBUTTONLAYOUTX);
        secondRoot.getChildren().add(backButton);
        // First scene settings
        scene1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.E) {
                    primaryStage.setScene(scene);
                }
            }
        });
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if(e.getCode() == KeyCode.X) {
                    Platform.exit();
                }
                if (e.getCode() == KeyCode.P) {
                    primaryStage.setScene(scene1);
                }
            }
        });
        primaryStage.setScene(scene);
        Button exitBtn = new Button("Exit X");
        exitBtn.setStyle(greenStyle);
        exitBtn.setOnAction(e -> Platform.exit());
        Button actionButton = new Button("Path P");
        actionButton.setLayoutX(ACTIONBUTTONLAYOUTX);
        actionButton.setStyle(greenStyle);
        //Getting second scene
        actionButton.setOnAction(e -> primaryStage.setScene(scene1));
        Button infoButton = new Button("Info");
        infoButton.setLayoutX(INFOBUTTONLAYOUTX);
        infoButton.setStyle(greenStyle);
        Label in = new Label("Q to zoom in");
        Label out = new Label("E to zoom out");
        in.setStyle(greenStyle);
        out.setStyle(greenStyle);
        in.setLayoutX(INZOOMLABELLAYOUTX);
        out.setLayoutX(OUTZOOMLAYOUTX);
        root.getChildren().add(in);
        root.getChildren().add(out);
        root.getChildren().add(exitBtn);
        root.getChildren().add(actionButton);
        root.getChildren().add(infoButton);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
