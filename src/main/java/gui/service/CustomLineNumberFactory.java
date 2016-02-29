package gui.service;

import gui.model.script.CustomCodeArea;
import gui.model.script.LineNumberLabel;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import org.reactfx.collection.LiveList;
import org.reactfx.value.Val;

import java.util.function.IntFunction;

public class CustomLineNumberFactory implements IntFunction<LineNumberLabel> {
    private static final Insets DEFAULT_INSETS = new Insets(0.0, 5.0, 0.0, 5.0);
    private static final Paint DEFAULT_TEXT_FILL = Color.web("#666");
    private static final Font DEFAULT_FONT = Font.font("monospace", FontPosture.ITALIC, 13);
    private static final Background DEFAULT_BACKGROUND = new Background(new BackgroundFill(Color.web("#ddd"), null, null));
    private static final Background BREAK_POINT_BACKGROUND = new Background(new BackgroundFill(Color.web("red"), null, null));

    public static IntFunction<LineNumberLabel> get(CustomCodeArea area) {
        return new CustomLineNumberFactory(area, digits -> "%0" + digits + "d");
    }

    private final Val<Integer> nParagraphs;
    private final IntFunction<String> format;
    private final CustomCodeArea customCodeArea;

    private CustomLineNumberFactory(CustomCodeArea area, IntFunction<String> format) {
        nParagraphs = LiveList.sizeOf(area.getParagraphs());
        customCodeArea = area;
        this.format = format;
    }


    @Override
    public LineNumberLabel apply(int value) {
        Val<String> formatted = nParagraphs.map(n -> format(value + 1, n));

        boolean breakPointExists = customCodeArea.isBreakPointExists(value);

        LineNumberLabel lineNo = new LineNumberLabel();
        lineNo.setCustomCodeArea(customCodeArea);
        lineNo.setFont(DEFAULT_FONT);
        lineNo.setBackground(breakPointExists ? BREAK_POINT_BACKGROUND : DEFAULT_BACKGROUND);
        lineNo.setTextFill(DEFAULT_TEXT_FILL);
        lineNo.setPadding(DEFAULT_INSETS);
        lineNo.getStyleClass().add("lineno");

        // bind label's text to a Val that stops observing area's paragraphs
        // when lineNo is removed from scene
        lineNo.textProperty().bind(formatted.conditionOnShowing(lineNo));

        lineNo.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                if (customCodeArea.isBreakPointExists(value)) {
                    lineNo.setBackground(DEFAULT_BACKGROUND);
                    customCodeArea.removeBreakPoint(value);
                } else {
                    lineNo.setBackground(BREAK_POINT_BACKGROUND);
                    customCodeArea.addBreakPoint(value);
                }
            }
        });
        return lineNo;
    }

    private String format(int x, int max) {
        int digits = (int) Math.floor(Math.log10(max)) + 1;
        return String.format(format.apply(digits), x);
    }
}
