package interpreter.types;

public interface NumericObject extends ObjectData {

    NumericType getNumericType();

    void setNumericType(NumericType numericType);
}
