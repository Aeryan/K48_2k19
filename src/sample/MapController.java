package sample;

import calculations.Sampler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;


public class MapController {

    private final float SPEED = 5;
    private final float ZOOM_SPEED = 0.1f;

    private float[] startPointPos = new float[]{-1, -1};
    private float[] endPointPos = new float[]{-1, -1};

    private Circle startCircle;
    private Circle endCircle;

    private InputManager inputManager = new InputManager();

    private double[] path;
    private Double[] rawPath;

    private Polyline drawnPath;

    private boolean pathChanged;

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
        //Zoom to pointer
        zoom();
        // Pointer
        pointer.setLayoutX(root.getLayoutBounds().getMaxX() / 2f);
        pointer.setLayoutY(root.getLayoutBounds().getMaxY() / 2f);

        drawWaypoint();
        drawPath();
    }

    private void zoom(){
        final Bounds before = mapView.sceneToLocal(pointer.localToScene(pointer.getBoundsInLocal()));
        final Point2D beforePoint = new Point2D(before.getCenterX(), before.getCenterY());
        mapView.setScaleX(mapView.getScaleX() + inputManager.getZoom() * ZOOM_SPEED);
        mapView.setScaleY(mapView.getScaleY() + inputManager.getZoom() * ZOOM_SPEED);
        final Bounds after = mapView.sceneToLocal(pointer.localToScene(pointer.getBoundsInLocal()));
        final Point2D afterPoint = new Point2D(after.getCenterX(), after.getCenterY());
        final Point2D dif = beforePoint.subtract(afterPoint);
        mapView.setTranslateX(mapView.getTranslateX() - dif.getX() * mapView.getScaleX());
        mapView.setTranslateY(mapView.getTranslateY() - dif.getY() * mapView.getScaleX());
    }

    private void drawWaypoint(){
        if (inputManager.isStartPointSelected()) {
            Bounds pointBoundsOnMap = mapView.sceneToLocal(pointer.localToScene(pointer.getBoundsInLocal()));
            startPointPos[0] = (float) pointBoundsOnMap.getCenterX();
            startPointPos[1] = (float) pointBoundsOnMap.getCenterY();
            pathChanged = true;
        }
        if (startCircle != null)
            root.getChildren().removeAll(startCircle);
        startCircle = drawCircle(startPointPos, Color.GREEN);

        if (inputManager.isEndPointSelected()) {
            Bounds pointBoundsOnMap = mapView.sceneToLocal(pointer.localToScene(pointer.getBoundsInLocal()));
            endPointPos[0] = (float) pointBoundsOnMap.getCenterX();
            endPointPos[1] = (float) pointBoundsOnMap.getCenterY();
            pathChanged = true;
        }
        if (endCircle != null)
            root.getChildren().removeAll(endCircle);
        endCircle = drawCircle(endPointPos, Color.RED);


    }

    private void drawPath(){
        if (startPointPos[0] >= 0 && startPointPos[1] >= 0 && endPointPos[0] >= 0 && endPointPos[1] >= 0){
            if (pathChanged) {
                rawPath = Sampler.main(1000, 10, (int) startPointPos[0], (int) startPointPos[1], (int) endPointPos[0], (int) endPointPos[1]).toArray(new Double[0]);
                pathChanged = false;
            }
            path = transformPoints(rawPath);
            if (drawnPath != null)
                root.getChildren().removeAll(drawnPath);
            if (path != null && path.length != 0){
                drawnPath = new Polyline(path);
                root.getChildren().add(drawnPath);
            }
        }
    }

    private Circle drawCircle(float[] pos, Color paint) {
        if (pos[0] != -1 && pos[1] != -1){
            Point2D pointOnScene = root.sceneToLocal(mapView.localToScene(pos[0], pos[1]));
            Circle circle = new Circle(pointOnScene.getX(), pointOnScene.getY(), 5, paint);
            root.getChildren().add(circle);
            return circle;
        }
        return null;
    }

    private double[] transformPoints(Double[] data){
        int i = 0;
        double[] result = new double[data.length];
        while (i < data.length){
            Point2D pointOnScene = root.sceneToLocal(mapView.localToScene(data[i], data[i + 1]));
            result[i] = pointOnScene.getX();
            result[i + 1] = pointOnScene.getY();
            i += 2;
        }
        return result;
    }

    @FXML
    private void HandleKeyRelease(KeyEvent event){
        inputManager.handleInput(event);
    }
}
