package gui.controller;

import com.google.common.eventbus.Subscribe;
import common.EventService;
import gui.events.CommandSubmittedEvent;
import gui.events.OpenScriptEvent;
import gui.model.CustomTab;
import gui.service.ScriptViewService;
import interpreter.core.ScriptFileService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import org.apache.commons.io.FilenameUtils;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.IntFunction;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ScriptEditorController implements Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptEditorController.class);
    private ScriptFileService scriptFileService;
    private ScriptViewService scriptViewService;
    private EventService eventService;
    private Map<String, CustomTab> tabs = new HashMap<>();

    @FXML
    private TabPane scriptPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scriptPane.setContextMenu(new ContextMenu(new MenuItem("Text")));
        scriptPane.setOnKeyPressed(event -> {
            CustomTab tab = (CustomTab) scriptPane.getSelectionModel().getSelectedItem();
            if (event.getCode().equals(KeyCode.S) && event.isControlDown() && Objects.nonNull(tab)) {
                saveScript(tab);
            }
            if (event.getCode().equals(KeyCode.F5) && Objects.nonNull(tab)) {
                eventService.publish(new CommandSubmittedEvent(tab.getText(), this));
            }
        });
    }

    public void saveScript(CustomTab tab) {
        try {
            scriptFileService.writeScript(tab.getText(), tab.getCodeArea().getText());
        } catch (IOException ignored) {
        }
    }

    @Subscribe
    public void openScript(OpenScriptEvent event) throws IOException {
        String script = FilenameUtils.removeExtension(event.getData());
        CustomTab tab = tabs.get(script);
        if (tab == null) {
            tabs.put(script, tab = new CustomTab(script));
            CodeArea codeArea = new CodeArea(scriptViewService.readScript(script));
            tab.setCodeArea(codeArea);

            IntFunction<Node> lineNumberFactory = LineNumberFactory.get(codeArea);

            codeArea.setParagraphGraphicFactory(number -> {
                Node node = lineNumberFactory.apply(number);
                node.setOnMouseClicked(ev -> LOGGER.info("{}", ((Label) node).getText()));
                return node;
            });

            codeArea.setOnMouseClicked(ev -> {
                LOGGER.info("{}", ev.getSource());
            });


            ContextMenu contextMenu = new ContextMenu();
            MenuItem run = new MenuItem("Run");
            run.setOnAction(ev -> eventService.publish(new CommandSubmittedEvent(script, this)));
            contextMenu.getItems().add(run);

            MenuItem close = new MenuItem("Close");
            close.setOnAction(ev -> scriptPane.getTabs().remove(tabs.remove(script)));
            contextMenu.getItems().add(close);

            tab.setContextMenu(contextMenu);

            scriptPane.getTabs().addAll(tab);
        }
        scriptPane.getSelectionModel().select(tab);
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

}
