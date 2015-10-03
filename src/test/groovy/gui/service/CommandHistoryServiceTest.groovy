package gui.service

import gui.helpers.TimeHelper
import gui.model.CommandHistory
import spock.lang.Specification

import java.time.Duration
import java.time.LocalDateTime

import static com.natpryce.makeiteasy.MakeItEasy.a
import static com.natpryce.makeiteasy.MakeItEasy.make
import static gui.makers.CommandMaker.saveCommand

class CommandHistoryServiceTest extends Specification {

    def historyService = new CommandHistoryService()
    def timeHelper = Mock(TimeHelper)

    def setup() {
        historyService.setTimeHelper(timeHelper)
    }

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

    def "Test add the same command second"() {
        given: "Command already exist in history"
        def commandHistory = new CommandHistory()
        def command = make a(saveCommand)
        commandHistory.add(command)

        when: "trying to add the same commant"
        historyService.addCommand(commandHistory, command.content)
        then: "the command has not been added"
        1 * timeHelper.isTheSameDay(command.createdAt, _ as LocalDateTime) >> true
        commandHistory.getSize() == 1

        when: "when the day is diferrent"
        historyService.addCommand(commandHistory, command.content)
        then: "the command is added"
        1 * timeHelper.isTheSameDay(command.createdAt, _ as LocalDateTime) >> false
        commandHistory.getSize() == 2

        when: "adding completely new command"
        historyService.addCommand(commandHistory, "[1,2,3]")
        then: "the new command is added"
        commandHistory.getSize() == 3
    }
}
