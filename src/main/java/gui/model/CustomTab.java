package gui.model;

import javafx.scene.control.Tab;
import org.fxmisc.richtext.CodeArea;

public class CustomTab extends Tab {
    private CodeArea codeArea;

    public CustomTab(String script) {
        super(script);
    }

    public CodeArea getCodeArea() {
        return codeArea;
    }

    public void setCodeArea(CodeArea codeArea) {
        this.codeArea = codeArea;
        setContent(codeArea);
    }
}
