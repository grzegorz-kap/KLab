package gui.model.script;

import javafx.scene.control.Label;

public class LineNumberLabel extends Label {
    private CustomCodeArea customCodeArea;

    public LineNumberLabel() {

    }

    public void setCustomCodeArea(CustomCodeArea customCodeArea) {
        this.customCodeArea = customCodeArea;
    }
}

