package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class Controller {

    @FXML
    private ImageView mapView;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Pane root;

    private final float scaleFactor = 0.05f;

    public Controller() {
    }

    @FXML
    private void initialize()
    {
    }

    @FXML
    private void HandleKeyPress(KeyEvent event){
        if (event.getCode() == KeyCode.Q){
            updateScale(1 + scaleFactor);
        } else if (event.getCode() == KeyCode.E) {
            updateScale(1 - scaleFactor);
        }
        System.out.println(mapView.getBoundsInLocal().getCenterX());
    }

    private void updateScale(double scale){
        mapView.setScaleX(mapView.getScaleX() * scale);
        mapView.setScaleY(mapView.getScaleY() * scale);
    }

}