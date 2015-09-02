package interpreter.lexer.makers

import com.natpryce.makeiteasy.Instantiator
import com.natpryce.makeiteasy.Property
import com.natpryce.makeiteasy.PropertyLookup
import interpreter.lexer.model.Token
import interpreter.lexer.model.TokenClass

import static com.natpryce.makeiteasy.Property.newProperty
import static interpreter.lexer.model.TokenClass.*

class TokenMaker {

    static final Property<Token, String> lexame = newProperty()
    static final Property<Token, Long> column = newProperty()
    static final Property<Token, Long> line = newProperty()
    static final Property<Token, TokenClass> tokenClass = newProperty()

    static final saveToken = new Instantiator<Token>() {

        @Override
        Token instantiate(PropertyLookup<Token> lookup) {
            Token token = new Token();
            token.lexeme = lookup.valueOf(lexame, "1234")
            token.column = lookup.valueOf(column, Long.valueOf(1))
            token.line = lookup.valueOf(line, Long.valueOf(1))
            token.setTokenClass(lookup.valueOf(tokenClass, NUMBER))
            return token;
        }
    }
}
