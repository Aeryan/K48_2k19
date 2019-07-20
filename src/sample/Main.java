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

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("map.fxml"));
        Pane root =  loader.load();
        primaryStage.setTitle("PathFinder");
        Scene scene = new Scene(root, 300, 275);
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
        exitBtn.setOnAction(e -> Platform.exit());
        root.getChildren().add(exitBtn);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
