package com.klab.gui.model;

import com.klab.gui.service.CustomLineNumberFactory;
import com.klab.interpreter.debug.BreakpointService;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import org.fxmisc.richtext.InlineStyleTextArea;
import org.fxmisc.richtext.StyledTextArea;

import java.util.function.Consumer;

import static com.klab.common.FunctionUtils.emptyConsumer;

public class ScriptContext {
    private Tab tab;
    private String scriptId;
    private StyledTextArea<Style> codeArea;
    private Consumer<ScriptContext> onRunHandler = emptyConsumer();
    private Consumer<ScriptContext> onCloseHandler = emptyConsumer();

    public ScriptContext(String title, String content) {
        this.tab = new Tab(title);
        this.scriptId = title;
        this.codeArea = new InlineStyleTextArea<>(String::new, Style::toCss);
        this.codeArea.appendText(content);
        this.tab.setContent(codeArea);
        buildContextMenu();
    }

    public void createLineNumbersFactory(BreakpointService breakpointService) {
        codeArea.setParagraphGraphicFactory(new CustomLineNumberFactory<>(codeArea, breakpointService, scriptId));
    }

    private void buildContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem run = new MenuItem("Run");
        run.setOnAction(e -> onRunHandler.accept(this));
        contextMenu.getItems().add(run);

        MenuItem close = new MenuItem("Close");
        close.setOnAction(e -> onCloseHandler.accept(this));
        contextMenu.getItems().add(close);

        tab.setContextMenu(contextMenu);
    }

    public Tab getTab() {
        return tab;
    }

    public StyledTextArea<Style> getCodeArea() {
        return codeArea;
    }

    public String getText() {
        return codeArea.getText();
    }

    public void setOnRunHandler(Consumer<ScriptContext> onRunHandler) {
        this.onRunHandler = onRunHandler;
    }

    public void setOnCloseHandler(Consumer<ScriptContext> onCloseHandler) {
        this.onCloseHandler = onCloseHandler;
    }
}
