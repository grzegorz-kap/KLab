package gui.controller;

import common.EventService;
import gui.events.CommandSubmittedEvent;
import gui.service.ScriptViewService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ScriptListController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptListController.class);

    private ScriptViewService scriptViewService;
    private EventService eventService;

    @FXML
    private TreeView<String> scriptView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scriptView.setRoot(scriptViewService.listScripts());
    }

    @FXML
    protected void onScriptViewMouseClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            //TODO open script editor
        }
    }

    @FXML
    public void onRunScriptMenuAction(ActionEvent actionEvent) {
        emitCommandSubmitEvent();
    }

    public void emitCommandSubmitEvent() {
        String command = scriptView.getSelectionModel().getSelectedItem().getValue();
        int dotIndex = command.lastIndexOf('.');
        command = dotIndex != -1 ? command.substring(0, dotIndex) : command;
        LOGGER.info("Script double clicked. Submiting command: {}", command);
        eventService.publish(new CommandSubmittedEvent(command, this));
    }

    @Autowired
    public void setScriptViewService(ScriptViewService scriptViewService) {
        this.scriptViewService = scriptViewService;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}
