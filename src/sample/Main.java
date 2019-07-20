package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.security.Key;

public class Main extends Application {

    public String pictureName = "somemap.png";

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
        Pane root =  loader.load();
        primaryStage.setTitle("PathFinder");
        Scene scene = new Scene(root, 500, 300);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if(e.getCode() == KeyCode.X) {
                    Platform.exit();
                }
            }
        });
        primaryStage.setScene(scene);
        Button exitBtn = new Button("Exit X");
        exitBtn.setStyle(greenStyle);
        exitBtn.setOnAction(e -> Platform.exit());
        Button actionButton = new Button("Path");
        actionButton.setLayoutX(60);
        actionButton.setStyle(greenStyle);
        root.getChildren().add(exitBtn);
        root.getChildren().add(actionButton);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
