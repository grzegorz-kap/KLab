package interpreter.service.functions;

import interpreter.types.ObjectData;

public interface InternalFunction {
    String SIZE_FUNCTION = "size";
    String RAND_FUNCTION = "rand";
    String EYE_FUNCTION = "eye";
    String ONES_FUNCTION = "ones";
    String ZEROS_FUNCTION = "zeros";
    String SQRT_FUNCTION = "sqrt";
    String INV_FUNCTION = "inv";
    String SINUS = "sin";
    String DETERMINANT = "det";
    String TANGENS = "tan";
    String COSINUS = "cos";
    String TIC = "tic";
    String TOC = "toc";

    String getName();

    int getMinArgs();

    int getMaxArgs();

    int getAddress();

    void setAddress(int address);

    ObjectData call(ObjectData[] data, int expectedOutput);

}
