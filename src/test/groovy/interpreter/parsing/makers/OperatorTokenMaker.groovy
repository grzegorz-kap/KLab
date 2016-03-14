package com.klab.interpreter.parsing.makers

import com.klab.interpreter.lexer.model.Token
import com.klab.interpreter.parsing.model.tokens.operators.OperatorAssociativity
import com.klab.interpreter.parsing.model.tokens.operators.OperatorPriority
import com.klab.interpreter.parsing.model.tokens.operators.OperatorToken
import com.natpryce.makeiteasy.Instantiator
import com.natpryce.makeiteasy.Property
import com.natpryce.makeiteasy.PropertyLookup

import static com.klab.interpreter.lexer.makers.TokenMaker.*
import static com.klab.interpreter.lexer.model.TokenClass.OPERATOR
import static com.klab.interpreter.parsing.model.tokens.operators.OperatorAssociativity.LEFT_TO_RIGHT
import static com.klab.interpreter.parsing.model.tokens.operators.OperatorPriority.LEVEL_30
import static com.natpryce.makeiteasy.MakeItEasy.*
import static com.natpryce.makeiteasy.Property.newProperty

class OperatorTokenMaker {

    static final Property<OperatorToken, Integer> argumentsNumber = newProperty()
    static final Property<OperatorToken, OperatorAssociativity> associativy = newProperty()
    static final Property<OperatorToken, OperatorPriority> priority = newProperty()
    static final Property<OperatorToken, Token> token = newProperty()

    static final saveOperatorToken = new Instantiator<OperatorToken>() {
        @Override
        OperatorToken instantiate(PropertyLookup<OperatorToken> lookup) {
            OperatorToken operatorToken = new OperatorToken()
            operatorToken.argumentsNumber = lookup.valueOf(argumentsNumber, 2)
            operatorToken.operatorAssociativity = lookup.valueOf(associativy, LEFT_TO_RIGHT)
            operatorToken.operatorPriority = lookup.valueOf(priority, LEVEL_30)
            def lexToken = make a(saveToken, with(lexame, "+"), with(tokenClass, OPERATOR))
            operatorToken.token == lookup.valueOf(token, lexToken)
            return operatorToken
        }
    }


}
