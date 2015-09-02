package console;

import interpreter.lexer.model.TokenList;
import interpreter.lexer.service.RegexTokenizer;
import interpreter.lexer.service.Tokenizer;

public class App {
    public static void main(String[] args){
        Tokenizer tokenizer = new RegexTokenizer();
        TokenList tokens = tokenizer.readTokens("323.3232 \tsdad * - / + \n \n \t f");
        tokens.stream().forEach( token -> System.out.println(String.format("%s\t %s", token.getLexeme(), token.getTokenClass())));
    }
}
