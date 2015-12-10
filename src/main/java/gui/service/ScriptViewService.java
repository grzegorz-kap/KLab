package gui.service;

import interpreter.core.ScriptFileService;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

@Service
public class ScriptViewService {

    private static Logger LOGGER = LoggerFactory.getLogger(ScriptViewService.class);

    private ScriptFileService scriptFileService;

    public TreeItem<String> listScripts() {
        TreeItem<String> root = new TreeItem<>("Working directory");
        ObservableList<TreeItem<String>> children = root.getChildren();
        try {
            scriptFileService.listScripts().stream()
                    .map(path -> new TreeItem<>(path.getFileName().toString()))
                    .forEach(children::add);
            children.sort(Collections.reverseOrder(Comparator.comparing(TreeItem::getValue)));
        } catch (IOException e) {
            LOGGER.error("Error loading script list", e);
        }
        root.setExpanded(true);
        return root;
    }

    @Autowired
    public void setScriptFileService(ScriptFileService scriptFileService) {
        this.scriptFileService = scriptFileService;
    }
}
