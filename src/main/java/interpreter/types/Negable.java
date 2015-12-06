package interpreter.types;

import org.ojalgo.function.UnaryFunction;

public interface Negable<T extends ObjectData> extends ObjectData {
	Negable<T> negate();
	
	interface UnaryNagate<N extends Number> extends UnaryFunction<N> {
		@Override
		default double invoke(double arg) {
			return arg == 0.0D ? 1.0D : 0.0D;
		}
	}
}
