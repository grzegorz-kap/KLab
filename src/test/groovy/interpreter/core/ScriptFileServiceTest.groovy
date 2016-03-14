package com.klab.interpreter.core

import com.klab.interpreter.core.code.ScriptFileServiceImpl
import spock.lang.Specification

import java.nio.file.Paths

class ScriptFileServiceTest extends Specification {

    def service = new ScriptFileServiceImpl();

    def setup() {
        def resourcesPath = Paths.get(getClass().getResource("ScriptFileServiceTest").toURI());
        service.setWorkingDirectory(resourcesPath.toAbsolutePath().toString())
        service.setApplicationName("KLab")
        service.afterPropertiesSet();
    }

    def "Test read script"() {
        when:
        def result = service.readScript("script1")

        then:
        result == '2+3'
    }

    def "Test list script files"() {
        when:
        def list = service.listScripts();

        then: "two files should be found"
        list.size() == 2
        list[0].getFileName().toString() == 'script1.m'
        list[1].getFileName().toString() == 'script2.m'
    }
}
