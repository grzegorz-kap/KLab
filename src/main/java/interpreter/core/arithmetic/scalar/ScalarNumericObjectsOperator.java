package interpreter.core.arithmetic.scalar;

import interpreter.core.arithmetic.NumericObjectsOperator;
import interpreter.types.NumericObject;
import interpreter.types.NumericType;
import org.springframework.beans.factory.annotation.Required;

public class ScalarNumericObjectsOperator<N extends Number> implements NumericObjectsOperator {
    private NumericType supportedType;
    private AbstractComparator<N> comparator;
    private ScalarOperator<N> adder;
    private ScalarOperator<N> div;
    private ScalarOperator<N> mult;
    private ScalarOperator<N> sub;

    @Override
    public NumericType getSupportedType() {
        return supportedType;
    }

    @Required
    public void setSupportedType(NumericType supportedType) {
        this.supportedType = supportedType;
    }

    @Override
    public NumericObject add(NumericObject a, NumericObject b) {
        return adder.operate(a, b);
    }

    @Override
    public NumericObject mult(NumericObject a, NumericObject b) {
        return mult.operate(a, b);
    }
    
    @Override
	public NumericObject pow(NumericObject a, NumericObject b) {
		return mult(a,b);
	}

    @Override
    public NumericObject amult(NumericObject a, NumericObject b) {
        return mult.operate(a, b);
    }

    @Override
    public NumericObject sub(NumericObject a, NumericObject b) {
        return sub.operate(a, b);
    }

    @Override
    public NumericObject div(NumericObject a, NumericObject b) {
        return div.operate(a, b);
    }

    @Override
    public NumericObject eq(NumericObject a, NumericObject b) {
        return comparator.eq(a, b);
    }

    @Override
    public NumericObject neq(NumericObject a, NumericObject b) {
        return comparator.neq(a, b);
    }

    @Override
    public NumericObject gt(NumericObject a, NumericObject b) {
        return comparator.gt(a, b);
    }

    @Override
    public NumericObject ge(NumericObject a, NumericObject b) {
        return comparator.ge(a, b);
    }

    @Override
    public NumericObject le(NumericObject a, NumericObject b) {
        return comparator.le(a, b);
    }

    @Override
    public NumericObject lt(NumericObject a, NumericObject b) {
        return comparator.lt(a, b);
    }

    @Required
    public void setComparator(AbstractComparator<N> comparator) {
        this.comparator = comparator;
    }

    @Required
    public void setAdder(ScalarOperator<N> adder) {
        this.adder = adder;
    }

    @Required
    public void setDiv(ScalarOperator<N> div) {
        this.div = div;
    }

    @Required
    public void setMult(ScalarOperator<N> mult) {
        this.mult = mult;
    }

    @Required
    public void setSub(ScalarOperator<N> sub) {
        this.sub = sub;
    }
}
