package gui.model.script;

import org.fxmisc.richtext.CodeArea;

public class CustomCodeArea extends CodeArea {
    private ScriptTab parentTab;

    public CustomCodeArea(String content) {
        super(content);
    }

    public ScriptTab getParentTab() {
        return parentTab;
    }

    public void setParentTab(ScriptTab parentTab) {
        this.parentTab = parentTab;
    }
}
