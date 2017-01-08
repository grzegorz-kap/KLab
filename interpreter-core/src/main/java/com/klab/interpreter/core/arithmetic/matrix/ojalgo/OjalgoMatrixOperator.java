package com.klab.interpreter.core.arithmetic.matrix.ojalgo;

import com.klab.interpreter.core.arithmetic.NumericObjectsOperator;
import com.klab.interpreter.core.arithmetic.matrix.ojalgo.comparator.AbstractOjalgoMatrixComparator;
import com.klab.interpreter.types.NumericObject;
import com.klab.interpreter.types.NumericType;
import com.klab.interpreter.types.matrix.ojalgo.OjalgoMatrixCreator;
import org.springframework.beans.factory.annotation.Required;

public class OjalgoMatrixOperator<T extends Number> implements NumericObjectsOperator {
    private AbstractOjalgoMatrixComparator<T> comparator;
    private MatrixAdder<T> adder;
    private MatrixDivider<T> divider;
    private MatrixArrayDivider<T> arrayDivider;
    private MatrixMultiplicator<T> multiplicator;
    private MatrixArrayMult<T> arrayMult;
    private MatrixSubtractor<T> matrixSubtractor;
    private MatrixPower<T> matrixPower;
    private MatrixArrayPower<T> matrixArrayPower;
    private MatrixTranspose<T> matrixTranspose;
    private MatrixArrayConjuctionOperator<T> arrayAnd;
    private MatrixArrayDisjuctionOperator<T> arrayOr;
    private MatrixArrayNegate<T> negator;
    private NumericType supportedType;

    @Override
    public NumericObject adiv(NumericObject a, NumericObject b) {
        return arrayDivider.operate(a, b);
    }

    @Override
    public NumericType getSupportedType() {
        return supportedType;
    }

    @Required
    public void setSupportedType(NumericType supportedType) {
        this.supportedType = supportedType;
    }

    @Override
    public NumericObject negate(NumericObject a) {
        return negator.operatate(a);
    }

    @Override
    public NumericObject aand(NumericObject a, NumericObject b) {
        return arrayAnd.operate(a, b);
    }

    @Override
    public NumericObject aor(NumericObject a, NumericObject b) {
        return arrayOr.operate(a, b);
    }

    @Override
    public NumericObject transpose(NumericObject a) {
        return matrixTranspose.operate(a);
    }

    @Override
    public NumericObject add(NumericObject a, NumericObject b) {
        return adder.operate(a, b);
    }

    @Override
    public NumericObject mult(NumericObject a, NumericObject b) {
        return multiplicator.operate(a, b);
    }

    @Override
    public NumericObject amult(NumericObject a, NumericObject b) {
        return arrayMult.operate(a, b);
    }

    @Override
    public NumericObject pow(NumericObject a, NumericObject b) {
        return matrixPower.operate(a, b);
    }

    @Override
    public NumericObject apow(NumericObject a, NumericObject b) {
        return matrixArrayPower.operate(a, b);
    }

    @Override
    public NumericObject sub(NumericObject a, NumericObject b) {
        return matrixSubtractor.operate(a, b);
    }

    @Override
    public NumericObject div(NumericObject a, NumericObject b) {
        return divider.operate(a, b);
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
    public void setComparator(AbstractOjalgoMatrixComparator<T> comparator) {
        this.comparator = comparator;
    }

    @Required
    public void setMatrixCreator(OjalgoMatrixCreator<T> matrixCreator) {
    }

    @Required
    public void setAdder(MatrixAdder<T> adder) {
        this.adder = adder;
    }

    @Required
    public void setDivider(MatrixDivider<T> divider) {
        this.divider = divider;
    }

    @Required
    public void setArrayDivider(MatrixArrayDivider<T> arrayDivider) {
        this.arrayDivider = arrayDivider;
    }

    @Required
    public void setMultiplicator(MatrixMultiplicator<T> multiplicator) {
        this.multiplicator = multiplicator;
    }

    @Required
    public void setMatrixSubtractor(MatrixSubtractor<T> matrixSubtractor) {
        this.matrixSubtractor = matrixSubtractor;
    }

    @Required
    public void setMatrixPower(MatrixPower<T> matrixPower) {
        this.matrixPower = matrixPower;
    }

    @Required
    public void setMatrixArrayPower(MatrixArrayPower<T> matrixArrayPower) {
        this.matrixArrayPower = matrixArrayPower;
    }

    @Required
    public void setArrayMult(MatrixArrayMult<T> arrayMult) {
        this.arrayMult = arrayMult;
    }

    @Required
    public void setMatrixTranspose(MatrixTranspose<T> matrixTranspose) {
        this.matrixTranspose = matrixTranspose;
    }

    @Required
    public void setArrayAnd(MatrixArrayConjuctionOperator<T> arrayAnd) {
        this.arrayAnd = arrayAnd;
    }

    @Required
    public void setArrayOr(MatrixArrayDisjuctionOperator<T> arrayOr) {
        this.arrayOr = arrayOr;
    }

    @Required
    public void setNegator(MatrixArrayNegate<T> negator) {
        this.negator = negator;
    }
}
