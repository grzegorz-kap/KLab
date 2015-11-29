package interpreter.parsing.factory.operator;

import static interpreter.parsing.model.tokens.operators.OperatorAssociativity.LEFT_TO_RIGHT;
import static interpreter.parsing.model.tokens.operators.OperatorAssociativity.RIGHT_TO_LEFT;
import static interpreter.parsing.model.tokens.operators.OperatorCode.ADD;
import static interpreter.parsing.model.tokens.operators.OperatorCode.ADIV;
import static interpreter.parsing.model.tokens.operators.OperatorCode.AMULT;
import static interpreter.parsing.model.tokens.operators.OperatorCode.ASSIGN;
import static interpreter.parsing.model.tokens.operators.OperatorCode.DIV;
import static interpreter.parsing.model.tokens.operators.OperatorCode.EQ;
import static interpreter.parsing.model.tokens.operators.OperatorCode.GE;
import static interpreter.parsing.model.tokens.operators.OperatorCode.GT;
import static interpreter.parsing.model.tokens.operators.OperatorCode.LE;
import static interpreter.parsing.model.tokens.operators.OperatorCode.LT;
import static interpreter.parsing.model.tokens.operators.OperatorCode.MULT;
import static interpreter.parsing.model.tokens.operators.OperatorCode.NEQ;
import static interpreter.parsing.model.tokens.operators.OperatorCode.POW;
import static interpreter.parsing.model.tokens.operators.OperatorCode.RANGE;
import static interpreter.parsing.model.tokens.operators.OperatorCode.RANGE3;
import static interpreter.parsing.model.tokens.operators.OperatorCode.SUB;
import static interpreter.parsing.model.tokens.operators.OperatorPriority.LEVEL_10;
import static interpreter.parsing.model.tokens.operators.OperatorPriority.LEVEL_20;
import static interpreter.parsing.model.tokens.operators.OperatorPriority.LEVEL_25;
import static interpreter.parsing.model.tokens.operators.OperatorPriority.LEVEL_30;
import static interpreter.parsing.model.tokens.operators.OperatorPriority.LEVEL_40;
import static interpreter.parsing.model.tokens.operators.OperatorPriority.LEVEL_50;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import interpreter.lexer.model.Token;
import interpreter.parsing.model.tokens.operators.OperatorToken;

@Service
public class OperatorFactory {
	private Map<String, Supplier<OperatorToken>> operatorsProducer = new HashMap<>();

	public OperatorFactory() {
		operatorsProducer.put("=", () -> new OperatorToken(2, RIGHT_TO_LEFT, LEVEL_10, ASSIGN));
		operatorsProducer.put("==", () -> new OperatorToken(2, LEFT_TO_RIGHT, LEVEL_20, EQ));
		operatorsProducer.put("~=", () -> new OperatorToken(2, LEFT_TO_RIGHT, LEVEL_20, NEQ));
		operatorsProducer.put(">", () -> new OperatorToken(2, LEFT_TO_RIGHT, LEVEL_20, GT));
		operatorsProducer.put(">=", () -> new OperatorToken(2, LEFT_TO_RIGHT, LEVEL_20, GE));
		operatorsProducer.put("<", () -> new OperatorToken(2, LEFT_TO_RIGHT, LEVEL_20, LT));
		operatorsProducer.put("<=", () -> new OperatorToken(2, LEFT_TO_RIGHT, LEVEL_20, LE));
		operatorsProducer.put(":", () -> new OperatorToken(2, LEFT_TO_RIGHT, LEVEL_25, RANGE));
		operatorsProducer.put("$::", () -> new OperatorToken(3, LEFT_TO_RIGHT, LEVEL_25, RANGE3));
		operatorsProducer.put("+", () -> new OperatorToken(2, LEFT_TO_RIGHT, LEVEL_30, ADD));
		operatorsProducer.put("-", () -> new OperatorToken(2, LEFT_TO_RIGHT, LEVEL_30, SUB));
		operatorsProducer.put("*", () -> new OperatorToken(2, LEFT_TO_RIGHT, LEVEL_40, MULT));
		operatorsProducer.put(".*", () -> new OperatorToken(2, LEFT_TO_RIGHT, LEVEL_40, AMULT));
		operatorsProducer.put("/", () -> new OperatorToken(2, LEFT_TO_RIGHT, LEVEL_40, DIV));
		operatorsProducer.put("./", () -> new OperatorToken(2, LEFT_TO_RIGHT, LEVEL_40, ADIV));
		operatorsProducer.put("^", () -> new OperatorToken(2, LEFT_TO_RIGHT, LEVEL_50, POW));
	}

	public OperatorToken getOperator(final Token token) {
		OperatorToken operatorToken = operatorsProducer.get(token.getLexeme()).get();
		operatorToken.setToken(token);
		return operatorToken;
	}

	public OperatorToken getOperator(final String token) {
		return operatorsProducer.get(token).get();
	}
}
