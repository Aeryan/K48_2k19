package sample;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class MapController {

    private final float SPEED = 5;
    private final float ZOOM_SPEED = 0.1f;

    private InputManager inputManager = new InputManager();
    @FXML
    private Pane root;

    @FXML
    private Circle pointer;

    @FXML
    private ImageView mapView;

    public MapController() {
    }

    @FXML
    private void initialize()
    {
    }

    @FXML
    private void HandleKeyPress(KeyEvent event){
        inputManager.handleInput(event);
        //Move
        mapView.setTranslateX(mapView.getTranslateX() + inputManager.getHorizontal() * SPEED);
        mapView.setTranslateY(mapView.getTranslateY() + inputManager.getVertical() * SPEED);
        //Zoom
        mapView.setScaleX(mapView.getScaleX() + inputManager.getZoom() * ZOOM_SPEED);
        mapView.setScaleY(mapView.getScaleY() + inputManager.getZoom() * ZOOM_SPEED);
        // Pointer
        pointer.setLayoutX(root.getLayoutBounds().getMaxX() / 2f);
        pointer.setLayoutY(root.getLayoutBounds().getMaxY() / 2f);

    }

    @FXML
    private void HandleKeyRelease(KeyEvent event){
        inputManager.handleInput(event);
    }
}
