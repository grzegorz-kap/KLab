package interpreter.core;

import interpreter.commons.IdentifierMapper;
import interpreter.commons.MemorySpace;
import interpreter.execution.model.Code;
import interpreter.lexer.model.TokenList;
import interpreter.lexer.service.Tokenizer;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.service.Parser;
import interpreter.translate.keyword.PostParseHandler;
import interpreter.translate.service.InstructionTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CodeGeneratorImpl implements CodeGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodeGeneratorImpl.class);

    private Parser parser;
    private Tokenizer tokenizer;
    private InstructionTranslator instructionTranslator;
    private List<PostParseHandler> postParseHandlers;
    private MemorySpace memorySpace;
    private IdentifierMapper identifierMapper;

    @Override
    public Code translate(String input) {
        Code code = initCode();
        parser.setTokenList(tokenizer.readTokens(input));
        process(code);
        LOGGER.debug("{}", code);
        return code;
    }

    @Override
    public Code translate(TokenList input) {
        Code code = initCode();
        parser.setTokenList(input);
        process(code);
        return code;
    }

    private Code initCode() {
        Code code = new Code();
        postParseHandlers.forEach(handler -> handler.setCode(code));
        instructionTranslator.setCode(code);
        return code;
    }

    private void process(Code code) {
        while (parser.hasNext()) {
            List<Expression<ParseToken>> expressionList = parser.process();
            PostParseHandler postParseHandler = findPostParseHandler(expressionList);
            if (Objects.isNull(postParseHandler)) {
                expressionList.forEach(expression -> code.add(instructionTranslator.translate(expression).getInstructions()));
            } else {
                code.add(postParseHandler.handle(expressionList, instructionTranslator).getInstructions());
            }
        }
        memorySpace.reserve(identifierMapper.mainMappingsSize());
    }

    public PostParseHandler findPostParseHandler(List<Expression<ParseToken>> expressionList) {
        return postParseHandlers.stream().filter(handler -> handler.canBeHandled(expressionList)).findFirst().orElse(null);
    }

    @Autowired
    public void setParser(Parser parser) {
        this.parser = parser;
    }

    @Autowired
    public void setTokenizer(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Autowired
    public void setInstructionTranslator(InstructionTranslator instructionTranslator) {
        this.instructionTranslator = instructionTranslator;
    }

    @Autowired
    public void setPostParseHandlers(List<PostParseHandler> postParseHandlers) {
        this.postParseHandlers = postParseHandlers;
    }

    @Autowired
    public void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }

    @Autowired
    public void setIdentifierMapper(IdentifierMapper identifierMapper) {
        this.identifierMapper = identifierMapper;
    }
}
