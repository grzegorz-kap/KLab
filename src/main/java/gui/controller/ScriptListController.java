package gui.controller;

import gui.service.ScriptViewService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ScriptListController implements Initializable {
    @Autowired
    private ScriptViewService scriptViewService;

    @FXML
    private TreeView<String> scriptView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scriptView.setRoot(scriptViewService.listScripts());
    }
}
