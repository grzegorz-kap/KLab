package interpreter.core;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface ScriptFileService {
    String readScript(String scriptName) throws IOException;

    void writeScript(String scriptName, String content) throws IOException;

    List<Path> listScripts() throws IOException;
}
