package interpreter.lexer.utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class TokenMatcher {

    public static final List<String> OPERATORS = Arrays.asList(
            "^\\+", "^\\*", "^-", "^/"
    );

    public static final Pattern OPERATOR_REGEX = Pattern.compile(mergeIntoPattern(OPERATORS));

    private static String mergeIntoPattern(List<String> list) {
        StringBuilder buffer = new StringBuilder("");
        list.forEach(op -> {
            buffer.append("(");
            buffer.append(op);
            buffer.append(")|");
        });
        if(buffer.length()>0) {
            buffer.deleteCharAt(buffer.length()-1);
        }
        return buffer.toString();
    }
}
