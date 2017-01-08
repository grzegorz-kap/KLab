package com.klab.interpreter.types.matrix.ojalgo;

import com.klab.interpreter.functions.math.LUResult;
import com.klab.interpreter.types.*;
import com.klab.interpreter.types.foriterator.AbstractForIterator;
import com.klab.interpreter.types.foriterator.ForIterator;
import com.klab.interpreter.types.scalar.DoubleScalar;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.store.PhysicalStore;
import org.ojalgo.matrix.store.PrimitiveDenseStore;

public class OjalgoRangeMatrix extends OjalgoDoubleMatrix {
    private final double j;
    private final double i;
    private final double k;
    private final int m;
    private PhysicalStore.Factory<Double, PrimitiveDenseStore> factory = PrimitiveDenseStore.FACTORY;
    private OjalgoDoubleMatrix doubleMatrix;

    public OjalgoRangeMatrix(Number start, Number step, Number stop) {
        j = start.doubleValue();
        i = step.doubleValue();
        k = stop.doubleValue();
        double temp = (k - j) / i;
        int tempM = (int) (temp >= 0.0 ? Math.floor(temp) : Math.ceil(temp));
        if (i == 0 || tempM == 0 || i > 0 && j > k || i < 0 && j < k) {
            tempM = 0;
        } else {
            tempM++;
        }
        this.m = tempM;
    }

    private double computeElement(int index) {
        if (index >= m || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return j + i * index;
    }

    private void init() {
        PhysicalStore<Double> store = factory.makeZero(1, m);
        for (long mult = 0; mult < m; mult++) {
            store.set(mult, j + i * mult);
        }
        doubleMatrix = new OjalgoDoubleMatrix(store);
    }

    @Override
    public ObjectData copy() {
        return new OjalgoRangeMatrix(j, i, k);
    }

    @Override
    public Double getValue() {
        if (m != 0) {
            throw new RuntimeException("Not scalar value!");
        } else {
            return j;
        }
    }

    @Override
    public boolean isTrue() {
        if (doubleMatrix != null) {
            return doubleMatrix.isTrue();
        } else {
            for (int i = 0; i < m; i++) {
                if (computeElement(i) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isVector() {
        return true;
    }

    @Override
    public ObjectData get(AddressIterator cell) {
        if (doubleMatrix != null) {
            return doubleMatrix.get(cell);
        }
        if (cell.length() == 1) {
            return createScalar(computeElement(cell.getNext() - 1));
        }
        PhysicalStore<Double> matrix = factory.makeZero(cell.length(), 1);
        int address = 0;
        while (cell.hasNext()) {
            matrix.set(address++, computeElement(cell.getNext() - 1));
        }
        return new OjalgoDoubleMatrix(matrix);
    }

    @Override
    public ObjectData get(AddressIterator row, AddressIterator column) {
        initIfNull();
        return doubleMatrix.get(row, column);
    }

    @Override
    public LUResult lu() {
        initIfNull();
        return doubleMatrix.lu();
    }

    @Override
    public EditSupplier<Double> getEditSupplier() {
        if (doubleMatrix != null) {
            doubleMatrix.getEditSupplier();
        }

        if (m == 1) {
            return new EditSupplier<Double>() {
                public boolean hasNext() {
                    return true;
                }

                public Double next() {
                    return j;
                }
            };
        } else {
            return new EditSupplier<Double>() {
                int index = 0;

                @Override
                public boolean hasNext() {
                    return index < m;
                }

                @Override
                public Double next() {
                    return computeElement(index++);
                }
            };
        }
    }

    @Override
    public PhysicalStore<Double> getMatrixStore() {
        initIfNull();
        return doubleMatrix.getMatrixStore();
    }

    @Override
    public MatrixStore<Double> getLazyStore() {
        initIfNull();
        return doubleMatrix.getLazyStore();
    }

    @Override
    public void setLazyStore(MatrixStore<Double> lazyStore) {
        initIfNull();
        doubleMatrix.setLazyStore(lazyStore);
    }

    @Override
    public ForIterator getForIterator() {
        if (doubleMatrix != null) {
            return doubleMatrix.getForIterator();
        }

        return new AbstractForIterator() {
            int index = 0;

            @Override
            public int getRows() {
                return 1;
            }

            @Override
            public int getColumns() {
                return m;
            }

            @Override
            public long getCells() {
                return m;
            }

            @Override
            public boolean hasNext() {
                return index < m;
            }

            @Override
            public ObjectData getNext() {
                return new DoubleScalar(computeElement(index++));
            }
        };
    }

    @Override
    public long length() {
        return m;
    }

    @Override
    public Double get(int m) {
        return computeElement(m);
    }

    @Override
    public Double get(long m, long n) {
        initIfNull();
        return doubleMatrix.get(m, n);
    }

    @Override
    public void set(int m, int n, Double value) {
        initIfNull();
        doubleMatrix.set(m, n, value);
    }

    @Override
    public long getCells() {
        return m;
    }

    @Override
    public int getRows() {
        return 1;
    }

    @Override
    public int getColumns() {
        return m;
    }

    @Override
    public Double getNumber(int m) {
        return computeElement(m);
    }

    @Override
    public Editable<Double> edit(AddressIterator cells, EditSupplier<Double> supplier) {
        initIfNull();
        return doubleMatrix.edit(cells, supplier);
    }

    @Override
    public Editable<Double> edit(AddressIterator row, AddressIterator column, EditSupplier<Double> supplier) {
        initIfNull();
        return doubleMatrix.edit(row, column, supplier);
    }

    private void initIfNull() {
        if (doubleMatrix == null) {
            init();
        }
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("< 1 x ").append(m).append(" >\n");

        if (m <= 50) {
            for (int i = 0; i < m; i++) {
                b.append(computeElement(i)).append('\t');
            }
        }

        return b.toString();
    }

    @Override
    public Negable<Double> negate() {
        initIfNull();
        return doubleMatrix.negate();
    }
}

