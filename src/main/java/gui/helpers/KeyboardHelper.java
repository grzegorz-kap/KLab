package gui.helpers;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.springframework.stereotype.Component;

@Component
public class KeyboardHelper {

    public boolean isEnterPressed(final KeyEvent keyEvent) {
        return keyEvent.getCode().equals(KeyCode.ENTER) && !keyEvent.isShiftDown();
    }

    public boolean isArrowUpPressed(KeyEvent keyEvent) {
        return keyEvent.getCode().equals(KeyCode.UP);
    }

    public boolean isArrowDownPressed(KeyEvent keyEvent) {
        return keyEvent.getCode().equals(KeyCode.DOWN);
    }

    public boolean isEnterShift(KeyEvent keyEvent) {
        return keyEvent.isShiftDown() && keyEvent.getCode().equals(KeyCode.ENTER);
    }
}
