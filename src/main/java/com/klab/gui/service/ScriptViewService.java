package com.klab.gui.service;

import javafx.scene.control.TreeItem;

import java.io.IOException;
import java.util.Optional;

public interface ScriptViewService {
    String readScript(String scriptName);

    TreeItem<String> listScripts();

    void createNewScriptDialog() throws IOException;

    Optional<String> renameScript(String oldName) throws IOException;

    boolean deleteScript(String value) throws IOException;
}
