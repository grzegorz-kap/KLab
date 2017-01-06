package com.klab.interpreter.types.matrix.ojalgo;

import com.klab.interpreter.functions.math.LUResult;
import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.matrix.Matrix;
import org.ojalgo.matrix.decomposition.LU;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.store.PhysicalStore;

import java.util.Optional;

class OjalgoLUResult<T extends Number> implements LUResult {
    private final LU<T> lu;
    private OjalgoAbstractMatrix<T> ojalgoAbstractMatrix;
    private Matrix p;
    private Matrix y;

    OjalgoLUResult(OjalgoAbstractMatrix<T> ojalgoAbstractMatrix, LU<T> lu) {
        this.ojalgoAbstractMatrix = ojalgoAbstractMatrix;
        this.lu = lu;
    }

    @Override
    public ObjectData getY() {
        return Optional.ofNullable(y).orElseGet(() -> {
            PhysicalStore<T> y = lu.getU().copy();
            MatrixStore<T> l = lu.getL().copy();

            int m = (int) l.countRows();
            int n = (int) l.countColumns();

            for (int j = 0; j < n; j++) {
                for (int i = j + 1; i < m; i++) {
                    y.set(i, j, l.get(i, j));
                }
            }
            return this.y = ojalgoAbstractMatrix.create(y);
        });
    }

    public ObjectData getL() {
        return ojalgoAbstractMatrix.create(lu.getL());
    }

    public ObjectData getU() {
        return ojalgoAbstractMatrix.create(lu.getU());
    }

    public ObjectData getP() {
        return Optional.ofNullable(p).orElseGet(() -> {
            int[] pivot = lu.getPivotOrder();
            PhysicalStore<T> physicalStore = ojalgoAbstractMatrix.getFactory().makeZero(pivot.length, pivot.length);
            for (int idx = 0; idx < pivot.length; idx++) {
                physicalStore.set(idx, pivot[idx], 1.0);
            }
            return p = ojalgoAbstractMatrix.create(physicalStore);
        });
    }
}
