
public class Rechteck {
	double _side1, _side2;
	
	public Rechteck(double Side1, double Side2){
		_side1 = Side1;
		_side2 = Side2;
	}
	
	public double Diagonale(){
		return Math.sqrt(_side1 * _side1 + _side2 * _side2 );
	}
	
	public double Umfang(){
		return 2 * (_side1 + _side2);
	}
	
	public double Fläche(){
		return _side1 * _side2;
	}

	@Override
	public String toString() {
		return "Seite 1: " + _side1 + " - Seite 2: " + _side2;
	}
}
