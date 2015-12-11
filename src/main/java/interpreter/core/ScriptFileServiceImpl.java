package interpreter.core;

import interpreter.utils.FileUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ScriptFileServiceImpl implements ScriptFileService, InitializingBean {

    private String scriptRegex = "[A-Za-z][A-Za-z0-9_]*[.]m$";
    private String workingDirectory;
    private String applicationName;

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    @Override
    public List<Path> listScripts() throws IOException {
        return Files.list(Paths.get(workingDirectory))
                .filter(path -> path.getFileName().toString().matches(scriptRegex))
                .collect(Collectors.toList());
    }

    @Override
    public String readScript(String scriptName) throws IOException {
        Path path = Paths.get(workingDirectory, String.format("%s%s", scriptName, ".m"));
        return new String(Files.readAllBytes(path));
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

    @Value("${app.name}")
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }
}
