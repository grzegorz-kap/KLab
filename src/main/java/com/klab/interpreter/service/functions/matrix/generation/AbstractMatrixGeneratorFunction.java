package com.klab.interpreter.service.functions.matrix.generation;

import com.klab.interpreter.service.functions.AbstractInternalFunction;
import com.klab.interpreter.service.functions.IntegerArgumentsMapper;
import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.matrix.MatrixFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractMatrixGeneratorFunction extends AbstractInternalFunction {
    @Autowired
    protected MatrixFactory<Double> matrixFactory;

    @Autowired
    protected IntegerArgumentsMapper integerArgumentsMapper;

    public AbstractMatrixGeneratorFunction(int argsMin, int argsMax, String name) {
        super(argsMin, argsMax, name);
    }

    protected ObjectData createMatrix(ObjectData[] datas, MatrixCreator creator) {
        List<Integer> arguments = integerArgumentsMapper.mapToIntArgs(datas, this);
        int rows = arguments.get(0);
        int columns = arguments.size() == 2 ? arguments.get(1) : rows;
        return creator.create(rows, columns);
    }
}
