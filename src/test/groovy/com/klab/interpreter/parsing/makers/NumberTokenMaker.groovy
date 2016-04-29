package com.klab.interpreter.parsing.makers

import com.klab.interpreter.lexer.model.Token
import com.klab.interpreter.parsing.model.tokens.NumberToken
import com.klab.interpreter.types.NumericType
import com.natpryce.makeiteasy.Instantiator
import com.natpryce.makeiteasy.Property
import com.natpryce.makeiteasy.PropertyLookup

import static com.klab.interpreter.lexer.makers.TokenMaker.saveToken
import static com.natpryce.makeiteasy.MakeItEasy.a
import static com.natpryce.makeiteasy.MakeItEasy.make
import static com.natpryce.makeiteasy.Property.newProperty

class NumberTokenMaker {

    static final Property<NumberToken, NumericType> numberType = newProperty()
    static final Property<NumberToken, Token> token = newProperty()

    static final saveNumberToken = new Instantiator<NumberToken>() {

        @Override
        NumberToken instantiate(PropertyLookup<NumberToken> lookup) {
            NumberToken numberToken = new NumberToken();
            numberToken.numericType = lookup.valueOf(numberType, NumericType.DOUBLE)
            numberToken.token = lookup.valueOf(token, make(a(saveToken)))
            return numberToken;
        }
    }
}
