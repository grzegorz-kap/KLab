package interpreter.core

import interpreter.AbstractServiceTest
import interpreter.core.code.CodeGenerator
import interpreter.translate.model.InstructionCode
import org.springframework.beans.factory.annotation.Autowired

class CodeGeneratorTest extends AbstractServiceTest {

    @Autowired
    private CodeGenerator codeGenerator;

    def "Test translate method"() {
        when:
        def code = codeGenerator.translate("2+3;")

        then:
        code != null
        code.size() == 4
        code.instructions.collect { it.instructionCode } == [
                InstructionCode.PUSH,
                InstructionCode.PUSH,
                InstructionCode.ADD,
                InstructionCode.ANS
        ]
        code.instructions.get(0).data[0].value == 2.0D
        code.instructions.get(1).data[0].value == 3.0D;
    }
}
