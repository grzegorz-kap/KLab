package com.klab.interpreter.service.functions.external

import com.klab.interpreter.AbstractServiceTest
import org.springframework.beans.factory.annotation.Autowired

class ExternalFunctionParserTest extends AbstractServiceTest {
    @Autowired
    private ExternalFunctionParser parser;

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
