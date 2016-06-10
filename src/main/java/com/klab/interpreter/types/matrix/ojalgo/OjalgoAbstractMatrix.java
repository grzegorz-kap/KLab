package com.klab.interpreter.types.matrix.ojalgo;

import com.klab.interpreter.service.LUResult;
import com.klab.interpreter.types.*;
import com.klab.interpreter.types.foriterator.ForIterator;
import com.klab.interpreter.types.foriterator.OjalgoForIteratorFactory;
import com.klab.interpreter.types.matrix.Matrix;
import com.klab.interpreter.types.scalar.Scalar;
import org.ojalgo.function.BinaryFunction;
import org.ojalgo.matrix.decomposition.LU;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.store.PhysicalStore;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class OjalgoAbstractMatrix<T extends Number> extends AbstractNumericObject implements Matrix<T>, Sizeable {
    private PhysicalStore<T> matrixStore;
    private MatrixStore<T> lazyStore;
    private Function<MatrixStore<T>, OjalgoAbstractMatrix<T>> creator;
    private PhysicalStore.Factory<T, ? extends PhysicalStore<T>> factory;
    private boolean temp = true;

    public OjalgoAbstractMatrix(NumericType numericType, Function<MatrixStore<T>, OjalgoAbstractMatrix<T>> creator) {
        super(numericType);
        this.creator = creator;
    }

    public PhysicalStore.Factory<T, ? extends PhysicalStore<T>> getFactory() {
        return factory;
    }

    @Override
    public boolean isTemp() {
        return temp;
    }

    @Override
    public void setTemp(boolean temp) {
        this.temp = temp;
    }

    @Override
    public ObjectData copy() {
        return creator.apply(getLazyStore());
    }

    @Override
    public T getValue() {
        if (isScalar()) {
            return getLazyStore().get(0);
        } else {
            throw new RuntimeException("Not scalar value!");
        }
    }

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
        return creator.apply(matrix);
    }

    @Override
    public EditSupplier<T> getEditSupplier() {
        // TODO check if correct iteration order
        if (isScalar()) {
            return new EditSupplier<T>() {
                private T value = lazyStore.get(0);

                public boolean hasNext() {
                    return true;
                }

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
    public Editable<T> edit(AddressIterator cells, EditSupplier<T> supplier) {
        if (matrixStore != lazyStore) {
            matrixStore = lazyStore.copy();
        }

        while (cells.hasNext()) {
            matrixStore.set(cells.getNext() - 1, supplier.next());
        }
        lazyStore = matrixStore;

        return this;
    }

    @Override
    public Editable<T> edit(AddressIterator row, AddressIterator column, EditSupplier<T> supplier) {
        if (row.max() > lazyStore.countRows()) {
            lazyStore = lazyStore.builder().below(factory.makeZero(row.max() - lazyStore.countRows(), lazyStore.countColumns())).copy();
        }
        if (column.max() > lazyStore.countColumns()) {
            lazyStore = lazyStore.builder().right(factory.makeZero(lazyStore.countRows(), column.max() - lazyStore.countColumns())).build();
        }

        if (matrixStore != lazyStore) {
            matrixStore = lazyStore.copy();
        }

        while (row.hasNext()) {
            long rowAddress = row.getNext() - 1;
            column.reset();
            while (column.hasNext()) {
                if (!supplier.hasNext()) {
                    throw new RuntimeException(); // TODO
                }
                matrixStore.set(rowAddress, column.getNext() - 1, supplier.next());
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
        return creator.apply(matrix);
    }

    @Override
    public LUResult lu() {
        LU<T> lu = LU.make(getLazyStore());
        lu.decompose(getLazyStore());
        return new OjalgoLUResult<>(this, lu);
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
    public long getCells() {
        return lazyStore.count();
    }

    @Override
    public int getRows() {
        return (int) getLazyStore().countRows();
    }

    @Override
    public int getColumns() {
        return (int) getLazyStore().countColumns();
    }

    public T getNumber(int m) {
        return getLazyStore().get(m);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        getMatrixStore().forEach(action);
    }

    public PhysicalStore<T> getMatrixStore() {
        if (matrixStore != lazyStore) {
            matrixStore = lazyStore.copy();
            lazyStore = matrixStore;
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

    public OjalgoAbstractMatrix<T> create(MatrixStore<T> ns) {
        return creator.apply(ns);
    }
}
