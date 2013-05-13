import java.io.IOException;


/**
 * 
 * @author Tobias Brosge (539713)
 * @author Veit Heller (539501)
 *
 */
public class Vector{
	class DimensionException extends Exception{
		private static final long serialVersionUID = 1L;
		public DimensionException(){ super(); }
		public DimensionException(String Message){ super(Message); }
	}
	
	private double[] _values;
	
	/**
	 * Creats a new zero-vector with in the given dimension (maximum 7).
	 * @param Dimension Dimension of the new vector (maximum 7)
	 * @throws DimensionException Dimension must be between 0 and 7
	 */
	public Vector(int Dimension) throws DimensionException{
		if(Dimension < 0) throw new DimensionException("Die Anzahl der Dimensionen muss Positiv sein.");
		if(Dimension > 7) throw new DimensionException("Die maximale Dimension der Vektoren ist die Siebte.");
		_values = new double[Dimension];
	}
	
	/**
	 * Creates a new vector with the given components.
	 * @param Values Components of the new vector
	 * @throws DimensionException Maximum 7 components are accepted
	 */
	public Vector(double... Values) throws DimensionException{
		if(Values.length > 7) throw new DimensionException("Die maximale Dimension der Vektoren ist die Siebte");
		_values = Values.clone();
	}
	
	/**
	 * Return the dimension of the vector.
	 * @return Dimension of the Vector
	 */
	public int getDimension(){
		return _values.length;
	}
	
	/**
	 * Return the value of a component.
	 * @param Index Index of the component
	 * @return Value of the component
	 */
	public double getValue(int Index){
		return _values[Index];
	}
	
	/**
	 * Return the length of dimension.
	 * @return Length of the Vector
	 */
	public double getLength(){
		double sqResult = 0;
		for(double f : _values)
			sqResult += f * f;
		return (double)Math.sqrt(sqResult);
	}
	
	/**
	 * Set a component to a value.
	 * @param Index Index of the component
	 * @param Value New value of the component
	 * @return This vector
	 */
	public Vector setValue(int Index, double Value){
		_values[Index] = Value;
		return this;
	}
	
	/**
	 * Add vectors (component by component) to this vector.
	 * @param Vectors Vectors to add
	 * @return This vector
	 * @throws
	 */
	public Vector Add(Vector... Vectors) throws DimensionException{
		for(Vector v : Vectors)
			if(v.getDimension() != _values.length)
				throw new DimensionException("Addition - Die Dimensionen der Vektoren stimmen nicht überein.");
		for(Vector v : Vectors)
			for(int i = 0; i < _values.length; i++)
				_values[i] += v.getValue(i) ;
		return this;
	}
	
	/**
	 * Subtract a vector (component by component) from this vector.
	 * @param vector Vector to subtract
	 * @return This vector
	 */
	public Vector Diff(Vector vector) throws DimensionException{
		if(_values.length != this.getDimension())
			throw new DimensionException("Differenz - Die Dimensionen der Vektoren stimmen nicht überein.");
		for(int i = 0; i < _values.length; i++)
			_values[i] -= vector.getValue(i);
		return this;
	}
	
	/**
	 * Multiplies each component with the value.
	 * @param Value Value to multiply with
	 * @return This vector
	 */
	public Vector Multiply(double Value){
		for(int i = 0; i < _values.length; i++)
			_values[i] *= Value;
		return this;
	}
	
	/**
	 * Divides each component by the value.
	 * @param Value Value to dived by
	 * @return This vector
	 */
	public Vector Divide(double Value){
		for(int i = 0; i < _values.length; i++)
			_values[i] /= Value;
		return this;
	}
	
	/**
	 * Creates a clone of this vector
	 * @return A clone of this vector
	 */
	public Vector Clone() throws DimensionException{
		return new Vector(_values);
	}
	
