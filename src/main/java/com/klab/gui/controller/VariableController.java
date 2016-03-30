package com.klab.gui.controller;

import com.google.common.collect.Maps;
import com.google.common.eventbus.Subscribe;
import com.klab.gui.model.Variable;
import com.klab.interpreter.commons.memory.MemorySpace;
import com.klab.interpreter.commons.memory.ObjectWrapper;
import com.klab.interpreter.core.Interpreter;
import com.klab.interpreter.core.events.ExecutionCompletedEvent;
import com.klab.interpreter.debug.BreakpointReachedEvent;
import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.Sizeable;
import com.klab.interpreter.types.matrix.Matrix;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.LongStream;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class VariableController implements Initializable {
    private Interpreter interpreter;
    private MemorySpace memorySpace;
    private int currentScope = Integer.MIN_VALUE;
    private Map<ObjectWrapper, Variable<TitledPane>> variablesMap = Maps.newHashMap();

    @FXML
    private VBox variablesBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Subscribe
    private void onExecutionCompleteEvent(ExecutionCompletedEvent event) {
        Platform.runLater(this::refreshVariables);
    }

    @Subscribe
    private void onBreakPointReachedEvent(BreakpointReachedEvent event) {
        Platform.runLater(this::refreshVariables);
    }

    private void refreshVariables() {
        if (memorySpace.scopeId() != currentScope) {
            variablesBox.getChildren().clear();
            variablesMap.clear();
        }

        memorySpace.listCurrentScopeVariables()
                .filter(this::isUpdated)
                .peek(this::removeUpdated)
                .filter(objectWrapper -> Objects.nonNull(objectWrapper.getData()))
                .filter(objectWrapper -> Objects.nonNull(objectWrapper.getData().getName()))
                .map(this::createNew)
                .peek(var -> variablesBox.getChildren().add(var.getNode()))
                .forEach(var -> variablesMap.put(var.getObjectWrapper(), var));
        currentScope = memorySpace.scopeId();
    }

    private Variable<TitledPane> createNew(ObjectWrapper variable) {
        TableView<VariableRow> tableView = new TableView<>();
        tableView.setEditable(true);
        long rows = ((Sizeable) variable.getData()).getRows();
        long columns = ((Sizeable) variable.getData()).getColumns();

        LongStream.range(0L, rows)
                .mapToObj(m -> new VariableRow(m, variable.getData()))
                .forEach(row -> tableView.getItems().add(row));

        LongStream.range(0L, columns).forEach(n -> {
            TableColumn<VariableRow, String> column = new TableColumn<>();
            column.setSortable(false);
            column.setText(String.valueOf(n + 1));
            column.setCellFactory(TextFieldTableCell.forTableColumn());
            column.setCellValueFactory(param -> {
                ObjectData objectData = param.getValue().getObjectData();
                if (objectData instanceof Matrix) {
                    Matrix matrix = (Matrix) objectData;
                    return new SimpleStringProperty(matrix.get(param.getValue().getRowNumber(), n).toString());
                } else {
                    return new SimpleStringProperty(objectData.toString());
                }

            });
            column.setEditable(true);
            column.setOnEditCommit(t -> {
                String name = variable.getData().getName();
                int i = t.getTablePosition().getRow();
                int j = t.getTablePosition().getColumn();
                String command = String.format("%s(%s,%s)=%s;", name, i + 1, j + 1, t.getNewValue());
                interpreter.startSync(command);
                refreshVariables();
            });
            tableView.getColumns().add(column);
        });

        TitledPane titledPane = new TitledPane(variable.getData().getName(), tableView);
        titledPane.setExpanded(false);
        return new Variable<>(titledPane, variable);
    }

    private void removeUpdated(ObjectWrapper variable) {
        Variable<TitledPane> var = variablesMap.remove(variable);
        if (var != null) {
            variablesBox.getChildren().remove(var.getNode());
        }
    }

    private boolean isUpdated(ObjectWrapper oldOne) {
        Variable<TitledPane> newOne = variablesMap.get(oldOne);
        return newOne == null || newOne.getVersion() != oldOne.getVersion();
    }

    @Autowired
    public void setInterpreter(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    @Autowired
    public void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }

    private static class VariableRow {
        private long rowNumber;
        private ObjectData objectData;

        public VariableRow(long rowNumber, ObjectData objectData) {
            this.rowNumber = rowNumber;
            this.objectData = objectData;
        }

        public long getRowNumber() {
            return rowNumber;
        }

        public ObjectData getObjectData() {
            return objectData;
        }
    }
}
