package gui.model.script;

import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ScriptEditorPane extends TabPane {
    private Consumer<ScriptTab> saveScriptHandler;
    private Consumer<ScriptTab> runScriptHandler;
    private Map<String, ScriptTab> tabs = new HashMap<>();

    public ScriptEditorPane() {
        setOnKeyPressed(event -> {
            ScriptTab tab = (ScriptTab) getSelectionModel().getSelectedItem();
            if( tab == null ) {
                return;
            }
            if (event.getCode().equals(KeyCode.S) && event.isControlDown()) {
                saveScriptHandler.accept(tab);
            }
            if (event.getCode().equals(KeyCode.F5)) {
                runScriptHandler.accept(tab);
            }
        });
    }

    public ScriptTab getScript(String name) {
        return tabs.get(name);
    }

    public void addScript(String name, ScriptTab scriptTab) {
        tabs.put(name, scriptTab);
        getTabs().add(scriptTab);
    }


    public void remove(String scriptName) {
        ScriptTab remove = tabs.remove(scriptName);
        if (remove != null) {
            getTabs().remove(remove);
        }
    }

    public void setSaveScriptHandler(Consumer<ScriptTab> saveScriptHandler) {
        this.saveScriptHandler = saveScriptHandler;
    }

    public void setRunScriptHandler(Consumer<ScriptTab> runScriptHandler) {
        this.runScriptHandler = runScriptHandler;
    }
}
