package gui.controller;

import com.google.common.eventbus.Subscribe;
import common.EventService;
import gui.events.CommandSubmittedEvent;
import gui.events.OpenScriptEvent;
import interpreter.core.ScriptFileService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.apache.commons.io.FilenameUtils;
import org.fxmisc.richtext.CodeArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ScriptEditorController implements Initializable {
    private ScriptFileService scriptFileService;
    private EventService eventService;

    private Map<String, Tab> tabs = new HashMap<>();

    @FXML
    private TabPane scriptPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scriptPane.setContextMenu(new ContextMenu(new MenuItem("Text")));
    }

    @Subscribe
    public void openScript(OpenScriptEvent event) throws IOException {
        String script = FilenameUtils.removeExtension(event.getData());
        Tab tab = tabs.get(script);
        if (tab == null) {
            tabs.put(script, tab = new Tab(script));
            tab.setContent(new CodeArea(scriptFileService.readScript(script)));

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
}
