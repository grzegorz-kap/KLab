package interpreter.lexer.model;

public class RegexTokenizerContext extends TokenizerContext {

    private String regexInput;
    private int index = 0;

    public RegexTokenizerContext(String inputText) {
        super(inputText);
        regexInput = inputText;
    }

    @Override
    public String getInputText() {
        return regexInput;
    }

    @Override
    public void setIndex(int index) {
        int start = index - this.index;
        int end = regexInput.length();
        if (start > end) {
            regexInput = "";
        } else {
            regexInput = regexInput.substring(start, end);
        }
        this.index = index;
        super.setIndex(index);
    }
}
