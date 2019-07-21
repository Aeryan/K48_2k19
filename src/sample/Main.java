package sample;

import javafx.application.Application;
import javafx.application.Platform;
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
    private static final int ACTIONBUTTONLAYOUTX = 60;
    private static final int INFOBUTTONLAYOUTX = 120;
    private static final int INZOOMLABELLAYOUTX = 180;
    private static final int OUTZOOMLAYOUTX = 260;

    private String greenStyle = "-fx-background-color:\n" +
            "        linear-gradient(#f0ff35, #a9ff00),\n" +
            "                radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%);\n" +
            "        -fx-background-radius: 6, 5;\n" +
            "        -fx-background-insets: 0, 1;\n" +
            "        -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );\n" +
            "        -fx-text-fill: #395306;";

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("map2.fxml"));
        Pane root =  loader.load();
        primaryStage.setTitle("pathfinder.calculations.PathFinder");
        Scene scene = new Scene(root, SCENEWIDTHX, SCENEHEIGHTX);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if(e.getCode() == KeyCode.ESCAPE) {
                    Platform.exit();
                }
            }
        });
        primaryStage.setScene(scene);
        Button exitBtn = new Button("Exit X");
        exitBtn.setStyle(greenStyle);
        exitBtn.setOnAction(e -> Platform.exit());
        Button actionButton = new Button("Path");
        actionButton.setLayoutX(ACTIONBUTTONLAYOUTX);
        actionButton.setStyle(greenStyle);
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
