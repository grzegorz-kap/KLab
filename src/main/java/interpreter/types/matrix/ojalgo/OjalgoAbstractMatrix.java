package interpreter.types.matrix.ojalgo;

import interpreter.types.*;
import interpreter.types.foriterator.ForIterator;
import interpreter.types.foriterator.OjalgoForIteratorFactory;
import interpreter.types.matrix.Matrix;
import interpreter.types.scalar.Scalar;
import org.ojalgo.function.BinaryFunction;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.store.PhysicalStore;

import java.util.Iterator;
import java.util.function.Consumer;

public abstract class OjalgoAbstractMatrix<T extends Number> extends AbstractNumericObject implements Matrix<T>, Sizeable {
    private PhysicalStore<T> matrixStore;
    private MatrixStore<T> lazyStore;
    private PhysicalStore.Factory<T, ? extends PhysicalStore<T>> factory;

    public OjalgoAbstractMatrix(NumericType numericType) {
        super(numericType);
    }

    public abstract OjalgoAbstractMatrix<T> create(MatrixStore<T> matrixStore);

    @Override
    public boolean isTrue() {
        for (T value : getMatrixStore()) {
            if (value.doubleValue() == 0.0D) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ObjectData get(AddressIterator cell) {
        if (cell.length() == 1) {
            return createScalar(getLazyStore().get(cell.getNext() - 1));
        }
        PhysicalStore<T> matrix = createMatrixStore(cell.length(), 1);
        int address = 0;
        while (cell.hasNext()) {
            matrix.set(address++, lazyStore.get(cell.getNext() - 1));
        }
        return create(matrix);
    }

    @Override
    public EditSupplier<T> getEditSupplier() {
        // TODO check if correct iteration order
        if(isScalar()) {
            return new EditSupplier<T>() {
                private T value = lazyStore.get(0);
                @Override
                public boolean hasNext() {
                    return true;
                }

                @Override
                public T next() {
                    return value;
                }
            };
        } else {
            return new EditSupplier<T>() {
                private Iterator<T> iterator = lazyStore.iterator();

                @Override
                public boolean hasNext() {
                    return iterator.hasNext();
                }

                @Override
                public T next() {
                    return iterator.next();
                }
            };
        }
    }

    @Override
    public Editable<T> edit(AddressIterator row, AddressIterator column, EditSupplier<T> supplier) {
        boolean changed = false;
        if(row.max() > lazyStore.countRows()) {
            lazyStore = lazyStore.builder().below(factory.makeZero(row.max() - lazyStore.countRows(), lazyStore.countColumns())).copy();
            changed = true;
        }
        if(column.max() > lazyStore.countColumns()) {
            lazyStore = lazyStore.builder().right(factory.makeZero(lazyStore.countRows(), column.max() - lazyStore.countColumns())).build();
            changed = true;
        }

        if(matrixStore != lazyStore) {
            matrixStore = lazyStore.copy();
        }

        while(row.hasNext()) {
            long rowAddress = row.getNext() - 1;
            while(column.hasNext()) {
                if(!supplier.hasNext()) {
                    throw new RuntimeException(); // TODO
                }
                matrixStore.set(rowAddress, column.getNext()-1, supplier.next());
            }
        }

        lazyStore = matrixStore;
        return this;
    }

    @Override
    public ObjectData get(AddressIterator row, AddressIterator column) {
        if (row.length() == 1 && column.length() == 1) {
            return createScalar(getLazyStore().get(row.getNext() - 1, column.getNext() - 1));
        }
        PhysicalStore<T> matrix = createMatrixStore(row.length(), column.length());
        long rowIndex = 0L;
        long colIndex = 0L;
        while (row.hasNext()) {
            long rowAddress = row.getNext() - 1;
            while (column.hasNext()) {
                matrix.set(rowIndex, colIndex++, lazyStore.get(rowAddress, column.getNext() - 1));
            }
            column.reset();
            colIndex = 0;
            rowIndex++;
        }
        return create(matrix);
    }

    protected abstract Scalar createScalar(Number number);

    @Override
    public T get(long m, long n) {
        return getLazyStore().get(m, n);
    }

    @Override
    public T get(int m) {
        return getLazyStore().get(m);
    }

    @Override
    public void set(int m, int n, T value) {
        getMatrixStore().set(m, n, value);
    }

    @Override
    public String toString() {
        return OjalgoMatrixPrinter.toString(getMatrixStore());
    }

    @Override
    public long getRows() {
        return getLazyStore().countRows();
    }

    @Override
    public long getColumns() {
        return getLazyStore().countColumns();
    }

    public T getNumber(int m) {
        return getLazyStore().get(m);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        getMatrixStore().forEach(action);
    }

    public PhysicalStore<T> getMatrixStore() {
        if (matrixStore == null) {
            matrixStore = getLazyStore().copy();
        }
        return matrixStore;
    }

    public MatrixStore<T> getLazyStore() {
        return lazyStore;
    }

    public void setLazyStore(MatrixStore<T> lazyStore) {
        this.lazyStore = lazyStore;
    }

    public void setFactory(PhysicalStore.Factory<T, ? extends PhysicalStore<T>> factory) {
        this.factory = factory;
    }

    private PhysicalStore<T> createMatrixStore(long rows, long columns) {
        return factory.makeZero(rows, columns);
    }

    @Override
    public ForIterator getForIterator() {
        return OjalgoForIteratorFactory.create(this);
    }

    public long length() {
        return lazyStore.count();
    }

    public BinaryFunction<T> getDivideFunction() {
        return lazyStore.factory().function().divide();
    }
}
