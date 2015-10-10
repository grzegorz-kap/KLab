package interpreter.lexer.utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class TokenMatcher {

    private static final List<String> OPERATORS_PATTERNS = Arrays.asList(
            "^\\+",
            "^\\*",
            "^-",
            "^/",
            "^="
    );

    private static final List<String> SYMBOLS_PATTERNS = Arrays.asList(
            "^,",
            "^:",
            "^;",
            "^\\[",
            "^\\]",
            "^\\(",
            "^\\)"
    );

    private static Pattern OPERATOR_REGEX = Pattern.compile(mergeIntoPattern(OPERATORS_PATTERNS));

    private static Pattern SYMBOLS_REGEX = Pattern.compile(mergeIntoPattern(SYMBOLS_PATTERNS));

    public static Pattern getOperatorRegex() {
        return OPERATOR_REGEX;
    }

    public static Pattern getSymbolsRegex() {
        return SYMBOLS_REGEX;
    }

    private static String mergeIntoPattern(List<String> list) {
        StringBuilder builder = new StringBuilder("");
        list.forEach(op -> {
            builder.append("(");
            builder.append(op);
            builder.append(")|");
        });
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }
}
