
public class ComplexNumber {
	private double _real, _img;
	
	public ComplexNumber(double Real, double Imaginary){
		this.set(Real, Imaginary);
	}
	public ComplexNumber(ComplexNumber c){
		new ComplexNumber(c.getReal(), c.getImaginary());
	}
	public ComplexNumber(){
		this(0,0);
	}
	
	public ComplexNumber clone(){
		return new ComplexNumber(this);
	}
	
	public double getImaginary(){
		return _img;
	}
	public ComplexNumber setImaginary(double Imaginary){
		_img = Imaginary;
		return this;
	}
	public double getReal(){
		return _real;
	}
	public ComplexNumber setReal(double Real){
		_real = Real;
		return this;
	}
	public double getAbs(){
		return Math.sqrt(Math.pow(this.getReal(), 2) + Math.pow(this.getImaginary(), 2));
	}

	
	public ComplexNumber set(double Real, double Imaginary){
		return this.setReal(Real).setImaginary(Imaginary);
	}
	public ComplexNumber add(double Real, double Imaginary){
		return this.set(this.getReal() + Real, this.getImaginary() + Imaginary);
	}
	public ComplexNumber add(ComplexNumber c){
		return this.add(c.getReal(),c.getImaginary());
	}
	public ComplexNumber sub(double Real, double Imaginary){
		return this.add(-Real, -Imaginary);
	}
	public ComplexNumber sub(ComplexNumber c){
		return this.sub(c.getReal(), c.getImaginary());
	}
	public ComplexNumber Multiply(ComplexNumber c){
		this.set(this.getReal() * c.getReal() - this.getImaginary() * c.getImaginary(), this.getReal() * c.getImaginary() + this.getImaginary() * c.getReal());
		return this;
	}
	
	public ComplexNumber Sum(ComplexNumber...c){
		ComplexNumber result = new ComplexNumber();
		for(ComplexNumber z : c)
			result.add(z);
		return result;
	}

	@Override
	public String toString() {
		return "Comlex Number: " + this.getReal() + " + " + this.getImaginary() + "i";
	}
}
