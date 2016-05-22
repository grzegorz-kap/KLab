package com.klab.gui.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klab.common.EventService;
import com.klab.gui.events.NewCommandEvent;
import com.klab.gui.model.Command;
import com.klab.gui.model.CommandHistory;
import com.klab.gui.model.CommandHistoryIterator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.groupingBy;
import static org.apache.commons.lang3.time.DateUtils.truncate;

@Service
public class CommandHistoryServiceImpl implements CommandHistoryService, InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandHistoryServiceImpl.class);

    private EventService eventService;
    private TaskExecutor taskExecutor;

    private Lock saveLock = new ReentrantLock();
    private CommandHistory commandHistory = new CommandHistory();
    private ObjectMapper objectMapper = new ObjectMapper();
    private Path path = Paths.get(FileUtils.getUserDirectoryPath(), "KLab");
    private Path file = Paths.get(path.toString(), "history.json");

    @Override
    public void afterPropertiesSet() throws Exception {
        Files.createDirectories(path);
        if (Files.exists(file)) {
            this.commandHistory = objectMapper.readValue(file.toFile(), CommandHistory.class);
        }
    }

    @Override
    public void add(String command) {
        if (isNewCommand(command)) {
            commandHistory.add(command);
            eventService.publish(new NewCommandEvent(command, this));
            taskExecutor.execute(this::saveToFile);
        }
    }

    private void saveToFile() {
        saveLock.lock();
        try (OutputStream out = Files.newOutputStream(file)) {
            byte[] bytes = objectMapper.writeValueAsBytes(commandHistory);
            IOUtils.write(bytes, out);
        } catch (IOException e) {
            LOGGER.error("Command history not saved", e);
        }
        saveLock.unlock();
    }

    @Override
    public Map<Date, List<Command>> getAllByDay() {
        return commandHistory.getCommands().stream()
                .collect(groupingBy(command -> truncate(command.getCreatedAt(), Calendar.DAY_OF_MONTH)));
    }

    @Override
    public CommandHistoryIterator iterator() {
        return new CommandHistoryIterator(commandHistory);
    }

    private boolean isNewCommand(String commandContent) {
        Command lastCommand = commandHistory.size() == 0 ? null : commandHistory.get(commandHistory.size() - 1);
        return isNull(lastCommand) || !lastCommand.getContent().equals(commandContent)
                || !DateUtils.isSameDay(lastCommand.getCreatedAt(), new Date());
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }
}
