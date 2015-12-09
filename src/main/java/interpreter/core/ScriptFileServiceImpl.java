package interpreter.core;

import interpreter.utils.FileUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class ScriptFileServiceImpl implements ScriptFileService, InitializingBean {

    private String scriptExtension;
    private String workingDirectory;
    private String applicationName;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (Objects.isNull(workingDirectory)) {
            Path path = Paths.get(FileUtils.getDocumentsPath(), applicationName);
            workingDirectory = path.toString();
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }
        }
    }

    @Override
    public String readScript(String scriptName) throws IOException {
        Path path = Paths.get(workingDirectory, String.format("%s%s", scriptName, scriptExtension));
        return new String(Files.readAllBytes(path));
    }

    @Value("${app.name}")
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    @Value("${app.script.extension}")
    public void setScriptExtension(String scriptExtension) {
        this.scriptExtension = scriptExtension;
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }
}
