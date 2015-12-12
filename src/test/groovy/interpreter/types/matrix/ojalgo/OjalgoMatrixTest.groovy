package interpreter.types.matrix.ojalgo

import interpreter.types.matrix.Matrix
import org.ojalgo.matrix.store.PhysicalStore
import spock.lang.Specification

class OjalgoMatrixTest extends Specification {

    private Matrix<Double> doubleMatrix;
    private PhysicalStore<Double> physicalStore = Mock(PhysicalStore)

    def setup() {
        doubleMatrix = new OjalgoDoubleMatrix<>(physicalStore);
    }

    def "Test get method"() {
        when:
        def result = doubleMatrix.get(0, 0)

        then:
        1 * physicalStore.get(0, 0) >> 0.5
        result == 0.5d
    }

    def "Test set method"() {
        when:
        doubleMatrix.set(3, 4, 5.0D)

        then:
        1 * physicalStore.set(3, 4, 5.0D)
    }
}
