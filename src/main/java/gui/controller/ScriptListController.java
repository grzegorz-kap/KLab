package gui.controller;

import gui.events.CommandSubmittedEvent;
import gui.service.ScriptViewService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ScriptListController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptListController.class);

    private ScriptViewService scriptViewService;
    private ApplicationEventPublisher applicationEventPublisher;

    @FXML
    private TreeView<String> scriptView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scriptView.setRoot(scriptViewService.listScripts());
    }

    @FXML
    protected void onScriptViewMouseClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            String command = scriptView.getSelectionModel().getSelectedItem().getValue();
            LOGGER.info("Script double clicked. Submiting command: {}", command);
            applicationEventPublisher.publishEvent(new CommandSubmittedEvent(command, this));
        }
    }

    @Autowired
    public void setScriptViewService(ScriptViewService scriptViewService) {
        this.scriptViewService = scriptViewService;
    }

    @Autowired
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
