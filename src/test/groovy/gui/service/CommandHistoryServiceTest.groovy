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
        commandHistory.currentPosition == 1
        Duration.between(commandHistory.commands[0].createdAt, LocalDateTime.now()).toMillis() < 1000
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

    def "Test keyUp and keyDown methods"() {
        given:
        def commandHistory = new CommandHistory()
        10.times { index -> historyService.addCommand(commandHistory, (index + 1).toString())
        }

        when: "keyUp method"
        def result = historyService.keyUp(commandHistory)
        def result2 = historyService.keyUp(commandHistory)
        then: "result is correct"
        result == "10"
        result2 == "9"
        commandHistory.currentPosition == 8

        when: "keyDown method"
        3.times { result = historyService.keyDown(commandHistory) }
        then:
        result == "10"
        commandHistory.currentPosition == 9
    }
}
