package interpreter.types;

public interface EditSupportable <N extends Number> {
    EditSupplier<N> getEditSupplier();
}
