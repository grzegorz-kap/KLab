package gui.model.script;

import gui.model.script.CustomCodeArea;
import javafx.scene.control.Tab;

public class ScriptTab extends Tab {
    private CustomCodeArea codeArea;

    public ScriptTab(String script) {
        super(script);
    }

    public CustomCodeArea getCodeArea() {
        return codeArea;
    }

    public void setCodeArea(CustomCodeArea codeArea) {
        this.codeArea = codeArea;
        setContent(codeArea);
    }
}
