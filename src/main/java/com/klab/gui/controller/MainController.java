package com.klab.gui.controller;

import com.google.common.eventbus.Subscribe;
import com.klab.interpreter.core.events.ExecutionCompletedEvent;
import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.profiling.ProfilingService;
import com.klab.interpreter.profiling.ReportService;
import com.klab.interpreter.profiling.model.ProfilingReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MainController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    private ProfilingService profilingService;
    private ReportService reportService;

    @Subscribe
    public void onExecutionCompleted(ExecutionCompletedEvent event) {
        Collection<Code> measured = profilingService.measured();
        ProfilingReport report = reportService.process(measured);
        return;
    }

    @Autowired
    public void setProfilingService(ProfilingService profilingService) {
        this.profilingService = profilingService;
    }

    @Autowired
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }
}
