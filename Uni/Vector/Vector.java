

public class Vector{
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
		Values = _values;
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
		for(int i = 0; i < _values.length; i++)
			sqResult += _values[i] * _values[i];
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
	public Vector Add(Vector... Vectors) throws Exception{
		for(Vector v : Vectors)
			if(v.getDimension() != _values.length)
				throw new Exception("Die Dimensionen der Vektoren stimmen nicht überein.");
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
	public Vector Diff(Vector v){
		if(_values.length != this.getDimension())
			System.out.println("Die Dimensionen der Vektoren stimmen nicht überein.");
		else
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
	public float DotProduct(Vector v){
		if(_values.length != this.getDimension())
			System.out.println("Die Dimensionen der Vektoren stimmen nicht überein.");
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
	public Vector CrossProduct(Vector v){
		if(_values.length != 3 || v.getDimension() != 3)
			System.out.println("Beide Vektoren müssen im Dreidimensionalen Raum liegen.");
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
	public Vector getInput(int Index){
		if(Index >= _values.length)
			System.out.println("Der Index zur Eingabe ist auserhalb der Dimenson des Vectors.");
		else{
			try{
				_values[Index] = ConsoleReader.Float("Bitte geben Sie die " + (Index + 1) + ". Komponente an");
			}catch(Exception e){
				System.out.println();
				System.out.println("Fehler beim lesen der Eingabe.");
			}
		}
		return this;
	}

	/**
	 * Gibt dem Benutzer die Möglichkeit die Komponenten über die Konsole einzugeben.
	 * @return  Vektor von dem die Funktion aufgerufen wurde
	 */
	public Vector getInput(){
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
	
	public static Vector CrossProduct(Vector v1, Vector v2){
		return v1.Clone().CrossProduct(v2);
	}
	
	/**
	 * Bildet das Spatprodukt der Vektoren (v1 x v2) * v3
	 * @param v1
	 * @param v2
	 * @param v3
	 * @return
	 */
	public static float TripleProduct(Vector v1, Vector v2, Vector v3){
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