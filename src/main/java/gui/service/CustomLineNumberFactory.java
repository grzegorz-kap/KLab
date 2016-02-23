package gui.service;

import gui.model.script.LineNumberLabel;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import org.fxmisc.richtext.StyledTextArea;
import org.reactfx.collection.LiveList;
import org.reactfx.value.Val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.IntFunction;

public class CustomLineNumberFactory implements IntFunction<LineNumberLabel> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomLineNumberFactory.class);
    private static final Insets DEFAULT_INSETS = new Insets(0.0, 5.0, 0.0, 5.0);
    private static final Paint DEFAULT_TEXT_FILL = Color.web("#666");
    private static final Font DEFAULT_FONT = Font.font("monospace", FontPosture.ITALIC, 13);
    private static final Background DEFAULT_BACKGROUND = new Background(new BackgroundFill(Color.web("#ddd"), null, null));

    public static IntFunction<LineNumberLabel> get(StyledTextArea<?> area) {
        return get(area, digits -> "%0" + digits + "d");
    }

    public static IntFunction<LineNumberLabel> get(StyledTextArea<?> area, IntFunction<String> format) {
        return new CustomLineNumberFactory(area, format);
    }

    private final Val<Integer> nParagraphs;
    private final IntFunction<String> format;

    private CustomLineNumberFactory(StyledTextArea<?> area, IntFunction<String> format) {
        nParagraphs = LiveList.sizeOf(area.getParagraphs());
        this.format = format;
    }


    @Override
    public LineNumberLabel apply(int value) {
        Val<String> formatted = nParagraphs.map(n -> format(value + 1, n));

        LineNumberLabel lineNo = new LineNumberLabel();
        lineNo.setFont(DEFAULT_FONT);
        lineNo.setBackground(DEFAULT_BACKGROUND);
        lineNo.setTextFill(DEFAULT_TEXT_FILL);
        lineNo.setPadding(DEFAULT_INSETS);
        lineNo.getStyleClass().add("lineno");

        // bind label's text to a Val that stops observing area's paragraphs
        // when lineNo is removed from scene
        lineNo.textProperty().bind(formatted.conditionOnShowing(lineNo));

        lineNo.setOnMouseClicked(event -> {
            LOGGER.info("Clicked {}", lineNo);
        });

        return lineNo;
    }

    private String format(int x, int max) {
        int digits = (int) Math.floor(Math.log10(max)) + 1;
        return String.format(format.apply(digits), x);
    }
}
