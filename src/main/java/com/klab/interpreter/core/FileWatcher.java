package com.klab.interpreter.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.*;
import java.util.List;
import java.util.function.Consumer;

import static java.nio.file.StandardWatchEventKinds.*;

public class FileWatcher implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileWatcher.class);
    private Path path;
    private Consumer<WatchEvent<?>> eventHandler;

    public FileWatcher(String path, Consumer<WatchEvent<?>> eventHandler) {
        this.path = Paths.get(path);
        this.eventHandler = eventHandler;
    }

    @Override
    public void run() {
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            this.path.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
            while (!Thread.currentThread().isInterrupted()) {
                WatchKey watchKey = watchService.take();
                List<WatchEvent<?>> events = watchKey.pollEvents();
                for (WatchEvent<?> event : events) {
                    if (event.kind() == ENTRY_CREATE) {
                        LOGGER.info("Created: " + event.context().toString());
                    }
                    if (event.kind() == ENTRY_DELETE) {
                        LOGGER.info("Delete: " + event.context().toString());
                    }
                    if (event.kind() == ENTRY_MODIFY) {
                        LOGGER.info("Modify: " + event.context().toString());
                    }
                    eventHandler.accept(event);
                }
                watchKey.reset();
            }
        } catch (Exception ex) {
            LOGGER.error("Error", ex);
        }
    }
}
