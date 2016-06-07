package com.klab.gui.model;

import com.klab.gui.service.CustomLineNumberFactory;
import com.klab.interpreter.debug.BreakpointService;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.stage.Popup;
import org.apache.commons.lang3.StringUtils;
import org.fxmisc.richtext.MouseOverTextEvent;
import org.fxmisc.richtext.StyleClassedTextArea;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;

import static com.klab.common.FunctionUtils.emptyConsumer;

public class ScriptContext {
    private Tab tab;
    private String scriptId;
    private StyleClassedTextArea codeArea;
    private Consumer<ScriptContext> onRunHandler = emptyConsumer();
    private Consumer<ScriptContext> onCloseHandler = emptyConsumer();
    private Consumer<ScriptContext> onDeleteHandler = emptyConsumer();
    private Consumer<ScriptContext> onRenameHandler = emptyConsumer();
    private Function<String, String> tooltipProducer = (val) -> null;

    public ScriptContext(String title, String content) {
        this.tab = new Tab(title);
        this.scriptId = title;
        this.codeArea = new StyleClassedTextArea();
        this.codeArea.setStyle("-fx-font-size: 14px");
        this.codeArea.appendText(content);
        this.tab.setContent(codeArea);
        createTooltip();
        buildContextMenu();
    }

    private void createTooltip() {
        Popup popup = new Popup();
        Label message = new Label("dupa");
        message.setStyle("-fx-background-color: black;  -fx-text-fill: white; -fx-padding: 5;");
        popup.getContent().addAll(message);

        this.codeArea.setMouseOverTextDelay(Duration.ofMillis(700));
        this.codeArea.addEventHandler(MouseOverTextEvent.MOUSE_OVER_TEXT_BEGIN, e -> {
            int start = e.getCharacterIndex();
            int end = e.getCharacterIndex();
            String text = codeArea.getText();
            Pattern pattern = Pattern.compile("[0-9a-zA-Z_]");
            while (start >= 0 && pattern.matcher(text.substring(start, start + 1)).matches()) {
                start--;
            }
            start++;
            while (end < text.length() && pattern.matcher(text.substring(end, end + 1)).matches()) {
                end++;
            }
            if (start < end && start >= 0 && end <= text.length()) {
                String var = tooltipProducer.apply(text.substring(start, end));
                if (StringUtils.isNoneBlank(var)) {
                    message.setText(var);
                    popup.show(codeArea, e.getScreenPosition().getX(), e.getScreenPosition().getY() + 15);
                }
            }
        });
        this.codeArea.addEventHandler(MouseOverTextEvent.MOUSE_OVER_TEXT_END, event -> popup.hide());
    }

    public void createLineNumbersFactory(BreakpointService breakpointService) {
        codeArea.setParagraphGraphicFactory(new CustomLineNumberFactory<>(codeArea, breakpointService, scriptId));
    }

    private void buildContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem run = new MenuItem("Run");
        run.setOnAction(e -> onRunHandler.accept(this));
        contextMenu.getItems().add(run);

        MenuItem rename = new MenuItem("Rename");
        rename.setOnAction(e -> onRenameHandler.accept(this));
        contextMenu.getItems().add(rename);

        MenuItem delete = new MenuItem("Delete");
        delete.setOnAction(e -> onDeleteHandler.accept(this));
        contextMenu.getItems().add(delete);

        MenuItem close = new MenuItem("Close");
        close.setOnAction(e -> onCloseHandler.accept(this));
        contextMenu.getItems().add(close);

        tab.setContextMenu(contextMenu);
    }

    public Tab getTab() {
        return tab;
    }

    public StyleClassedTextArea getCodeArea() {
        return codeArea;
    }

    public String getText() {
        return codeArea.getText();
    }

    public String getScriptId() {
        return scriptId;
    }

    public void setOnRunHandler(Consumer<ScriptContext> onRunHandler) {
        this.onRunHandler = onRunHandler;
    }

    public void setOnCloseHandler(Consumer<ScriptContext> onCloseHandler) {
        this.onCloseHandler = onCloseHandler;
    }

    public void setOnDeleteHandler(Consumer<ScriptContext> onDeleteHandler) {
        this.onDeleteHandler = onDeleteHandler;
    }

    public void setOnRenameHandler(Consumer<ScriptContext> onRenameHandler) {
        this.onRenameHandler = onRenameHandler;
    }

    public void setTooltipProducer(Function<String, String> tooltipProducer) {
        this.tooltipProducer = tooltipProducer;
    }
}
