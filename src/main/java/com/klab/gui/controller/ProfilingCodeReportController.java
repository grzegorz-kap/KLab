package com.klab.gui.controller;

import com.klab.gui.helpers.HtmlUtils;
import com.klab.interpreter.commons.analyze.CodeLine;
import com.klab.interpreter.profiling.ReportService;
import com.klab.interpreter.profiling.model.CodeReport;
import com.klab.interpreter.profiling.model.ProfilingData;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ProfilingCodeReportController implements
        ProfilingCodeReportDetailsViewer, CustomInitializeble, ResourceLoaderAware {
    private ResourceLoader resourceLoader;
    private CodeReport codeReport;
    private ReportService reportService;

    @FXML
    private WebView codeListening;

    @Override
    public void customInit() {
        Resource resource = resourceLoader.getResource("classpath:html/code-profiling-table.html");
        try (InputStream in = resource.getInputStream()) {
            String html = IOUtils.toString(in, "UTF-8");
            codeListening.getEngine().loadContent(html.replace("{{content}}", createTableRows()));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private String createTableRows() {
        StringBuilder builder = new StringBuilder();
        String sourceCode = codeReport.getCode().getSourceCode();
        String[] lines = sourceCode.split("\\r?\\n");
        reportService.computeLines(codeReport);

        for (int index = 0; index < lines.length; index++) {
            ProfilingData<CodeLine> line = codeReport.getLinesProfile().get(index + 1);
            builder.append("<tr>");
            builder.append("<td style='text-align:right;'>")
                    .append(line != null ? line.getTimeSeconds() : 0.00)
                    .append(" s</td>");
            builder.append("<td>").append(line != null ? line.getCount() : 0).append("</td>");
            builder
                    .append("<td>")
                    .append(HtmlUtils.formatCode(lines[index]))
                    .append("</td>");
            builder.append("</tr>");
        }
        return builder.toString();
    }

    @Override
    public void setProfilingCodeReport(CodeReport codeReport) {
        this.codeReport = codeReport;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Autowired
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }
}
