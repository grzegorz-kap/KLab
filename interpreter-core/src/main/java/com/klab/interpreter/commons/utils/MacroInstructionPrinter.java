package com.klab.interpreter.commons.utils;

import com.klab.interpreter.translate.model.MacroInstruction;
import org.springframework.stereotype.Service;

@Service
public class MacroInstructionPrinter {

    public String print(MacroInstruction macroInstruction) {
        StringBuilder stringBuilder = new StringBuilder();
        macroInstruction.forEach(instruction -> {
            stringBuilder.append(instruction);
            stringBuilder.append("\n");
        });
        return stringBuilder.toString();
    }

}
