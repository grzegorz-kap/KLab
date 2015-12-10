package gui.controller;

import interpreter.core.ScriptFileService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ScriptListController implements Initializable {

    @Autowired
    private ScriptFileService scriptFileService;

    @FXML
    private TreeView<String> scriptView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TreeItem<String> root = new TreeItem<>("Working directory");
        ObservableList<TreeItem<String>> children = root.getChildren();
        try {
            scriptFileService.listScripts().stream()
                    .map(path -> new TreeItem<>(path.getFileName().toString()))
                    .forEach(children::add);
            children.sort(Collections.reverseOrder(Comparator.comparing(TreeItem::getValue)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        root.setExpanded(true);
        scriptView.setRoot(root);
    }
}
