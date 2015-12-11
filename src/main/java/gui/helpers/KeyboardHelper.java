package gui.helpers;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyboardHelper {

    private KeyboardHelper() {
    }

    public static boolean isEnterPressed(final KeyEvent keyEvent) {
        return keyEvent.getCode().equals(KeyCode.ENTER) && !keyEvent.isShiftDown();
    }

    public static boolean isArrowUpPressed(KeyEvent keyEvent) {
        return keyEvent.getCode().equals(KeyCode.UP);
    }

    public static boolean isArrowDownPressed(KeyEvent keyEvent) {
        return keyEvent.getCode().equals(KeyCode.DOWN);
    }

    public static boolean isEnterShift(KeyEvent keyEvent) {
        return keyEvent.isShiftDown() && keyEvent.getCode().equals(KeyCode.ENTER);
    }
}
