
public class Kaffem�hle {
	private double _bohnenmenge, _pulvermenge, _maxBohnenmenge, _maxPulvermenge;
	
	public Kaffem�hle(double MaxBohnenmenge, double MaxPulvermenge){
		_maxBohnenmenge = MaxBohnenmenge;
		_maxPulvermenge = MaxPulvermenge;
		_pulvermenge = _bohnenmenge = 0;
	}

	public double getMaxF�llmenge() {
		return _maxBohnenmenge;
	}

	public double getMaxPulvermenge() {
		return _maxPulvermenge;
	}
	
	public Kaffem�hle f�llen(double Menge) throws Exception{
		if(_bohnenmenge + Menge > _maxBohnenmenge)
			throw new Exception("Das Kaffebohnen-Fach ist zu klein.");
		_bohnenmenge += Menge;
		return this;
	}
	
	public Kaffem�hle m�hlen(double Menge) throws Exception{
		if(_bohnenmenge < Menge)
			throw new Exception("Es soll mehr Kaffe gemahlen werden als Bohnen im Kaffebohnen-Fach sind.");
		if( _pulvermenge + Menge > _maxPulvermenge)
			throw new Exception("Es soll mehr Pulver gemahlen als ins Pulverfach passt.");
		_bohnenmenge -= Menge;
		_pulvermenge += Menge;
		return this;
	}
	
	public Kaffem�hle entnehmen(double Menge) throws Exception{
		if(_bohnenmenge - Menge < 0)
			throw new Exception("Es ist weniger Kaffepulver vorhanden als entnommen werden soll.");
		_pulvermenge -= Menge;
		return this;
	}

	@Override
	public String toString() {
		return "Bohnenmenge: " + _bohnenmenge + " | Pulvermenge: " + _pulvermenge;
	}

	
}
