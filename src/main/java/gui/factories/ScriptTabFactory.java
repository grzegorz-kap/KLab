package gui.factories;

import gui.model.ScriptContext;
import javafx.scene.control.TabPane;

public interface ScriptTabFactory {
    ScriptContext create(String scriptName, TabPane scriptPane);

    void initializeScriptPane(TabPane scriptPane);
}
