package gui.model.script;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;

import java.util.function.Consumer;

public class ScriptTab extends Tab {
    private CustomCodeArea codeArea;
    private String scriptName;
    private Consumer<ScriptTab> onRunHandler;
    private Consumer<ScriptTab> onCloseHandler;

    public ScriptTab(String script, String content) {
        super(script);
        this.scriptName = script;
        setContent(codeArea = new CustomCodeArea(content, this));
        buildContextMenu();
    }

    private void buildContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem run = new MenuItem("Run");
        run.setOnAction(e -> onRunHandler.accept(this));
        contextMenu.getItems().add(run);

        MenuItem close = new MenuItem("Close");
        close.setOnAction(e -> onCloseHandler.accept(this));
        contextMenu.getItems().add(close);

        setContextMenu(contextMenu);
    }

    public String getScriptContent() {
        return codeArea.getText();
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setOnCloseHandler(Consumer<ScriptTab> onCloseHandler) {
        this.onCloseHandler = onCloseHandler;
    }

    public void setOnRunHandler(Consumer<ScriptTab> onRunHandler) {
        this.onRunHandler = onRunHandler;
    }
}
