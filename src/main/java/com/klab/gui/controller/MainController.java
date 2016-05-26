package com.klab.gui.controller;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.klab.common.EventService;
import com.klab.gui.CustomInitializeble;
import com.klab.gui.config.GuiContext;
import com.klab.gui.events.AppendCommandEvent;
import com.klab.gui.events.NewCommandEvent;
import com.klab.gui.events.OpenScriptEvent;
import com.klab.gui.model.Command;
import com.klab.gui.service.CommandHistoryService;
import com.klab.gui.service.ScriptViewService;
import com.klab.interpreter.commons.memory.MemorySpace;
import com.klab.interpreter.commons.memory.ObjectWrapper;
import com.klab.interpreter.core.events.ExecutionCompletedEvent;
import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.Sizeable;
import com.klab.interpreter.types.scalar.Scalar;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;
import static java.util.Map.Entry.comparingByKey;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MainController implements CustomInitializeble, Initializable {
    private EventService eventService;
    private CommandHistoryService commandHistoryService;
    private ScriptViewService scriptViewService;
    private MemorySpace memorySpace;
    private GuiContext guiContext;
    private TreeItem<String> nowHistory = new TreeItem<>("Teraz");

    @FXML
    private TreeView<String> commandTree;
    @FXML
    private TableView<ObjectData> mainVarTable;
    @FXML
    private TableColumn<ObjectData, String> varName;
    @FXML
    private TableColumn<ObjectData, String> varValue;

    @Subscribe
    private void onExecutionComplete(ExecutionCompletedEvent event) {
        mainVarTable.getItems().clear();
        List<ObjectData> vars = memorySpace.listCurrentScopeVariables()
                .map(ObjectWrapper::getData)
                .filter(Objects::nonNull)
                .filter(obj -> StringUtils.isNotEmpty(obj.getName()))
                .sorted(Comparator.comparing(ObjectData::getName))
                .limit(100)
                .collect(Collectors.toList());
        mainVarTable.getItems().addAll(vars);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        varName.setCellValueFactory(val -> new SimpleStringProperty(val.getValue().getName()));
        varValue.setCellValueFactory(val -> {
            String text = "";
            ObjectData data = val.getValue();
            if (data instanceof Scalar) {
                text = data.toString();
            } else if (data instanceof Sizeable) {
                text = ((Sizeable) data).formatToString();
            }
            return new SimpleStringProperty(text);
        });
    }

    @Override
    public void customInit() {
        TreeItem<String> rootHistory = new TreeItem<>("Command history");
        SimpleDateFormat format = new SimpleDateFormat("dd MMMMMMMMMMMMM yyyy");
        rootHistory.setExpanded(true);
        commandTree.setRoot(rootHistory);
        rootHistory.getChildren().add(nowHistory);
        nowHistory.setExpanded(true);

        commandTree.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                TreeItem<String> item = commandTree.getSelectionModel().getSelectedItem();
                if (item.isLeaf() && item.getParent() != rootHistory) {
                    eventService.publish(new AppendCommandEvent(item.getValue(), this));
                }
            }
        });

        Map<Date, List<Command>> allByDay = commandHistoryService.getAllByDay();
        allByDay.entrySet().stream()
                .sorted(reverseOrder(comparingByKey()))
                .limit(3000)
                .forEach(day -> {
                    TreeItem<String> dayRoot = new TreeItem<>(format.format(day.getKey()));
                    List<TreeItem<String>> collect = Lists.reverse(day.getValue()).stream()
                            .map(Command::getContent)
                            .filter(StringUtils::isNoneBlank)
                            .map(TreeItem::new)
                            .collect(Collectors.toList());
                    dayRoot.getChildren().addAll(collect);
                    dayRoot.setExpanded(true);
                    rootHistory.getChildren().add(dayRoot);
                });
    }

    public void newScript(ActionEvent actionEvent) throws IOException {
        scriptViewService.createNewScriptDialog();
    }

    @Subscribe
    public void onNewCommand(NewCommandEvent newCommandEvent) {
        if (StringUtils.isNoneBlank(newCommandEvent.getData())) {
            nowHistory.getChildren().add(0, new TreeItem<>(newCommandEvent.getData()));
        }
    }

    @Subscribe
    public void showEditorScreen(OpenScriptEvent event) throws IOException {
        guiContext.showScriptEditor();
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    public void setGuiContext(GuiContext guiContext) {
        this.guiContext = guiContext;
    }

    @Autowired
    public void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }

    @Autowired
    public void setCommandHistoryService(CommandHistoryService commandHistoryService) {
        this.commandHistoryService = commandHistoryService;
    }

    @Autowired
    public void setScriptViewService(ScriptViewService scriptViewService) {
        this.scriptViewService = scriptViewService;
    }
}
