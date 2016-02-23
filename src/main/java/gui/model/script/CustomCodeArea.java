package gui.model.script;

import gui.service.CustomLineNumberFactory;
import org.fxmisc.richtext.CodeArea;

public class CustomCodeArea extends CodeArea {
    private ScriptTab parentTab;

    CustomCodeArea(String content, ScriptTab scriptTab) {
        super(content);
        setParagraphGraphicFactory(CustomLineNumberFactory.get(this));
        parentTab = scriptTab;
    }
}
