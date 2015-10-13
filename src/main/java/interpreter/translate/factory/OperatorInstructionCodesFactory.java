package interpreter.translate.factory;

import interpreter.parsing.model.tokens.operators.OperatorCode;
import interpreter.translate.model.instruction.InstructionCode;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
public class OperatorInstructionCodesFactory {

    private Map<OperatorCode, InstructionCode> instructionCodeMap = new EnumMap<>(OperatorCode.class);

    public OperatorInstructionCodesFactory() {
        instructionCodeMap.put(OperatorCode.ADD, InstructionCode.ADD);
        instructionCodeMap.put(OperatorCode.DIV, InstructionCode.DIV);
        instructionCodeMap.put(OperatorCode.MULT, InstructionCode.MULT);
        instructionCodeMap.put(OperatorCode.SUB, InstructionCode.SUB);
        instructionCodeMap.put(OperatorCode.ASSIGN, InstructionCode.STORE);
        instructionCodeMap.put(OperatorCode.EQ, InstructionCode.EQ);
        instructionCodeMap.put(OperatorCode.NEQ, InstructionCode.NEQ);
    }

    public InstructionCode get(OperatorCode operatorCode) {
        return instructionCodeMap.get(operatorCode);
    }
}
