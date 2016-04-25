package com.klab.interpreter.analyze;

import com.klab.interpreter.execution.model.Code;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class CodeLinerImpl implements CodeLiner {
    @Override
    public List<CodeLine> separate(Code code) {
        String[] lines = code.getSourceCode().split("\\r?\\n");

        return code.instructions()
                .filter(instruction -> instruction.getCodeAddress() != null)
                .collect(groupingBy(instruction -> instruction.getCodeAddress().getLine()))
                .entrySet().stream()
                .map(e -> {
                    CodeLine line = new CodeLine();
                    line.setInstructions(e.getValue());
                    line.setNumber(e.getKey());
                    line.setLine(lines[e.getKey() - 1]);
                    return line;
                })
                .sorted(Comparator.comparingLong(CodeLine::getNumber))
                .collect(Collectors.toList());
    }
}
