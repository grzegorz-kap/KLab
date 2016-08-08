package com.klab.interpreter.core.code;

import com.klab.interpreter.commons.memory.IdentifierMapper;
import com.klab.interpreter.commons.memory.MemorySpace;
import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.lexer.model.TokenList;
import com.klab.interpreter.lexer.service.TokenizerService;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.parsing.service.ParserService;
import com.klab.interpreter.translate.handlers.PostParseHandler;
import com.klab.interpreter.translate.model.MacroInstruction;
import com.klab.interpreter.translate.service.ExpressionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CodeGeneratorImpl implements CodeGenerator {
    private Supplier<Code> defaultCodeSupplier = Code::new;
    private InstructionTranslatedCallback defaultInstructionTranslatedCallback = null;
    private ParserService parserService;
    private TokenizerService tokenizerService;
    private ExpressionTranslator expressionTranslator;
    private List<PostParseHandler> preTranslateHandlers;
    private MemorySpace memorySpace;
    private IdentifierMapper identifierMapper;
    private Code codeCache;

    @Override
    public Code translate(String input) {
        return translate(input, defaultCodeSupplier, defaultInstructionTranslatedCallback);
    }

    @Override
    public Code translate(String input, Supplier<Code> codeSupplier) {
        return translate(input, codeSupplier, defaultInstructionTranslatedCallback);
    }

    @Override
    public Code translate(TokenList input, Supplier<Code> codeSupplier) {
        Code code = initCode(codeSupplier);
        parserService.setupTokenList(input);
        process(code, defaultInstructionTranslatedCallback);
        return code;
    }

    @Override
    public Code translate(TokenList input) {
        return translate(input, defaultCodeSupplier);
    }

    @Override
    public Code translate(String input, Supplier<Code> codeSupplier, InstructionTranslatedCallback instructionTranslatedCallback) {
        Code code = initCode(codeSupplier);
        code.setSourceText(input);
        parserService.setupTokenList(tokenizerService.readTokens(input));
        process(code, instructionTranslatedCallback);
        return code;
    }

    @Override
    public boolean isInstructionCompletelyTranslated() {
        for (PostParseHandler handler : preTranslateHandlers) {
            if (!handler.isInstructionCompletelyTranslated()) {
                return false;
            }
        }
        return true;
    }

    private Code initCode(Supplier<Code> codeSupplier) {
        Code code = codeSupplier.get();
        if (code != codeCache) {
            preTranslateHandlers.forEach(handler -> handler.setCode(code));
            expressionTranslator.setupCode(code);
            codeCache = code;
        }
        return code;
    }

    @Override
    public void reset() {
        preTranslateHandlers.forEach(PostParseHandler::reset);
    }

    private void process(Code code, InstructionTranslatedCallback callback) {
        while (parserService.hasNext()) {
            List<Expression<ParseToken>> expressionList = parserService.process();
            PostParseHandler handler = findPostParseHandler(expressionList);
            if (!isNull(handler)) {
                MacroInstruction instructions = handler.handle(expressionList, expressionTranslator);
                Optional.ofNullable(instructions).ifPresent(code::add);
            } else {
                for (Expression<ParseToken> node : expressionList) {
                    code.add(expressionTranslator.translate(node));
                }
            }
            if (nonNull(callback)) {
                memorySpace.reserve(identifierMapper.mainMappingsSize());
                callback.invoke();
            }
        }
        memorySpace.reserve(identifierMapper.mainMappingsSize());
    }

    private PostParseHandler findPostParseHandler(List<Expression<ParseToken>> expressionList) {
        for (PostParseHandler handler : preTranslateHandlers) {
            if (handler.canBeHandled(expressionList)) {
                return handler;
            }
        }
        return null;
    }

    @Autowired
    private void setParserService(ParserService parserService) {
        this.parserService = parserService;
    }

    @Autowired
    private void setTokenizerService(TokenizerService tokenizerService) {
        this.tokenizerService = tokenizerService;
    }

    @Autowired
    private void setExpressionTranslator(ExpressionTranslator expressionTranslator) {
        this.expressionTranslator = expressionTranslator;
    }

    @Autowired
    private void setPreTranslateHandlers(List<PostParseHandler> preTranslateHandlers) {
        this.preTranslateHandlers = preTranslateHandlers;
    }

    @Autowired
    private void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }

    @Autowired
    private void setIdentifierMapper(IdentifierMapper identifierMapper) {
        this.identifierMapper = identifierMapper;
    }
}
