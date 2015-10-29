package interpreter.types.matrix.ojalgo;

import java.util.function.Consumer;

import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.store.PhysicalStore;

import interpreter.parsing.model.NumericType;
import interpreter.types.AbstractNumericObject;
import interpreter.types.ObjectData;
import interpreter.types.Sizeable;
import interpreter.types.matrix.Matrix;

public class OjalgoMatrix<T extends Number> extends AbstractNumericObject implements Matrix<T>, Sizeable {

	private PhysicalStore<T> matrixStore;
	private MatrixStore<T> lazyStore;

	public <P extends PhysicalStore<T>> OjalgoMatrix(P matrixStore) {
		super(NumericType.MATRIX_DOUBLE);
		this.setMatrixStore(matrixStore);
		this.setLazyStore(matrixStore);
	}

	public OjalgoMatrix(MatrixStore<T> store) {
		super(NumericType.MATRIX_DOUBLE);
		setLazyStore(store);
	}

	public PhysicalStore<T> getMatrixStore() {
		if (matrixStore == null) {
			matrixStore = getLazyStore().copy();
		}
		return matrixStore;
	}

	@Override
	public T get(int m, int n) {
		return getMatrixStore().get(m, n);
	}

	@Override
	public T get(int m) {
		return getMatrixStore().get(m);
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
	public ObjectData copyObjectData() {
		return new OjalgoMatrix<T>(getMatrixStore().copy());
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
	public long getRows() {
		return getMatrixStore().countRows();
	}

	@Override
	public long getColumns() {
		return getMatrixStore().countColumns();
	}

	@Override
	public void forEach(Consumer<? super T> action) {
		getMatrixStore().forEach(action);
	}

	public void setMatrixStore(PhysicalStore<T> matrixStore) {
		this.matrixStore = matrixStore;
	}

	public MatrixStore<T> getLazyStore() {
		return lazyStore;
	}

	public void setLazyStore(MatrixStore<T> lazyStore) {
		this.lazyStore = lazyStore;
	}
}
