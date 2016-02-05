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
import java.util.function.Supplier;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CodeGeneratorImpl implements CodeGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodeGeneratorImpl.class);
    private Supplier<Code> defaultCodeSupplier = Code::new;
    private Callback defaultCallback = null;
    private Parser parser;
    private Tokenizer tokenizer;
    private InstructionTranslator instructionTranslator;
    private List<PostParseHandler> postParseHandlers;
    private MemorySpace memorySpace;
    private IdentifierMapper identifierMapper;
    private Code codeCache;

    @Override
    public Code translate(String input) {
        return translate(input, defaultCodeSupplier, defaultCallback);
    }

    @Override
    public Code translate(TokenList input) {
        Code code = initCode(defaultCodeSupplier);
        parser.setTokenList(input);
        process(code, defaultCallback);
        return code;
    }

    @Override
    public Code translate(String input, Supplier<Code> codeSupplier, Callback callback) {
        Code code = initCode(codeSupplier);
        parser.setTokenList(tokenizer.readTokens(input));
        process(code, callback);
        return code;
    }

    @Override
    public boolean executionCanStart() {
        for (PostParseHandler handler : postParseHandlers) {
            if (!handler.executionCanStart()) {
                return false;
            }
        }
        return true;
    }

    private Code initCode(Supplier<Code> codeSupplier) {
        Code code = codeSupplier.get();
        if (code != codeCache) {
            postParseHandlers.forEach(handler -> handler.setCode(code));
            instructionTranslator.setCode(code);
            codeCache = code;
        }
        return code;
    }

    private void process(Code code, Callback callback) {
        while (parser.hasNext()) {
            List<Expression<ParseToken>> expressionList = parser.process();
            PostParseHandler postParseHandler = postParseHandlers.stream()
                    .filter(handler -> handler.canBeHandled(expressionList))
                    .findFirst()
                    .orElse(null);
            if (Objects.isNull(postParseHandler)) {
                expressionList.forEach(expression -> code.add(instructionTranslator.translate(expression).getInstructions()));
            } else {
                code.add(postParseHandler.handle(expressionList, instructionTranslator).getInstructions());
            }
            memorySpace.reserve(identifierMapper.mainMappingsSize());
            if (callback != null) {
                callback.invoke();
            }
        }
        LOGGER.info("Translated: {}", code);
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
