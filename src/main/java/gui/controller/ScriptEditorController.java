package gui.controller;

import com.google.common.eventbus.Subscribe;
import gui.events.OpenScriptEvent;
import gui.factories.ScriptTabFactory;
import gui.model.ScriptContext;
import gui.model.Style;
import interpreter.debug.BreakpointReachedEvent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.apache.commons.io.FilenameUtils;
import org.fxmisc.richtext.StyledTextArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ScriptEditorController implements Initializable {
    private ScriptTabFactory scriptTabFactory;

    @FXML
    private TabPane scriptPane;

    @FXML
    private Button releaseBreakpointButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scriptTabFactory.initializeScriptPane(scriptPane);
    }

    @Subscribe
    public void openScript(OpenScriptEvent event) throws IOException {
        String scriptName = FilenameUtils.removeExtension(event.getData());
        Tab tab = scriptTabFactory.create(scriptName, scriptPane).getTab();
        scriptPane.getSelectionModel().select(tab);
    }

    @Subscribe
    public void onBreakpointReachedEvent(BreakpointReachedEvent event) {
        ScriptContext context = scriptTabFactory.create(event.getData().getSourceId(), scriptPane);
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

    @Autowired
    public void setScriptTabFactory(ScriptTabFactory scriptTabFactory) {
        this.scriptTabFactory = scriptTabFactory;
    }
}
