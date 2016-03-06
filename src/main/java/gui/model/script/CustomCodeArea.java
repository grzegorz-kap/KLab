package gui.model.script;

import com.google.common.collect.Sets;
import gui.model.Style;
import gui.service.CustomLineNumberFactory;
import org.fxmisc.richtext.InlineStyleTextArea;

import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public class CustomCodeArea extends InlineStyleTextArea<Style> {
    private ScriptTab parentTab;
    private Set<Integer> breakingPoints = Sets.newHashSet();
    private Consumer<Integer> breakPointAddedHandler;
    private Consumer<Integer> breakPointRemovedHandler;

    CustomCodeArea(String content, ScriptTab scriptTab) {
        super(String::new, Style::toCss);
        appendText(content);
        this.setParagraphGraphicFactory(CustomLineNumberFactory.get(this));
        parentTab = scriptTab;
    }

    public boolean isBreakPointExists(Integer lineNumber) {
        return breakingPoints.contains(lineNumber);
    }

    public boolean addBreakPoint(Integer lineNumber) {
        boolean added = breakingPoints.add(lineNumber);
        if (added) {
            Objects.requireNonNull(breakPointAddedHandler).accept(lineNumber);
        }
        return added;
    }

    public boolean removeBreakPoint(Integer lineNumber) {
        boolean removed = breakingPoints.remove(lineNumber);
        if (removed) {
            Objects.requireNonNull(breakPointRemovedHandler).accept(lineNumber);
        }
        return removed;
    }

    public void setBreakPointAddedHandler(Consumer<Integer> breakPointAddedHandler) {
        this.breakPointAddedHandler = breakPointAddedHandler;
    }

    public void setBreakPointRemovedHandler(Consumer<Integer> breakPointRemovedHandler) {
        this.breakPointRemovedHandler = breakPointRemovedHandler;
    }

    public void setBreakingPoints(Set<Integer> breakingPoints) {
        this.breakingPoints = breakingPoints;
    }
}
