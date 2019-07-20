package sample;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class MapController {

    private final float SPEED = 5;
    private final float ZOOM_SPEED = 0.1f;

    private float[] startPointPos = new float[]{-1, -1};
    private float[] endPointPos = new float[]{-1, -1};

    private Circle startCircle;
    private Circle endCircle;

    private InputManager inputManager = new InputManager();
    @FXML
    private Pane root;

    @FXML
    private Canvas canvas;

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

        drawWaypoint();
    }

    private void drawWaypoint(){
        if (inputManager.isStartPointSelected()) {
            Bounds pointBoundsOnMap = mapView.sceneToLocal(pointer.localToScene(pointer.getBoundsInLocal()));
            startPointPos[0] = (float) pointBoundsOnMap.getCenterX();
            startPointPos[1] = (float) pointBoundsOnMap.getCenterY();
        }
        if (startCircle != null)
            root.getChildren().removeAll(startCircle);
        startCircle = drawCircle(startPointPos);

        if (inputManager.isEndPointSelected()) {
            Bounds pointBoundsOnMap = mapView.sceneToLocal(pointer.localToScene(pointer.getBoundsInLocal()));
            endPointPos[0] = (float) pointBoundsOnMap.getCenterX();
            endPointPos[1] = (float) pointBoundsOnMap.getCenterY();
        }
        if (endCircle != null)
            root.getChildren().removeAll(endCircle);
        endCircle = drawCircle(endPointPos);


    }

    private Circle drawCircle(float[] pos) {
        if (pos[0] != -1 && pos[1] != -1){
            Point2D pointOnScene = root.sceneToLocal(mapView.localToScene(pos[0], pos[1]));
            Circle circle = new Circle(pointOnScene.getX(), pointOnScene.getY(), 5);
            root.getChildren().add(circle);
            return circle;
        }
        return null;
    }

    @FXML
    private void HandleKeyRelease(KeyEvent event){
        inputManager.handleInput(event);
    }
}