	/**
	 * Calculate the dot product.
	 * @param v Vector to multiply with
	 * @return Cross of this vector and the given vector
	 */
	public double DotProduct(Vector v) throws DimensionException{
		if(_values.length != this.getDimension())
			throw new DimensionException("Skalarprodukt - Die Dimensionen der Vektoren stimmen nicht überein.");
		double result = 0;
		for(int i = 0; i < _values.length; i++)
			result += _values[i] * v.getValue(i);
		return result;
	}
	
	/**
	 * Calculate the cross product.
	 * @param v Vector to multiply with
	 * @return This vector
	 */
	public Vector CrossProduct(Vector v) throws DimensionException{
		if(_values.length != 3 || v.getDimension() != 3)
			throw new DimensionException("Beide Vektoren müssen im Dreidimensionalen Raum liegen.");
		else
		{
			Vector temp = this.Clone();
			_values[0] = temp.getValue(1) * v.getValue(2) - temp.getValue(2) * v.getValue(1);
			_values[1] = temp.getValue(2) * v.getValue(0) - temp.getValue(0) * v.getValue(2);
			_values[2] = temp.getValue(0) * v.getValue(1) - temp.getValue(1) * v.getValue(0);
		}
		return this;
	}
	
	/**
	 * Make this vector to a unit vector.
	 * @return this vector
	 */
	public Vector toUnit(){
		return this.Divide(this.getLength());
	}

	/**
	 * Write this vector to console.
	 * @return This vector
	 */
	public Vector print(){
		Console.writeln(this.toString());
		return this;
	}

	/**
	 * Let the user edit a component by console.
	 * @param Index Index of the component
	 * @return This vector
	 */
	public Vector getInput(int Index) throws DimensionException, IOException{
		if(Index < 0 || Index >= _values.length)
			throw new DimensionException("Eingabe - Der Index zur Eingabe ist auserhalb der Dimenson des Vectors.");
		else
			_values[Index] = Console.getFloat("Bitte geben Sie die " + (Index + 1) + ". Komponente an");
		return this;
	}

	/**
	 * Let the user edit all components by console.
	 * @return This vector
	 * @throws DimensionException 
	 */
	public Vector getInput() throws IOException{
		try{ // can't be a DimensionException
			for(int i = 0; i < _values.length; i++)
				this.getInput(i);
		}catch(DimensionException e){ }
		return this;
	}
	
	/**
	 * Adds all Vectors.
	 * @param v Vectors to be summed
	 * @return Sum of the vectors
	 * @throws Exception No vector given
	 * @throws DimensionException not all vectors have the same dimension
	 */
	public static Vector Sum(Vector... v) throws Exception, DimensionException{
		if(v.length == 0)
			throw new Exception("No Vector given.");
		return new Vector(v[0].getDimension()).Add(v);
	}
	
	/**
	 * Calculate the cross product of two vectors.
	 * @param vector1 First vector of the cross product
	 * @param vector2 Second vector of the cross product
	 * @return The cross product of vector1 and vector 2
	 * @throws DimensionException The two vectors must be in the third dimension
	 */
	public static Vector CrossProduct(Vector vector1, Vector vector2) throws DimensionException{
		return vector1.Clone().CrossProduct(vector2);
	}
	
	/**
	 * Calculate the triple product of three vectors (vector1 x vector2) * vector3.
	 * @param vector1 The first vector
	 * @param vector2 The second vector
	 * @param vector3 The third vector
	 * @return The triple product of vector1, vector2 and vector3
	 * @throws DimensionException The three vectors must be in the third dimension
	 */
	public static double TripleProduct(Vector vector1, Vector vector2, Vector vector3) throws DimensionException{
		return vector1.Clone().CrossProduct(vector2).DotProduct(vector3);
	}
	
	@Override
	public String toString(){
		String result = "Vektor(" + (_values.length > 0 ? _values[0] : "");
		for(int i = 1; i < _values.length; i++)
			result += ", " + _values[i];
		return result + ")";
	}
}