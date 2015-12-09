package interpreter.core;

import java.io.IOException;

public interface ScriptFileService {
    String readScript(String scriptName) throws IOException;
}
