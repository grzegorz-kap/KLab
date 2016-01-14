package gui.service;

import interpreter.core.ScriptFileService;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScriptViewService {
    private static Logger LOGGER = LoggerFactory.getLogger(ScriptViewService.class);

    private ScriptFileService scriptFileService;

    public String readScript(String scriptName) {
        try {
            return scriptFileService.readScript(scriptName);
        } catch (IOException e) {

            return "";
        }
    }

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

    @Autowired
    public void setScriptFileService(ScriptFileService scriptFileService) {
        this.scriptFileService = scriptFileService;
    }
}
