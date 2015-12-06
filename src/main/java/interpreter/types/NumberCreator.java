package interpreter.types;

public interface NumberCreator<T extends Number> {
	T create(double d);
}
