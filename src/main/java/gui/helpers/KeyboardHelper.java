package gui.helpers;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.springframework.stereotype.Component;

@Component
public class KeyboardHelper {

    public boolean isEnterPressed(final KeyEvent keyEvent) {
        return keyEvent.getCode().equals(KeyCode.ENTER) && !keyEvent.isAltDown();
    }
}
