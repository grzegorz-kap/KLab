package com.klab.interpreter.profiling;

import com.google.common.collect.Maps;
import com.klab.interpreter.analyze.CodeLine;
import com.klab.interpreter.analyze.CodeLiner;
import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.profiling.model.CodeReport;
import com.klab.interpreter.profiling.model.ProfilingData;
import com.klab.interpreter.profiling.model.ProfilingReport;
import com.klab.interpreter.profiling.model.SourceType;
import com.klab.interpreter.translate.model.Instruction;
import com.klab.interpreter.translate.model.InstructionCode;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

import static com.google.common.base.MoreObjects.firstNonNull;
import static com.klab.interpreter.translate.model.InstructionCode.FUNCTION_END;
import static com.klab.interpreter.translate.model.InstructionCode.SCRIPT_EXIT;

@Service
public class ReportServiceImpl implements ReportService {
    private static final Map<InstructionCode, SourceType> SOURCE_TYPE_MAP = new EnumMap<InstructionCode, SourceType>(InstructionCode.class) {
        {
            put(SCRIPT_EXIT, SourceType.SCRIPT);
            put(FUNCTION_END, SourceType.FUNCTION);
        }
    };
    private static final String COMMAND = "command";

    private CodeLiner codeLiner;

    @Override
    public ProfilingReport process(Collection<Code> measured) {
        ProfilingReport report = new ProfilingReport();
        measured.stream()
                .map(this::codeReport)
                .peek(codeReport -> codeReport.setParent(report))
                .forEach(report::add);
        return report;
    }

    private CodeReport codeReport(Code code) {
        CodeReport report = new CodeReport(code, resolveSourceType(code));
        report.setCalled(report.getSourceType() == SourceType.COMMAND ? 1 : callCount(code));
        report.setTotalTime(totalTime(code));
        report.setTitle(report.getSourceType() == SourceType.COMMAND ? COMMAND : code.getSourceId());
        return report;
    }

    private SourceType resolveSourceType(Code code) {
        InstructionCode result = code.instructions().map(Instruction::getInstructionCode)
                .filter(c -> c == SCRIPT_EXIT || c == FUNCTION_END)
                .findAny()
                .orElse(null);
        return firstNonNull(SOURCE_TYPE_MAP.get(result), SourceType.COMMAND);
    }

    private long callCount(Code code) {
        Instruction instruction = code.instructions()
                .filter(i -> SOURCE_TYPE_MAP.keySet().contains(i.getInstructionCode()))
                .findAny()
                .orElseThrow(RuntimeException::new);
        return instruction.getProfilingData() == null ? 1L : instruction.getProfilingData().getCount();
    }

    private long totalTime(Code code) {
        return code.instructions()
                .map(Instruction::getProfilingData)
                .filter(Objects::nonNull)
                .mapToLong(ProfilingData::getTime)
                .sum();
    }

    @Override
    public void computeLines(CodeReport codeReport) {
        if (!MapUtils.isNotEmpty(codeReport.getLinesProfile())) {
            Map<Integer, ProfilingData<CodeLine>> map = Maps.newLinkedHashMap();
            codeLiner.separate(codeReport.getCode()).stream()
                    .map(ProfilingData::new)
                    .peek(data -> {
                        long sum = data.getSubject().getInstructions().stream()
                                .map(Instruction::getProfilingData)
                                .filter(Objects::nonNull)
                                .mapToLong(ProfilingData::getTime).sum();
                        long count = data.getSubject().getInstructions().stream()
                                .map(Instruction::getProfilingData)
                                .filter(Objects::nonNull)
                                .mapToLong(ProfilingData::getCount)
                                .max().orElse(0L);
                        data.setCount(count);
                        data.setTime(sum);
                    })
                    .forEach(data -> map.put(data.getSubject().getNumber(), data));
            codeReport.setLinesProfile(map);
        }
    }

    @Autowired
    public void setCodeLiner(CodeLiner codeLiner) {
        this.codeLiner = codeLiner;
    }
}
