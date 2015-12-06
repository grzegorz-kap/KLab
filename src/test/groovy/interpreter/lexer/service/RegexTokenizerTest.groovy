package interpreter.lexer.service

import interpreter.lexer.model.TokenClass
import interpreter.lexer.utils.*
import spock.lang.Specification

class RegexTokenizerTest extends Specification {

    Tokenizer tokenizer

    def setup() {
        RegexTokenizer regexTokenizer = new RegexTokenizer()
        regexTokenizer.setKeywordMatcher(new KeywordMatcher())
        regexTokenizer.setSymbolsMapper(new SymbolsMapper())
        regexTokenizer.settCM(new TokenizerContextManager())
        regexTokenizer.setTokenRegexReader(new TokenRegexReader())
        regexTokenizer.setTokenMatcher(new TokenMatcher())
        regexTokenizer.setTokenStartMatcher(new TokenStartMatcher())
        tokenizer = regexTokenizer;
    }

    def "Testing reading numbers"(def input) {
        when:
        def tokens = tokenizer.readTokens(input)
        then:
        tokens.size() == 1
        tokens.get(0).lexeme == input
        tokens.get(0).tokenClass == TokenClass.NUMBER
        where:
        input         | _
        "32323"       | _
        "3.32"        | _
        ".32323"      | _
        "3232."       | _
        "3223e121"    | _
        "3232e-323"   | _
        ".3232e+3232" | _
        "32323.e+323" | _
        "3232i"       | _
        "323.i"       | _
        ".3232i"      | _
        "323e1i"      | _
        "32.e1"       | _
    }

    def "Testing reading incorrect numbers"(def input) {
        when:
        def tokens = tokenizer.readTokens(input);
        then:
        tokens.size() == 0 || tokens.size() > 1 || tokens.get(0).lexeme != input
        where:
        input    | _
        "332-3"  | _
        "3232e-" | _
        "323.e"  | _
    }

    def "Testing reading correct words"(def input) {
        when:
        def tokens = tokenizer.readTokens(input)
        then:
        tokens.size() == 1
        tokens.get(0).lexeme == input
        tokens.get(0).tokenClass == TokenClass.WORD
        where:
        input      | _
        "a"        | _
        "aaaaaaaa" | _
        "a1"       | _
        "a\$1_2fd" | _
        "__\$ds"   | _
        "\$\$"     | _
        "_____12"  | _
    }

    def "Teting reading incorrect words"() {
        when:
        def tokens = tokenizer.readTokens("3fsfds");
        then:
        tokens.size() != 1
    }

    def "Testing reading space or tabulator"(input) {
        when:
        def tokens = tokenizer.readTokens(input)
        then:
        tokens.size() == 1
        tokens.get(0).lexeme == input
        tokens.get(0).tokenClass == TokenClass.SPACE
        where:
        input    | _
        " "      | _
        "\t"     | _
        "\t \t " | _
        "   \t " | _
    }

    def "Testing reading new lines"(input) {
        when:
        def tokens = tokenizer.readTokens(input)
        then:
        tokens.size() == 1
        tokens.get(0).lexeme == input
        tokens.get(0).tokenClass == TokenClass.NEW_LINE
        where:
        input       | _
        "\n"        | _
        "\n\n\n"    | _
        "\n \t \n " | _
    }

    def "Testing reading operators"(input) {
        when:
        def tokens = tokenizer.readTokens(input)
        then:
        tokens.size() == 1
        tokens.get(0).lexeme == input
        tokens.get(0).tokenClass == TokenClass.OPERATOR
        where:
        input | _
        "*"   | _
        "+"   | _
        "/"   | _
        "-"   | _
    }

    def "Testing reading symbols"(def input, def tokenClass) {
        when:
        def tokens = tokenizer.readTokens(input);
        then:
        tokens.size() == 1
        tokens.get(0).lexeme == input
        tokens.get(0).tokenClass == tokenClass
        where:
        input || tokenClass
        ","   || TokenClass.COMMA
        ":"   || TokenClass.COLON
        ";"   || TokenClass.SEMICOLON
        "("   || TokenClass.OPEN_PARENTHESIS
        ")"   || TokenClass.CLOSE_PARENTHESIS
        "["   || TokenClass.OPEN_BRACKET
        "]"   || TokenClass.CLOSE_BRACKET
    }
}
