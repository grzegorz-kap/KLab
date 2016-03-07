package gui.controller;

import com.google.common.eventbus.Subscribe;
import common.EventService;
import gui.events.CommandSubmittedEvent;
import gui.events.OpenScriptEvent;
import gui.model.script.CustomCodeArea;
import gui.model.script.ScriptEditorPane;
import gui.model.script.ScriptTab;
import gui.service.ScriptViewService;
import interpreter.core.code.ScriptFileService;
import interpreter.debug.BreakpointEvent;
import interpreter.debug.BreakpointReachedEvent;
import interpreter.debug.BreakpointService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static interpreter.debug.BreakpointEvent.Operation.ADD;
import static interpreter.debug.BreakpointEvent.Operation.REMOVE;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ScriptEditorController implements Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptEditorController.class);
    private ScriptFileService scriptFileService;
    private ScriptViewService scriptViewService;
    private BreakpointService breakpointService;
    private EventService eventService;

    @FXML
    private ScriptEditorPane scriptPane;

    @FXML
    private Button releaseBreakpointButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scriptPane.setSaveScriptHandler(tab -> {
            try {
                scriptFileService.writeScript(tab.getText(), tab.getScriptContent());
            } catch (IOException e) {
                LOGGER.error("error", e);
            }
        });
        scriptPane.setRunScriptHandler(tab -> eventService.publish(new CommandSubmittedEvent(tab.getText(), this)));
    }

    @Subscribe
    public void openScript(OpenScriptEvent event) throws IOException {
        String scriptName = FilenameUtils.removeExtension(event.getData());
        ScriptTab tab = getScriptTab(scriptName);
        scriptPane.getSelectionModel().select(tab);
    }

    @Subscribe
    public void onBreakpointReachedEvent(BreakpointReachedEvent event) {
        ScriptTab tab = getScriptTab(event.getData().getSourceId());
        int line = event.getData().getAddress().getLine() - 1;
        releaseBreakpointButton.setOnMouseClicked(ev -> {
            try {
                event.getLock().lock();
                event.getData().setReleased(true);
                event.getReleased().signalAll();
                releaseBreakpointButton.setDisable(true);
                tab.getCodeArea().clearStyle(line);
            } finally {
                event.getLock().unlock();
            }
        });
        releaseBreakpointButton.setDisable(false);
        Platform.runLater(() -> {
            scriptPane.getSelectionModel().select(tab);
            CustomCodeArea area = tab.getCodeArea();
            area.setStyle(line, () -> "-fx-font-weight: bold");
        });
    }

    private ScriptTab getScriptTab(String scriptName) {
        ScriptTab tab = scriptPane.getScript(Objects.requireNonNull(scriptName));
        if (tab == null) {
            tab = new ScriptTab(scriptName, scriptViewService.readScript(scriptName));
            scriptPane.addScript(scriptName, tab);
            tab.getCodeArea().setBreakingPoints(breakpointService.linesFor(scriptName));
            tab.setOnRunHandler(t -> eventService.publish(new CommandSubmittedEvent(t.getScriptName(), this)));
            tab.setOnCloseHandler(t -> scriptPane.remove(t.getScriptName()));
            tab.getCodeArea().setBreakPointAddedHandler(number -> eventService.publish(BreakpointEvent.create(scriptName, number + 1, ADD, this)));
            tab.getCodeArea().setBreakPointRemovedHandler(number -> eventService.publish(BreakpointEvent.create(scriptName, number + 1, REMOVE, this)));
        }
        return tab;
    }

    @Autowired
    public void setScriptFileService(ScriptFileService scriptFileService) {
        this.scriptFileService = scriptFileService;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    public void setScriptViewService(ScriptViewService scriptViewService) {
        this.scriptViewService = scriptViewService;
    }

    @Autowired
    public void setBreakpointService(BreakpointService breakpointService) {
        this.breakpointService = breakpointService;
    }
}
