package com.klab.gui.factories;

import com.klab.gui.model.ScriptContext;
import javafx.scene.control.TabPane;

public interface ScriptTabFactory {
    ScriptContext create(String scriptName, TabPane scriptPane);

    void initializeScriptPane(TabPane scriptPane);
}
