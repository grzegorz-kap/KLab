package com.klab.interpreter.core.code;

import com.klab.interpreter.commons.memory.IdentifierMapper;
import com.klab.interpreter.commons.memory.MemorySpace;
import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.lexer.model.TokenList;
import com.klab.interpreter.lexer.service.Tokenizer;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.parsing.service.Parser;
import com.klab.interpreter.translate.keyword.PostParseHandler;
import com.klab.interpreter.translate.service.InstructionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CodeGeneratorImpl implements CodeGenerator {
    private Supplier<Code> defaultCodeSupplier = Code::new;
    private MacroInstructionTranslatedCallback defaultMacroInstructionTranslatedCallback = null;
    private Parser parser;
    private Tokenizer tokenizer;
    private InstructionTranslator instructionTranslator;
    private List<PostParseHandler> postParseHandlers;
    private MemorySpace memorySpace;
    private IdentifierMapper identifierMapper;
    private Code codeCache;

    @Override
    public Code translate(String input) {
        return translate(input, defaultCodeSupplier, defaultMacroInstructionTranslatedCallback);
    }

    @Override
    public Code translate(String input, Supplier<Code> codeSupplier) {
        return translate(input, codeSupplier, defaultMacroInstructionTranslatedCallback);
    }

    @Override
    public Code translate(TokenList input, Supplier<Code> codeSupplier) {
        Code code = initCode(codeSupplier);
        parser.setTokenList(input);
        process(code, defaultMacroInstructionTranslatedCallback);
        return code;
    }

    @Override
    public Code translate(TokenList input) {
        return translate(input, defaultCodeSupplier);
    }

    @Override
    public Code translate(String input, Supplier<Code> codeSupplier, MacroInstructionTranslatedCallback macroInstructionTranslatedCallback) {
        Code code = initCode(codeSupplier);
        code.setSourceCode(input);
        parser.setTokenList(tokenizer.readTokens(input));
        process(code, macroInstructionTranslatedCallback);
        code.setSourceCode(input);
        return code;
    }

    @Override
    public boolean isInstructionCompletelyTranslated() {
        for (PostParseHandler handler : postParseHandlers) {
            if (!handler.isInstructionCompletelyTranslated()) {
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

    private void process(Code code, MacroInstructionTranslatedCallback callback) {
        while (parser.hasNext()) {
            List<Expression<ParseToken>> expressionList = parser.process();
            PostParseHandler postParseHandler = findPostParseHandler(expressionList);
            if (isNull(postParseHandler)) {
                for (Expression<ParseToken> node : expressionList) {
                    code.add(instructionTranslator.translate(node));
                }
            } else {
                code.add(postParseHandler.handle(expressionList, instructionTranslator));
            }
            memorySpace.reserve(identifierMapper.mainMappingsSize());
            if (nonNull(callback)) {
                callback.invoke();
            }
        }
    }

    private PostParseHandler findPostParseHandler(List<Expression<ParseToken>> expressionList) {
        for (PostParseHandler handler : postParseHandlers) {
            if (handler.canBeHandled(expressionList)) {
                return handler;
            }
        }
        return null;
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
