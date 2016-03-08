package gui.controller;

import com.google.common.collect.Maps;
import com.google.common.eventbus.Subscribe;
import common.EventService;
import gui.events.CommandSubmittedEvent;
import gui.events.OpenScriptEvent;
import gui.model.ScriptContext;
import gui.model.Style;
import gui.service.ScriptViewService;
import interpreter.core.code.ScriptFileService;
import interpreter.debug.BreakpointReachedEvent;
import interpreter.debug.BreakpointService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import org.apache.commons.io.FilenameUtils;
import org.fxmisc.richtext.StyledTextArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ScriptEditorController implements Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptEditorController.class);
    private ScriptFileService scriptFileService;
    private ScriptViewService scriptViewService;
    private BreakpointService breakpointService;
    private EventService eventService;
    private Map<String, ScriptContext> scripts = Maps.newHashMap();

    @FXML
    private TabPane scriptPane;

    @FXML
    private Button releaseBreakpointButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scriptPane.setOnKeyPressed(event -> {
            Tab tab = scriptPane.getSelectionModel().getSelectedItem();
            if (tab == null) {
                return;
            }
            if (event.getCode().equals(KeyCode.S) && event.isControlDown()) {
                try {
                    scriptFileService.writeScript(tab.getText(), getScriptContext(tab.getText()).getText());
                } catch (IOException e) {
                    LOGGER.error("error", e);
                }
            }
            if (event.getCode().equals(KeyCode.F5)) {
                eventService.publish(new CommandSubmittedEvent(tab.getText(), this));
            }
        });
    }

    @Subscribe
    public void openScript(OpenScriptEvent event) throws IOException {
        String scriptName = FilenameUtils.removeExtension(event.getData());
        Tab tab = getScriptContext(scriptName).getTab();
        scriptPane.getSelectionModel().select(tab);
    }

    @Subscribe
    public void onBreakpointReachedEvent(BreakpointReachedEvent event) {
        ScriptContext context = getScriptContext(event.getData().getSourceId());
        int line = event.getData().getAddress().getLine() - 1;
        releaseBreakpointButton.setOnMouseClicked(ev -> {
            try {
                event.getLock().lock();
                event.getData().setReleased(true);
                event.getReleased().signalAll();
                releaseBreakpointButton.setDisable(true);
                context.getCodeArea().clearStyle(line);
            } finally {
                event.getLock().unlock();
            }
        });
        releaseBreakpointButton.setDisable(false);
        Platform.runLater(() -> {
            scriptPane.getSelectionModel().select(context.getTab());
            StyledTextArea<Style> area = context.getCodeArea();
            area.setStyle(line, () -> "-fx-font-weight: bold");
        });
    }

    private ScriptContext getScriptContext(String scriptName) {
        ScriptContext context = scripts.get(scriptName);
        if (context == null) {
            context = new ScriptContext(scriptName, scriptViewService.readScript(scriptName));
            context.createLineNumbersFactory(breakpointService);
            context.setOnRunHandler(t -> eventService.publish(new CommandSubmittedEvent(scriptName, this)));
            context.setOnCloseHandler(t -> {
                scriptPane.getTabs().remove(t.getTab());
                scripts.remove(scriptName);
            });
            scripts.put(scriptName, context);
            scriptPane.getTabs().add(context.getTab());
        }
        return context;
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
