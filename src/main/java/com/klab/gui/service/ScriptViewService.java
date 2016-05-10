package com.klab.gui.service;

import javafx.scene.control.TreeItem;

import java.io.IOException;

public interface ScriptViewService {
    String readScript(String scriptName);

    TreeItem<String> listScripts();

    void createNewScriptDialog() throws IOException;
}
