package com.klab.gui.controller;

import com.google.common.eventbus.Subscribe;
import com.klab.gui.ProfilingCodeReportDetailsViewer;
import com.klab.gui.config.GuiContext;
import com.klab.interpreter.core.events.ExecutionCompletedEvent;
import com.klab.interpreter.profiling.ProfilingService;
import com.klab.interpreter.profiling.ReportService;
import com.klab.interpreter.profiling.model.CodeReport;
import com.klab.interpreter.profiling.model.ProfilingReport;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ProfilingReportController implements Initializable {
    private GuiContext guiContext;
    private ProfilingService profilingService;
    private ReportService reportService;

    // FXML
    public TableView<CodeReport> profileSummary;
    public TableColumn<CodeReport, String> titleColumn;
    public TableColumn<CodeReport, String> callsColumn;
    public TableColumn<CodeReport, String> totalTimeColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titleColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTitle()));
        callsColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getCalled())));
        totalTimeColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.format("%f s", cell.getValue().getTotalTime() / 1000_000_000.0)));

        profileSummary.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                CodeReport selected = profileSummary.getSelectionModel().getSelectedItem();
                Pane root = guiContext.loadScene("code-profiling-report.fxml", (ProfilingCodeReportDetailsViewer viewer) -> {
                    viewer.setProfilingCodeReport(selected);
                });
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }
        });
    }

    @Subscribe
    public void onExecutionCompleted(ExecutionCompletedEvent event) {
        Platform.runLater(() -> guiContext.showProfilingStage());
        ProfilingReport report = reportService.process(profilingService.measured());
        profileSummary.getItems().clear();
        profileSummary.getItems().addAll(report.getCodeReports());
    }

    @Autowired
    public void setGuiContext(GuiContext guiContext) {
        this.guiContext = guiContext;
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
