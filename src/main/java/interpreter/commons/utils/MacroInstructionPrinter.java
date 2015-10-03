package interpreter.commons.utils;

import interpreter.translate.model.MacroInstruction;
import org.springframework.stereotype.Service;

@Service
public class MacroInstructionPrinter {

    public String print(MacroInstruction macroInstruction) {
        StringBuilder stringBuilder = new StringBuilder();
        macroInstruction.forEach(instruction -> {
            stringBuilder.append(instruction.getInstructionCode());
            stringBuilder.append('\t');
            instruction.forEachObjectData(objectData -> stringBuilder.append(objectData).append("\t"));
            stringBuilder.append("\n");
        });
        return stringBuilder.toString();
    }

}
