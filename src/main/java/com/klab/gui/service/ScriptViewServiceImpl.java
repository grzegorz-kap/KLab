package com.klab.gui.service;

import com.klab.common.EventService;
import com.klab.gui.events.OpenScriptEvent;
import com.klab.interpreter.core.code.ScriptFileService;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
class ScriptViewServiceImpl implements ScriptViewService {
    private static Logger LOGGER = LoggerFactory.getLogger(ScriptViewServiceImpl.class);
    private EventService eventService;
    private ScriptFileService scriptFileService;

    @Override
    public String readScript(String scriptName) {
        try {
            return scriptFileService.readScript(scriptName);
        } catch (IOException e) {

            return "";
        }
    }

    @Override
    public TreeItem<String> listScripts() {
        TreeItem<String> root = new TreeItem<>("Working directory");
        ObservableList<TreeItem<String>> children = root.getChildren();
        getScriptNames().stream()
                .map(TreeItem::new)
                .forEach(children::add);
        children.sort(Collections.reverseOrder(Comparator.comparing(TreeItem::getValue)));
        root.setExpanded(true);
        return root;
    }

    private List<String> getScriptNames() {
        try {
            return scriptFileService.listScripts().stream()
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error("Error loading script list", e);
        }
        return new ArrayList<>();
    }

    @Override
    public void createNewScriptDialog() throws IOException {
        TextInputDialog dialog = new TextInputDialog("script" + System.currentTimeMillis());
        dialog.setTitle("Script name");
        dialog.setHeaderText("Script or function name");
        dialog.setContentText("Name:");
        String name = dialog.showAndWait().orElse(null);
        if (name != null) {
            if (!scriptFileService.exists(name)) {
                scriptFileService.writeScript(name, "");
            }
            eventService.publish(new OpenScriptEvent(name, this));
        }
    }

    @Override
    public Optional<String> renameScript(String oldName) throws IOException {
        TextInputDialog dialog = new TextInputDialog(oldName);
        dialog.setTitle("Change script name");
        dialog.setHeaderText("New name for script: '" + oldName + "'");
        dialog.setContentText("Name: ");
        Optional<String> name = dialog.showAndWait();
        if (name.isPresent()) {
            scriptFileService.rename(oldName, name.get());
        }
        return name;
    }

    @Override
    public boolean deleteScript(String value) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to remove'" + value + "'?");
        alert.setTitle("Confirm script delete");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        return result == ButtonType.OK && scriptFileService.removeScript(value);
    }

    @Autowired
    public void setScriptFileService(ScriptFileService scriptFileService) {
        this.scriptFileService = scriptFileService;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}
