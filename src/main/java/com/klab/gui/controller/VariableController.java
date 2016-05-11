package com.klab.gui.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.eventbus.Subscribe;
import com.klab.gui.model.Variable;
import com.klab.interpreter.commons.memory.MemorySpace;
import com.klab.interpreter.commons.memory.ObjectWrapper;
import com.klab.interpreter.core.ExecutionCommand;
import com.klab.interpreter.core.Interpreter;
import com.klab.interpreter.core.events.ExecutionCompletedEvent;
import com.klab.interpreter.debug.BreakpointReachedEvent;
import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.Sizeable;
import com.klab.interpreter.types.matrix.Matrix;
import com.klab.interpreter.types.scalar.Scalar;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class VariableController implements Initializable {
    private Interpreter interpreter;
    private MemorySpace memorySpace;
    private int currentScope = Integer.MIN_VALUE;
    private Map<ObjectWrapper, Variable<TitledPane>> variablesMap = Maps.newHashMap();
    private int maxCellsToDisplay = 300;

    @FXML
    private ScrollPane variableScrollPanel;

    @FXML
    private VBox variablesBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        variablesBox.prefWidthProperty().bind(variableScrollPanel.widthProperty().subtract(15));
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
        TableView<Row> tableView = new TableView<>();
        tableView.widthProperty().addListener(new NumberChangeListenerHideHeaderRow(tableView));
        tableView.setEditable(true);
        int rows = (int) ((Sizeable) variable.getData()).getRows();
        int columns = (int) ((Sizeable) variable.getData()).getColumns();
        int cells = rows * columns;

        if (cells <= maxCellsToDisplay) {
            IntStream.range(0, rows)
                    .mapToObj(r -> new Row(variable.getData(), r))
                    .forEach(row -> tableView.getItems().add(row));

            int col = tableView.getItems().stream().mapToInt(row -> row.cells.size()).findFirst().orElse(0);
            IntStream.range(0, col).forEach(n -> {
                TableColumn<Row, String> column = new TableColumn<>();
                column.setSortable(false);
                column.setCellFactory(TextFieldTableCell.forTableColumn());
                column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(n)));
                column.setEditable(true);
                column.setOnEditCommit(t -> {
                    String name = variable.getData().getName();
                    int i = t.getTablePosition().getRow();
                    int j = t.getTablePosition().getColumn();
                    String command = String.format("%s(%s,%s)=%s;", name, i + 1, j + 1, t.getNewValue());
                    interpreter.startSync(new ExecutionCommand(command));
                    variablesMap.get(variable).setVersion(variable.getVersion());
                    t.getTableView().getItems().get(i).modify(variable.getData(), i, j);
                });
                tableView.getColumns().add(column);
            });
        }


        String value = variable.getData() instanceof Scalar ? variable.getData().toString() :
                String.format("%d x %d", rows, columns);
        String name = String.format("%s (%s)", variable.getData().getName(), value);
        TitledPane titledPane = new TitledPane(name, tableView);
        titledPane.prefWidthProperty().bind(variablesBox.widthProperty().subtract(15));
        titledPane.setDisable(cells > maxCellsToDisplay);
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

    private static class Row {
        private ObjectData data;
        private List<SimpleStringProperty> cells;

        Row(ObjectData data, int rowNumber) {
            this.data = data;
            if (data instanceof Matrix) {
                Matrix matrix = (Matrix) data;
                cells = IntStream.range(0, (int) matrix.getColumns())
                        .mapToObj(n -> matrix.get(rowNumber, n).toString())
                        .map(SimpleStringProperty::new)
                        .collect(Collectors.toList());
            } else {
                cells = Lists.newArrayList(new SimpleStringProperty(data.toString()));
            }
        }

        void modify(ObjectData data, int row, int column) {
            if (data instanceof Matrix) {
                Matrix matrix = (Matrix) data;
                cells.get(column).setValue(matrix.get(row, column).toString());
            } else {
                cells.get(column).setValue(data.toString());
            }
        }

        String get(int index) {
            return cells.get(index).getValue();
        }

        public ObjectData getData() {
            return data;
        }

        public void setData(ObjectData data) {
            this.data = data;
        }
    }

    private static class NumberChangeListenerHideHeaderRow implements ChangeListener<Number> {
        private final TableView<Row> tableView;

        NumberChangeListenerHideHeaderRow(TableView<Row> tableView) {
            this.tableView = tableView;
        }

        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            Pane header = (Pane) tableView.lookup("TableHeaderRow");
            if (header.isVisible()) {
                header.setMaxHeight(0);
                header.setMinHeight(0);
                header.setPrefHeight(0);
                header.setVisible(false);
            }
        }
    }
}
