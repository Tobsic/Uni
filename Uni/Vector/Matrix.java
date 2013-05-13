import java.text.MessageFormat;


public class Matrix {
	class DimensionException extends Exception{
		private static final long serialVersionUID = 1L;
		public DimensionException(){ super(); }
		public DimensionException(String Message){ super(Message); }
	}
	

	private int _columnCount, _rowCount;
	private double[][] _values;
	
	public Matrix(double[][] Values) throws DimensionException{
		this.set(Values);
	}
	public Matrix(Matrix Original){
		try{
			this.set(Original._values);
		}catch(DimensionException e){ }
	}
	public Matrix(int RowCount, int ColumnCount) throws DimensionException{
		this.set(new double[RowCount][ColumnCount]);
	}
	public Matrix(int Dimensions) throws DimensionException{
		this(Dimensions, Dimensions);
	}
	
	public Matrix set(double[][] Values) throws DimensionException{
		int rowCount, columnCount;
		if((rowCount = Values.length) <= 0)
			throw new DimensionException("Need minimum 1 row.");
		if((columnCount = Values[0].length) == 0)
			throw new DimensionException("Need minimum 1 column.");
		for(int i = 1; i < rowCount; i++)
			if(Values[i].length != columnCount)
				throw new DimensionException("Not all rows have the same column count.");
		_columnCount = columnCount;
		_rowCount = rowCount;
		_values = Values.clone();
		return this;
	}
	
	public Matrix clone(){
		return new Matrix(this);
	}
	
	public int getRowCount(){
		return _rowCount;
	}
	public int getColumnCount(){
		return _columnCount;
	}

	public double getValue(int Row, int Column){
//		if(Column < 0 || Row < 0 || Column >= _values.length || Row >= _values[0].length)
//			throw new DimensionException("Index out of range.");
		return _values[Row][Column];
	}
	public Matrix setValue(int Row, int Column, double Value){
//		if(Column < 0 || Row < 0 || Column >= _values.length || Row >= _values[0].length)
//		throw new DimensionException("Index out of range.");
		_values[Row][Column] = Value;
		return this;
	}
	
	public Matrix toUnit(){
		for(int columnIndex = 0; columnIndex < _columnCount; columnIndex++)
			for(int rowIndex = 0; rowIndex < _rowCount; rowIndex++)
				this.setValue(columnIndex, rowIndex, columnIndex == rowIndex ? 1 : 0);
		return this;
	}
	
