package gui.model.script;

import com.google.common.collect.Sets;
import gui.service.CustomLineNumberFactory;
import org.fxmisc.richtext.CodeArea;

import java.util.Set;

public class CustomCodeArea extends CodeArea {
    private ScriptTab parentTab;
    private Set<Integer> breakingPoints = Sets.newHashSet();

    CustomCodeArea(String content, ScriptTab scriptTab) {
        super(content);
        this.setParagraphGraphicFactory(CustomLineNumberFactory.get(this));
        parentTab = scriptTab;
    }

    public boolean isBreakPointExists(Integer lineNumber) {
        return breakingPoints.contains(lineNumber);
    }

    public boolean addBreakPoint(Integer lineNumber) {
        return breakingPoints.add(lineNumber);
    }

    public boolean removeBreakPoint(Integer lineNumber) {
        return breakingPoints.remove(lineNumber);
    }

    public ScriptTab getParentTab() {
        return parentTab;
    }
}
