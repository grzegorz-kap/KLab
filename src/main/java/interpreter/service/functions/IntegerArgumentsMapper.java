package interpreter.service.functions;

import interpreter.commons.exception.IllegalArgumentException;
import interpreter.core.math.utils.SizeUtils;
import interpreter.service.functions.exception.WrongNumberOfArgumentsException;
import interpreter.types.ObjectData;
import interpreter.types.Sizeable;
import interpreter.types.matrix.Matrix;
import interpreter.types.scalar.Scalar;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class IntegerArgumentsMapper {

    public List<Integer> mapToIntArgs(ObjectData[] datas, InternalFunction function) {
        List<Integer> arguments = new ArrayList<>();
        Stream.of(datas).forEach(data -> process(function, arguments, data));
        if (arguments.size() < function.getMinArgs()) {
            throw new WrongNumberOfArgumentsException(function);
        }
        return arguments;
    }

    private void process(InternalFunction function, List<Integer> arguments, ObjectData data) {
        if (data instanceof Scalar) {
            processScalar(function, arguments, data);
        } else if (data instanceof Matrix<?>) {
            processMatrix(function, arguments, data);
        }
    }

    private void processMatrix(InternalFunction function, List<Integer> arguments, ObjectData data) {
        Matrix<?> matrix = (Matrix<?>) data;
        checkCorrectArgumentsNumber(arguments.size(), function, matrix);
        matrix.forEach(value -> arguments.add(convertToInteger(value)));
    }

    private void processScalar(InternalFunction function, List<Integer> arguments, ObjectData data) {
        Scalar scalar = (Scalar) data;
        checkCorrectArgumentsNumber(arguments.size(), function, scalar);
        arguments.add(convertToInteger(scalar.getValue()));
    }

    private void checkCorrectArgumentsNumber(int current, InternalFunction function, Sizeable sizeable) {
        if (current + SizeUtils.getLength(sizeable) > function.getMaxArgs()) {
            throw new WrongNumberOfArgumentsException(function);
        }
    }

    private Integer convertToInteger(Number number) {
        Double value = number.doubleValue();
        if (Math.rint(value) != value || value < 0) {
            throw new IllegalArgumentException(IllegalArgumentException.ARGUMENT_MUST_BE_NON_NEGATIVE_INTEGER);
        }
        return number.intValue();
    }
}
