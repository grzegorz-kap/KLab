package interpreter.parsing.service

import interpreter.parsing.model.BalanceContext
import interpreter.parsing.model.BalanceType
import spock.lang.Specification

class BalanceContextServiceTest extends Specification {

    def balanceService = new BalanceContextService()
    def parseContextManager = Mock(ParseContextManager)
    def balanceContext = Mock(BalanceContext)

    def "Test add method"() {
        given:
        def balanceType = BalanceType.INSIDE_MATRIX
        when:
        balanceService.add(parseContextManager, balanceType)
        then:
        1 * parseContextManager.getBalanceContext() >> balanceContext
        1 * balanceContext.put(balanceType)
    }

    def "Test pop method"() {
        when:
        balanceService.popOrThrow(parseContextManager, BalanceType.INSIDE_MATRIX)
        then:
        1 * parseContextManager.getBalanceContext() >> balanceContext
        1 * balanceContext.pop() >> BalanceType.INSIDE_MATRIX
        notThrown(Exception)
    }

    def "Test pop method with exception"() {
        when:
        balanceService.popOrThrow(parseContextManager, BalanceType.INSIDE_MATRIX)
        then:
        1 * parseContextManager.getBalanceContext() >> balanceContext
        1 * balanceContext.pop() >> BalanceType.NORMAL
        thrown(Exception)
    }
}
