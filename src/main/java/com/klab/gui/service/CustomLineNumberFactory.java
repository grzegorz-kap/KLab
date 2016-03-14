package com.klab.gui.service;

import com.klab.interpreter.debug.BreakpointService;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import org.fxmisc.richtext.StyledTextArea;
import org.reactfx.collection.LiveList;
import org.reactfx.value.Val;

import java.util.Objects;
import java.util.function.IntFunction;

public class CustomLineNumberFactory<S> implements IntFunction<Label> {
    private static final Insets DEFAULT_INSETS = new Insets(0.0, 5.0, 0.0, 5.0);
    private static final Paint DEFAULT_TEXT_FILL = Color.web("#666");
    private static final Font DEFAULT_FONT = Font.font("monospace", FontPosture.ITALIC, 13);
    private static final Background DEFAULT_BACKGROUND = new Background(new BackgroundFill(Color.web("#ddd"), null, null));
    private static final Background BREAK_POINT_BACKGROUND = new Background(new BackgroundFill(Color.web("red"), null, null));

    private final Val<Integer> nParagraphs;
    private final IntFunction<String> format = digits -> "%0" + digits + "d";
    private final BreakpointService breakpointService;
    private final String scriptId;

    public CustomLineNumberFactory(StyledTextArea<S> area, BreakpointService breakpointService, String scriptId) {
        this.nParagraphs = LiveList.sizeOf(area.getParagraphs());
        this.breakpointService = Objects.requireNonNull(breakpointService);
        this.scriptId = scriptId;
    }

    @Override
    public Label apply(int value) {
        Val<String> formatted = nParagraphs.map(n -> format(value + 1, n));
        boolean breakPointExists = breakpointService.isBreakPointExists(value + 1, scriptId);
        Label lineNo = new Label();
        lineNo.setFont(DEFAULT_FONT);
        lineNo.setBackground(breakPointExists ? BREAK_POINT_BACKGROUND : DEFAULT_BACKGROUND);
        lineNo.setTextFill(DEFAULT_TEXT_FILL);
        lineNo.setPadding(DEFAULT_INSETS);
        lineNo.getStyleClass().add("lineno");

        // bind label's text to a Val that stops observing area's paragraphs
        // when lineNo is removed from scene
        lineNo.textProperty().bind(formatted.conditionOnShowing(lineNo));

        lineNo.setOnMouseClicked(event -> {
            if (breakpointService.isBreakPointExists(value + 1, scriptId)) {
                lineNo.setBackground(DEFAULT_BACKGROUND);
                breakpointService.remove(scriptId, value + 1);
            } else {
                lineNo.setBackground(BREAK_POINT_BACKGROUND);
                breakpointService.add(scriptId, value + 1);
            }
        });
        return lineNo;
    }

    private String format(int x, int max) {
        int digits = (int) Math.floor(Math.log10(max)) + 1;
        return String.format(format.apply(digits), x);
    }
}