	public Matrix add(Matrix A) throws DimensionException{
		if(_columnCount != A.getColumnCount() || _rowCount != A.getRowCount())
			throw new DimensionException("Addiditon - The dimensions does not match.");
		for(int rowIndex = 0; rowIndex < _rowCount; rowIndex++)
			for(int columnIndex = 0; columnIndex < _columnCount; columnIndex++)
				this.setValue(rowIndex, columnIndex, this.getValue(rowIndex, columnIndex) + A.getValue(rowIndex, columnIndex));
		return this;
	}
	public Matrix multiply(double Value){
		for(int rowIndex = 0; rowIndex < _rowCount; rowIndex++)
			for(int columnIndex = 0; columnIndex < _columnCount; columnIndex++)
				this.setValue(rowIndex, columnIndex, this.getValue(rowIndex, columnIndex) * Value);
		return this;
	}
	public Matrix multiply(Matrix A) throws DimensionException{
		if(this.getRowCount() != A.getColumnCount())
			throw new DimensionException("Multiply - The row count of this matrix and the columns count of the given matrix are not the same.");
		int rowCount = _rowCount,
			columnCount = A.getColumnCount(),
			innerCount = _columnCount;
		Matrix result = new Matrix(rowCount, columnCount);
		for(int rowIndex = 0; rowIndex < rowCount; rowIndex++)
			for(int columnIndex = 0; columnIndex < columnCount; columnIndex++)
				for(int innerIndex = 0; innerIndex < innerCount; innerIndex++)
					result.setValue(rowIndex, columnIndex, this.getValue(rowIndex, columnIndex) + this.getValue(rowIndex, innerIndex) * A.getValue(innerIndex, columnIndex));
		return result;
	}
	public Matrix getInverted() throws DimensionException{
		if(_rowCount != _columnCount)
			throw new DimensionException("Invert - No square matrix given.");
		Matrix result = new Matrix(_columnCount).toUnit();
		Matrix tmp = this.clone();
		
		for(int pivotIndex = 0; pivotIndex < _rowCount; pivotIndex++){
			double pivot = tmp.getValue(pivotIndex, pivotIndex);
			if(pivot == 0)
				return null;
			for(int rowIndex = pivotIndex + 1; rowIndex < _rowCount; rowIndex++){
				double factor = tmp.getValue(rowIndex, pivotIndex) / pivot;
				for(int columnIndex = 0; columnIndex < _rowCount; columnIndex++){
					if(columnIndex > pivotIndex)
						tmp.setValue(rowIndex, columnIndex, tmp.getValue(rowIndex, columnIndex) - tmp.getValue(pivotIndex, columnIndex) * factor);
					result.setValue(rowIndex, columnIndex, result.getValue(rowIndex, columnIndex) - result.getValue(pivotIndex, columnIndex) * factor);
				}
				tmp.setValue(rowIndex, pivotIndex, 0);
			}
		}

		for(int pivotIndex = 0; pivotIndex < _rowCount - 1; pivotIndex++){
			double pivot = tmp.getValue(pivotIndex, pivotIndex);
			tmp.setValue(pivotIndex, pivotIndex, 1);
			for(int columnIndex = 0; columnIndex < _columnCount; columnIndex++){
				if(columnIndex > pivotIndex)
					tmp.setValue(pivotIndex, columnIndex, tmp.getValue(pivotIndex, columnIndex) / pivot);
				result.setValue(pivotIndex, columnIndex, result.getValue(pivotIndex, columnIndex) / pivot);
			}
		}
		
		for(int pivotIndex = 0; pivotIndex < _rowCount - 1; pivotIndex++)
			for(int rowIndex = 0; rowIndex <= pivotIndex; rowIndex++){
				double factor = tmp.getValue(rowIndex, pivotIndex + 1);
				for(int columnIndex = 0; columnIndex < _columnCount; columnIndex++){
					if(columnIndex > pivotIndex)
						tmp.setValue(rowIndex, columnIndex, result.getValue(rowIndex, columnIndex) - tmp.getValue(pivotIndex, columnIndex) * factor);
					result.setValue(rowIndex, columnIndex, result.getValue(rowIndex, columnIndex) - result.getValue(pivotIndex + 1, columnIndex) * factor);
				}
			}
				
		return result;
	}
	public Matrix getTransosed(){
		Matrix result = null;
		try{
			result = new Matrix(_columnCount, _rowCount);
			for(int rowIndex = 0; rowIndex < _rowCount;  rowIndex++)
				for(int columnIndex = 0; columnIndex < _columnCount; columnIndex++)
					result.setValue(columnIndex, rowIndex, this.getValue(rowIndex, columnIndex));
		}catch(DimensionException e){ }
		return result;
	}
	public double getDetermine() throws DimensionException{
		if(_rowCount != _columnCount)
			throw new DimensionException("Determine - No square matrix given.");
		Matrix tmp = this.clone();
		for(int pivotIndex = 0; pivotIndex < _rowCount; pivotIndex++){
			if(tmp.getValue(pivotIndex, pivotIndex) == 0)
				return 0;
			for(int rowIndex = pivotIndex + 1; rowIndex < _rowCount; rowIndex++){
				double factor = tmp.getValue(rowIndex, pivotIndex) / tmp.getValue(pivotIndex, pivotIndex);
				for(int columnIndex = pivotIndex + 1; columnIndex < _rowCount; columnIndex++)
					tmp.setValue(rowIndex, columnIndex, tmp.getValue(rowIndex, columnIndex) - tmp.getValue(pivotIndex, columnIndex) * factor);
				tmp.setValue(rowIndex, pivotIndex, 0);
			}
		}
		double result = 1;
		for(int pivotIndex = 0; pivotIndex < _rowCount; pivotIndex++)
			result *= tmp.getValue(pivotIndex, pivotIndex);
		return result;
	}

	@Override
	public String toString() {
		String values = "( " + Console.join(" | ", _values[0]) + " ) ";
		for(int row = 1; row < _rowCount; row++)
			values += " (" + Console.join(" | ", _values[row]) + ")";
		return MessageFormat.format("Matrix {0}x{1} ({2})", _rowCount, _columnCount, values);
	}
}
