package gui.service

import gui.model.CommandHistory
import spock.lang.Specification

import java.time.Duration
import java.time.LocalDateTime

class CommandHistoryServiceTest extends Specification {

    def historyService = new CommandHistoryService()

    def "Test add command method"() {
        given:
        def commandHistory = new CommandHistory()
        def command = "2+3+10"

        when:
        historyService.addCommand(commandHistory, command)

        then:
        commandHistory.getSize() == 1
        commandHistory.commands[0].content == command
        commandHistory.currentPosition == 0
        Duration.between(commandHistory.commands[0].createdAt, LocalDateTime.now()).toMillis() < 100
    }
}
