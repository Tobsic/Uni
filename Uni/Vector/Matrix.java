
public class Matrix {
	class DimensionException extends Exception{
		private static final long serialVersionUID = 1L;
		public DimensionException(){ super(); }
		public DimensionException(String Message){ super(Message); }
	}
	
	private double[][] _values;
	
	public Matrix(double[][] Values) throws DimensionException{
		if(Values.length <= 0 || Values[0].length <= 0)
			throw new DimensionException("The dimensions must be greater than 0.");
		_values = Values.clone();
	}
	public Matrix(Matrix Original){
		this._values = Original._values;
	}
	public Matrix(int SpanCount, int RowCount) throws DimensionException{
		if(SpanCount <= 0 || RowCount <= 0)
			throw new DimensionException("The dimensions must be greater than 0.");
		_values = new double[SpanCount][RowCount];
	}
	public Matrix(int Dimensions) throws DimensionException{
		this(Dimensions, Dimensions);
	}
	
	public Matrix clone(){
		return new Matrix(this);
	}
	
	public int getSpanCount(){
		return _values.length;
	}
	public int getRowCount(){
		return _values[0].length;
	}

	public double getValue(int Span, int Row) throws DimensionException{
//		if(Span < 0 || Row < 0 || Span >= _values.length || Row >= _values[0].length)
//			throw new DimensionException("Index out of range.");
		return _values[Span][Row];
	}
	public Matrix setValue(int Span, int Row, double Value){
//		if(Span < 0 || Row < 0 || Span >= _values.length || Row >= _values[0].length)
//		throw new DimensionException("Index out of range.");
		_values[Span][Row] = Value;
		return this;
	}
	
	public Matrix add(Matrix A) throws DimensionException{
		if(this.getSpanCount() != A.getSpanCount() || this.getRowCount() != A.getRowCount())
			throw new DimensionException("Addiditon - The dimensions does not match.");
		for(int span = 0; span < _values.length; span++)
			for(int row = 0; row < _values[0].length; row++)
				this.setValue(span, row, this.getValue(span, row) + A.getValue(span, row));
		return this;
	}
	public Matrix multiply(Matrix A) throws DimensionException{
		if(this.getRowCount() != A.getSpanCount())
			throw new DimensionException("Multiply - The row count of this matrix and the spans count of the given matrix are not the same.");
		int spanCount = this.getSpanCount(),
			rowCount = A.getRowCount(),
			innerCount = this.getRowCount();
		Matrix result = new Matrix(spanCount, rowCount);
		for(int spanIndex = 0; spanIndex < result.getSpanCount(); spanIndex++)
			for(int rowIndex = 0; rowIndex < result.getSpanCount(); rowIndex++)
				for(int innerIndex = 0; innerIndex < innerCount; innerIndex++)
					result.setValue(spanIndex, rowIndex, this.getValue(spanIndex, rowIndex) + this.getValue(spanIndex, innerIndex) * A.getValue(innerIndex, rowIndex));
		return result;
	}
	public Matrix invert() throws DimensionException{
		if(this.getRowCount() != this.getSpanCount())
			throw new DimensionException("Invert - No suare matrix given.");
		Matrix result = new Matrix(this.getSpanCount());
		
		return result;
	}
}
