package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("map.fxml"));
        Pane root =  loader.load();
        primaryStage.setTitle("PathFinder");
        primaryStage.setScene(new Scene(root, 300, 275));
        Button exitBtn = new Button("Exit X");
        exitBtn.setOnAction(e -> Platform.exit());
        root.getChildren().add(exitBtn);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
