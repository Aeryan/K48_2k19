package sample;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputManager {

    private float horizontal;
    private float vertical;
    private float zoom;
    private boolean startPointSelected;
    private boolean endPointSelected;


    public void handleInput(KeyEvent event){
        if (event.getCode() == KeyCode.W){
            if (event.getEventType() == KeyEvent.KEY_PRESSED)
                vertical = 1;
            else
                vertical = 0;
        }
        if (event.getCode() == KeyCode.S) {
            if (event.getEventType() == KeyEvent.KEY_PRESSED)
                vertical = -1;
            else
                vertical = 0;
        }
        if (event.getCode() == KeyCode.A) {
            if (event.getEventType() == KeyEvent.KEY_PRESSED)
                horizontal = 1;
            else
                horizontal = 0;
        }
        if (event.getCode() == KeyCode.D) {
            if (event.getEventType() == KeyEvent.KEY_PRESSED)
                horizontal = -1;
            else
                horizontal = 0;
        }

        if (event.getCode() == KeyCode.Q){
            if (event.getEventType() == KeyEvent.KEY_PRESSED)
                zoom = 1;
            else
                zoom = 0;
        }

        if (event.getCode() == KeyCode.E){
            if (event.getEventType() == KeyEvent.KEY_PRESSED)
                zoom = -1;
            else
                zoom = 0;
        }

        if (event.getCode() == KeyCode.Z){
            if (event.getEventType() == KeyEvent.KEY_PRESSED)
                startPointSelected = true;
            else
                startPointSelected = false;
        }

        if (event.getCode() == KeyCode.X){
            if (event.getEventType() == KeyEvent.KEY_PRESSED)
                endPointSelected = true;
            else
                endPointSelected = false;
        }
    }

    public float getHorizontal() {
        return horizontal;
    }

    public float getVertical() {
        return vertical;
    }

    public float getZoom() {
        return zoom;
    }

    public boolean isStartPointSelected() {

        return startPointSelected;
    }

    public boolean isEndPointSelected() {
        return endPointSelected;
    }
}
