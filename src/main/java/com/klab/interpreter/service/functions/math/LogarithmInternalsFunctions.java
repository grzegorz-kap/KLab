package com.klab.interpreter.service.functions.math;


import com.klab.interpreter.types.NumericObject;
import com.klab.interpreter.types.NumericType;
import com.klab.interpreter.types.ObjectData;
import org.springframework.stereotype.Component;

public class LogarithmInternalsFunctions {

    @Component
    public static class Log10 extends AbstractMathFunction {
        public Log10() {
            super(1, 1, "log10");
        }

        @Override
        protected ObjectData process(NumericObject[] data) {
            return single(data, mathFunctions -> mathFunctions::log10);
        }
    }

    @Component
    public static class Log extends AbstractMathFunction {
        public Log() {
            super(1, 2, "log");
        }

        @Override
        protected ObjectData process(NumericObject[] data) {
            if (data.length == 1) {
                return single(data, mathFunctions -> mathFunctions::log);
            } else {
                NumericObject a = data[0];
                NumericObject b = data[1];
                NumericType promote = promote(a, b);
                return functionsHolder.get(promote, this).log(convert(a, promote), convert(b, promote));
            }
        }
    }
}
