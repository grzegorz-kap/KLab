package interpreter.lexer.utils;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class TokenMatcher {
    private Pattern operatorRegex;
    private Pattern symbolsRegex;
    private List<String> operatorsPatterns = Arrays.asList(
    		"^\\|\\|",
    		"^\\|",
    		"^&&",
    		"^&",
            "^\\+",
            "^\\.\\*",
            "^\\*",
            "^-",
            "^\\./",
            "^/",
            "^==",
            "^~=",
            "^~",
            "^=",
            "^>=",
            "^<=",
            "^<",
            "^>",
            "^:",
            "^\\.\\^",
            "^\\^",
            "^'"
    );
    private List<String> symbolsPatterns = Arrays.asList(
            "^[ \\t]*,[ \\t]*",
            "^;",
            "^\\[",
            "^\\]",
            "^[ \\t]*\\([ \\t]*",
            "^[ \\t]*\\)[ \\t]*"
    );

    public TokenMatcher() {
        operatorRegex = Pattern.compile(mergeIntoPattern(operatorsPatterns));
        symbolsRegex = Pattern.compile(mergeIntoPattern(symbolsPatterns));
    }

    public Pattern getOperatorRegex() {
        return operatorRegex;
    }

    public Pattern getSymbolsRegex() {
        return symbolsRegex;
    }

    private String mergeIntoPattern(List<String> list) {
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
