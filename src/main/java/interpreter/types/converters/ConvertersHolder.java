package interpreter.types.converters;

import interpreter.types.NumericType;

public interface ConvertersHolder {

    Converter getConverter(NumericType sourceType, NumericType destType);
}
