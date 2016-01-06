package interpreter.types;

public interface NumericObject extends ObjectData, Sizeable{

    NumericType getNumericType();

    void setNumericType(NumericType numericType);
}
