import java.io.IOException;



public class Vector{
	class DimensionException extends Exception{
		private static final long serialVersionUID = 1L;
		public DimensionException(){ super(); }
		public DimensionException(String Message){ super(Message); }
	}
	
	private float[] _values;
	
	/**
	 * 
	 * @param Dimension
	 */
	public Vector(int Dimension){
		_values = new float[Dimension];
	}
	
	/**
	 * 
	 * @param Values
	 */
	public Vector(float... Values){
		_values = Values;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getDimension(){ return _values.length; }
	
	/**
	 * Gibt den Wert einer Komponente zurück
	 * @param Index Index der Komponente
	 * @return Wert der Komponente
	 */
	public float getValue(int Index){
		return _values[Index];
	}
	
	/**
	 * 
	 * @return
	 */
	public float getLength(){
		float sqResult = 0;
		for(float f : _values)
			sqResult += f * f;
		return (float)Math.sqrt(sqResult);
	}
	
	/**
	 * 
	 * @param Index
	 * @param Value
	 * @return Vektor von dem die Funktion aufgerufen wurde
	 */
	public Vector setValue(int Index, float Value){
		_values[Index] = Value;
		return this;
	}
	
	/**
	 * 
	 * @param Vectors
	 * @return Vektor von dem die Funktion aufgerufen wurde
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
	 * 
	 * @param v
	 * @return Vektor von dem die Funktion aufgerufen wurde
	 */
	public Vector Diff(Vector v) throws DimensionException{
		if(_values.length != this.getDimension())
			throw new DimensionException("Differenz - Die Dimensionen der Vektoren stimmen nicht überein.");
		for(int i = 0; i < _values.length; i++)
			_values[i] -= v.getValue(i);
		return this;
	}
	
	/**
	 * 
	 * @param Value
	 * @return Vektor von dem die Funktion aufgerufen wurde
	 */
	public Vector Multiply(float Value){
		for(int i = 0; i < _values.length; i++)
			_values[i] *= Value;
		return this;
	}
	
	/**
	 * 
	 * @param Value
	 * @return Vektor von dem die Funktion aufgerufen wurde
	 */
	public Vector Divide(float Value){
		for(int i = 0; i < _values.length; i++)
			_values[i] /= Value;
		return this;
	}
	
	/**
	 * Erstellt einen Klon des Vektors
	 * @return Eine Klon des Vektors
	 */
	public Vector Clone(){
		return new Vector(_values);
	}
	
	/**
	 * Errechnet das Skalarprodukt
	 * @param v Vector der zur berechnung genutz werden soll
	 * @return
	 */
	public float DotProduct(Vector v) throws DimensionException{
		if(_values.length != this.getDimension())
			throw new DimensionException("Skalarprodukt - Die Dimensionen der Vektoren stimmen nicht überein.");
		float result = 0;
		for(int i = 0; i < _values.length; i++)
			result += _values[i] * v.getValue(i);
		return result;
	}
	
	/**
	 * 
	 * @param v
	 * @return Vektor von dem die Funktion aufgerufen wurde
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
	 * Wandelt den Vektor in einen Einheitsvektor um
	 * @return Vektor von dem die Funktion aufgerufen wurde
	 */
	public Vector UnitVector(){
		return this.Divide(this.getLength());
	}

	/**
	 * Gibt den Vektor als Text in der Konsole aus.
	 * @return  Vektor von dem die Funktion aufgerufen wurde
	 */
	public Vector print(){
		System.out.println(this);
		return this;
	}

	/**
	 * Gibt dem Benutzer die Möglichkeit eine Komponenten über die Konsole einzugeben.
	 * @param Index Index des festzulegenden Wertes
	 * @return  Vektor von dem die Funktion aufgerufen wurde
	 */
	public Vector getInput(int Index) throws DimensionException{
		if(Index < 0 || Index >= _values.length)
			throw new DimensionException("Eingabe - Der Index zur Eingabe ist auserhalb der Dimenson des Vectors.");
		else{
			try{
				_values[Index] = Console.Float("Bitte geben Sie die " + (Index + 1) + ". Komponente an");
			}catch(IOException e){
				System.out.println();
				System.out.println("Fehler beim lesen der Eingabe.");
			}
		}
		return this;
	}

	/**
	 * Gibt dem Benutzer die Möglichkeit die Komponenten über die Konsole einzugeben.
	 * @return  Vektor von dem die Funktion aufgerufen wurde
	 * @throws DimensionException 
	 */
	public Vector getInput() throws DimensionException{
		for(int i = 0; i < _values.length; i++)
			this.getInput(i);
		return this;
	}
	
	/**
	 * Funktion zum Summieren von Vektoren.
	 * @param v Aufzusummierende Vektoren
	 * @return Ein Vektor, dessen Komponenten die Summen der Positionsgleichen Komponenten der als Parameter übergebenden Vektoren sind
	 * @throws Exception
	 */
	public static Vector Sum(Vector... v) throws Exception{
		if(v.length == 0) throw new Exception("No Vector given.");
		return new Vector(v[0].getDimension()).Add(v);
	}
	
	public static Vector CrossProduct(Vector v1, Vector v2) throws DimensionException{
		return v1.Clone().CrossProduct(v2);
	}
	
	/**
	 * Bildet das Spatprodukt der Vektoren (v1 x v2) * v3
	 * @param v1
	 * @param v2
	 * @param v3
	 * @return
	 * @throws DimensionException 
	 */
	public static float TripleProduct(Vector v1, Vector v2, Vector v3) throws DimensionException{
		return v1.Clone().CrossProduct(v2).DotProduct(v3);
	}
	
	@Override
	public String toString(){
		String result = "Vector(" + (_values.length > 0 ? _values[0] : "");
		for(int i = 1; i < _values.length; i++)
			result += ", " + _values[i];
		return result + ")";
	}
}