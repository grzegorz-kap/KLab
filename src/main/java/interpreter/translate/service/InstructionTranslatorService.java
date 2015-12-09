package interpreter.translate.service;

import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.model.expression.ExpressionValue;
import interpreter.parsing.model.tokens.operators.OperatorCode;
import interpreter.parsing.model.tokens.operators.OperatorToken;
import interpreter.translate.exception.UnsupportedParseToken;
import interpreter.translate.handlers.TranslateHandler;
import interpreter.translate.model.Instruction;
import interpreter.translate.model.InstructionCode;
import interpreter.translate.model.JumperInstruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class InstructionTranslatorService extends AbstractInstructionTranslator {
    public static final String UNEXPECTED_TOKEN_MESSAGE = "Unexpected token";

    @Autowired
    public InstructionTranslatorService(Set<TranslateHandler> translateHandlers) {
        super(translateHandlers);
    }

    @Override
    protected void translate() {
        Expression<ParseToken> parseTokenExpression = translateContext.getExpression();
        process(parseTokenExpression);
        addAns();
        addPrint();
    }

    private void addAns() {
        if (getAnsProperty()) {
            translateContextManager.addInstruction(new Instruction(InstructionCode.ANS, 1));
        }

    }

    private void translateExpressionValue(Expression<ParseToken> expression) {
        TranslateHandler translateHandler = getTranslateHandler(expression.getValue().getParseClass());
        checkIfSupported(expression, translateHandler);
        translateHandler.handle(expression);
    }

    private void translateExpressionNode(Expression<ParseToken> expression) {
        if(checkIfOperator(OperatorCode.AND, expression.getValue())) {
        	process(expression.getChildren().get(0));
        	translateContextManager.addInstruction(new Instruction(InstructionCode.LOGICAL, 0));
        	JumperInstruction jmpf = new JumperInstruction(InstructionCode.JMPFNP, 0);
        	translateContextManager.addInstruction(jmpf);
        	process(expression.getChildren().get(1));
        	translateExpressionValue(expression);
        	jmpf.setJumpIndex(code.size() + translateContext.getMacroInstruction().size());
        } else if(checkIfOperator(OperatorCode.OR, expression.getValue())) {
        	process(expression.getChildren().get(0));
        	translateContextManager.addInstruction(new Instruction(InstructionCode.LOGICAL, 0));
        	JumperInstruction jmpt = new JumperInstruction(InstructionCode.JMPTNP, 0);
        	translateContextManager.addInstruction(jmpt);
        	process(expression.getChildren().get(1));
        	translateExpressionValue(expression);
        	jmpt.setJumpIndex(code.size() + translateContext.getMacroInstruction().size());
        } else {
        	expression.getChildren().forEach(this::process);
            translateExpressionValue(expression);
        }
    }
    
    private boolean checkIfOperator(OperatorCode operatorCode, ParseToken parseToken) {
    	if(parseToken instanceof OperatorToken) {
    		OperatorToken operatorToken = (OperatorToken) parseToken;
    		return operatorCode.equals(operatorToken.getOperatorCode());
    	} else {
    		return false;
    	}
    }

    private void process(Expression<ParseToken> parseTokenExpression) {
        if (parseTokenExpression instanceof ExpressionValue) {
            translateExpressionValue(parseTokenExpression);
        } else {
            translateExpressionNode(parseTokenExpression);
        }
    }

    private void addPrint() {
        if (getPrintProperty()) {
            translateContextManager.addInstruction(new Instruction(InstructionCode.PRINT, 0));
        }
    }

    private boolean getAnsProperty() {
        return Optional
                .ofNullable(translateContext.getExpression().getProperty(Expression.ANS_PROPERTY_KEY, Boolean.class))
                .orElse(true);
    }

    private boolean getPrintProperty() {
        return Optional
                .ofNullable(translateContext.getExpression().getProperty(Expression.PRINT_PROPERTY_KEY, Boolean.class))
                .orElse(false);
    }

    private void checkIfSupported(Expression<ParseToken> expression, TranslateHandler translateHandler) {
        if (Objects.isNull(translateHandler)) {
            throw new UnsupportedParseToken(UNEXPECTED_TOKEN_MESSAGE, expression.getValue());
        }
    }
}
