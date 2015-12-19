package interpreter.service.functions.external;

import com.google.common.eventbus.Subscribe;
import interpreter.commons.IdentifierMapper;
import interpreter.core.ScriptFileService;
import interpreter.core.events.ScriptChangeEvent;
import interpreter.lexer.service.RegexTokenizer;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExternalFunctionServiceImpl implements ExternalFunctionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalFunctionServiceImpl.class);

    private IdentifierMapper identifierMapper;
    private ScriptFileService scriptFileService;
    private RegexTokenizer regexTokenizer;

    private Map<Integer, ExternalFunction> functionsCache = Collections.synchronizedMap(new HashMap<>());

    @Override
    public ExternalFunction loadFunction(Integer functionId, String functionName) {
        ExternalFunction externalFunction = functionsCache.get(functionId);
        return externalFunction != null ? externalFunction : read(functionId, functionName);
    }

    @Subscribe
    public void onScriptChangedEvent(ScriptChangeEvent event) {
        if (event.getType() == ScriptChangeEvent.Type.DELETED || event.getType() == ScriptChangeEvent.Type.UPDATED) {
            String name = FilenameUtils.removeExtension(event.getData());
            Integer address = identifierMapper.getExternalAddress(name);
            boolean result = address != null && functionsCache.remove(address) != null;
            if (result) {
                LOGGER.info("Function '{}' removed from cache, cause: {}", name, event.getType());
            }
        }
    }

    private ExternalFunction read(Integer functionId, String functionName) {
//        try {
////            String fileContent = scriptFileService.readScript(functionName);
////            TokenList tokenList = regexTokenizer.readTokens(fileContent);
////            ExternalFunction e = new ExternalFunctionParser(tokenList).parse();
////            System.out.println(tokenList);
//        } catch (IOException e) {
//            LOGGER.error("Error reading function '{}'. {}", functionName, e);
//        }
        return null; //TODO
    }

    @Autowired
    public void setIdentifierMapper(IdentifierMapper identifierMapper) {
        this.identifierMapper = identifierMapper;
    }

    @Autowired
    public void setScriptFileService(ScriptFileService scriptFileService) {
        this.scriptFileService = scriptFileService;
    }

    @Autowired
    public void setRegexTokenizer(RegexTokenizer regexTokenizer) {
        this.regexTokenizer = regexTokenizer;
    }
}
