package interpreter.types.matrix.ojalgo;

import interpreter.parsing.model.NumericType;
import interpreter.types.AbstractNumericObject;
import interpreter.types.ObjectData;
import interpreter.types.Sizeable;
import interpreter.types.matrix.Matrix;

import java.util.function.Consumer;

import org.ojalgo.matrix.store.PhysicalStore;

public class OjalgoMatrix<T extends Number> extends AbstractNumericObject implements Matrix<T>, Sizeable {

	private PhysicalStore<T> matrixStore;

	public <P extends PhysicalStore<T>> OjalgoMatrix(P matrixStore) {
		super(NumericType.MATRIX_DOUBLE);
		this.matrixStore = matrixStore;
	}

	@Override
	public T get(int m, int n) {
		return matrixStore.get(m, n);
	}
	
	@Override
	public T get(int m) {
		return matrixStore.get(m);
	}

	@Override
	public void set(int m, int n, T value) {
		matrixStore.set(m, n, value);
	}

	@Override
	public long getRowsCount() {
		return matrixStore.countRows();
	}

	@Override
	public long getColumnsCount() {
		return matrixStore.countColumns();
	}

	public PhysicalStore<T> getMatrixStore() {
		return matrixStore;
	}

	@Override
	public String toString() {
		return OjalgoMatrixPrinter.toString(matrixStore);
	}

	@Override
	public ObjectData copyObjectData() {
		return new OjalgoMatrix<T>(matrixStore.copy());
	}

	@Override
	public boolean isTrue() {
		for (T value : matrixStore) {
			if (value.equals(0)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public long getRows() {
		return matrixStore.countRows();
	}

	@Override
	public long getColumns() {
		return matrixStore.countColumns();
	}

	@Override
	public void forEach(Consumer<? super T> action) {
		matrixStore.forEach(action);
	}
}
