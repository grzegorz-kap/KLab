package interpreter.types.scalar;

import interpreter.types.Sizeable;

public interface Scalar extends Sizeable {
	
	Number getValue();
	boolean isMathematicalInteger();

}
