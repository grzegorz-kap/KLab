package interpreter.service.functions.external

import spock.lang.Specification


class ExternalFunctionParserTest extends Specification {
    def parser = new ExternalFunctionParser();

    def "Test parse method"() {
        when:
        def result = parser.parse("     \t         \n" +
                "   function  \t [   output  ,  output2  output3]    = name(a,   b ,  \t c)   \n" +
                "       dupa = a + b   \n" +
                "       output = 1;     \n" +
                "   end  \n \t " +
                " ");
        then:
        result.arguments.size() == 3
        result.returns.size() == 3
        result.name == 'name'
    }
}
