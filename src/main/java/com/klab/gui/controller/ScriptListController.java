package com.klab.gui.controller;

import com.google.common.eventbus.Subscribe;
import com.klab.common.EventService;
import com.klab.gui.events.CommandSubmittedEvent;
import com.klab.gui.events.OpenScriptEvent;
import com.klab.gui.service.ScriptViewService;
import com.klab.interpreter.core.events.ScriptChangeEvent;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
    protected void onScriptViewMouseClick(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            eventService.publish(new OpenScriptEvent(scriptView.getSelectionModel().getSelectedItem().getValue(), this));
        }
    }

    @FXML
    public void onRunScriptMenuAction(ActionEvent actionEvent) {
        String command = FilenameUtils.removeExtension(scriptView.getSelectionModel().getSelectedItem().getValue());
        LOGGER.info("Script double clicked. Submiting command: {}", command);
        eventService.publish(CommandSubmittedEvent.create().data(command).build(this));
    }

    @Subscribe
    public void onSciptFileChange(ScriptChangeEvent event) {
        Platform.runLater(() -> scriptView.setRoot(scriptViewService.listScripts()));
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
