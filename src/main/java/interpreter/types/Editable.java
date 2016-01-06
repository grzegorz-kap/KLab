package interpreter.types;

public interface Editable<N extends Number> extends ObjectData {
    Editable<N> edit(AddressIterator cells, EditSupplier<N> supplier);

    Editable<N> edit(AddressIterator row, AddressIterator column, EditSupplier<N> supplier);
}
