package interpreter.service.functions.external;


import interpreter.commons.IdentifierMapper;
import interpreter.core.CodeGenerator;
import interpreter.execution.model.Code;
import interpreter.lexer.model.TokenList;
import interpreter.lexer.service.Tokenizer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ExternalFunctionParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalFunctionParser.class);
    private static final String WORD = "[A-Za-z]+[A-Za-z\\d_]*";
    private static final String ARGS = String.format("([ \\t]*(%s[ ,\\t]*)*)", WORD);
    private Pattern signaturePattern;
    private Pattern endPattern = Pattern.compile("((end)|(endfunction))([\\n \\t])*$", Pattern.MULTILINE);
    private IdentifierMapper identifierMapper;
    private CodeGenerator codeGenerator;
    private Tokenizer tokenizer;

    public ExternalFunctionParser() {
        StringBuilder builder = new StringBuilder()
                .append("(^[\\n \\t]*")
                .append("function[\\t ]+)")
                .append("((\\[").append(ARGS).append("\\])")
                .append('|')
                .append("(").append(WORD).append("))")
                .append("[ \\t]*=[ \\t]*")
                .append('(').append(WORD).append(')')
                .append("\\(").append(ARGS).append("\\)")
                .append("[ \\t]*\\n");
        this.signaturePattern = Pattern.compile(builder.toString(), Pattern.MULTILINE);
    }

    public ExternalFunction parse(String input) {
        Matcher signatureMatcher = signaturePattern.matcher(input);
        Matcher endMatcher = endPattern.matcher(input);
        if (signatureMatcher.find() && endMatcher.find()) {
            ExternalFunction fun = new ExternalFunction();
            fun.setName(signatureMatcher.group(7));
            processOutput(fun, signatureMatcher.group(2));
            processInput(fun, signatureMatcher.group(8));
            processBody(fun, input, signatureMatcher.group(), endMatcher.group());
            return fun;
        } else {
            LOGGER.error("Error parsing: {}", input);
        }
        return null;
    }

    private synchronized void processBody(ExternalFunction fun, String input, String signature, String end) {
        identifierMapper.putNewScope();
        fun.getArguments().forEach(argument -> identifierMapper.registerMainIdentifier(argument.getName()));
        fun.getReturns().forEach(externalReturn -> identifierMapper.registerMainIdentifier(externalReturn.getName()));
        TokenList tokenList = tokenizer.readTokens(StringUtils.removeEnd(input, end));
        int lines = StringUtils.countMatches(signature, '\n');
        tokenList.removeIf(token -> token.getLine() <= lines);
        Code code = codeGenerator.translate(tokenList);
        fun.setCode(code);
        fun.setMemoryLength(identifierMapper.mainMappingsSize());
        identifierMapper.restorePreviousScope();
    }

    private void processInput(ExternalFunction fun, String input) {
        int index = 0;
        for (String argument : parseArguments(input)) {
            fun.addArgument(new ExternalArgument(index++, argument));
        }
    }

    private void processOutput(ExternalFunction fun, String output) {
        output = StringUtils.removeStart(output, "[");
        output = StringUtils.removeEnd(output, "]");
        int index = 0;
        for (String argument : parseArguments(output)) {
            fun.addReturn(new ExternalReturn(argument, index++));
        }
    }

    private String[] parseArguments(String output) {
        output = output.trim();
        output = output.replaceAll("[ \\t]*,[ \\t]*", ",");
        output = output.replaceAll("[ \\t]+", ",");
        return StringUtils.split(output, ',');
    }

    @Autowired
    public void setIdentifierMapper(IdentifierMapper identifierMapper) {
        this.identifierMapper = identifierMapper;
    }

    @Autowired
    public void setCodeGenerator(CodeGenerator codeGenerator) {
        this.codeGenerator = codeGenerator;
    }

    @Autowired
    public void setTokenizer(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }
}
