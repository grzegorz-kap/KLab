package interpreter.core;

import common.EventService;
import interpreter.core.events.ScriptChangeEvent;
import interpreter.utils.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class ScriptFileServiceImpl implements ScriptFileService, InitializingBean {
    private EventService eventService;
    private String scriptRegex = "[A-Za-z][A-Za-z0-9_]*[.]m$";
    private String workingDirectory;
    private String applicationName;

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
        watchForFilesChange();
    }

    @Override
    public List<Path> listScripts() throws IOException {
        return Files.list(Paths.get(workingDirectory))
                .filter(path -> path.getFileName().toString().matches(scriptRegex))
                .collect(Collectors.toList());
    }

    @Override
    public String readScript(String scriptName) throws IOException {
        scriptName = FilenameUtils.removeExtension(scriptName);
        Path path = Paths.get(workingDirectory, String.format("%s%s", scriptName, ".m"));
        return new String(Files.readAllBytes(path)).replaceAll("\\r\\n", "\n");
    }

    private void init() throws IOException {
        if (Objects.isNull(workingDirectory)) {
            Path path = Paths.get(FileUtils.getDocumentsPath(), applicationName);
            workingDirectory = path.toString();
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }
        }
    }

    private void watchForFilesChange() {
        Consumer<WatchEvent<?>> consumer = (WatchEvent<?> ev) -> {
            if (ev.context().toString().matches(scriptRegex)) {
                eventService.publish(new ScriptChangeEvent(ev.context().toString(), this, ev.kind()));
            }
        };
        Thread thread = new Thread(new FileWatcher(workingDirectory, consumer));
        thread.setDaemon(true);
        thread.start();
    }

    @Value("${app.name}")
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}
