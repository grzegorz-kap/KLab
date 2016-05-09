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
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MainController implements CustomInitializeble {
    private EventService eventService;
    private CommandHistoryService commandHistoryService;
    private GuiContext guiContext;
    private TreeItem<String> nowHistory = new TreeItem<>("Teraz");

    @FXML
    private TreeView<String> commandTree;

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
        for (Map.Entry<Date, List<Command>> day : allByDay.entrySet()) {
            TreeItem<String> dayRoot = new TreeItem<>(format.format(day.getKey()));
            List<TreeItem<String>> collect = Lists.reverse(day.getValue()).stream()
                    .map(Command::getContent)
                    .filter(StringUtils::isNoneBlank)
                    .map(TreeItem::new)
                    .collect(Collectors.toList());
            dayRoot.getChildren().addAll(collect);
            dayRoot.setExpanded(true);
            rootHistory.getChildren().add(dayRoot);
        }
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
    public void setCommandHistoryService(CommandHistoryService commandHistoryService) {
        this.commandHistoryService = commandHistoryService;
    }
}
