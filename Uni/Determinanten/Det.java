public class Det{
	public static void main(String[] args) throws Exception{
		Console.clear();
		int dem = Console.getInteger("Anzahl der Spalten/Zeilen");
		double[][] matrix = new double[dem][dem];
		for(int row = 0; row < dem; row++)
			for(int span = 0; span < dem; span++)
				matrix[row][span] = Console.getDouble("Wert f¸r " + (row + 1) + ". Zeile, " + (span + 1) + ". Spalte");
		Console.writeln();
		Console.writeln("Die Determinante ist laut Rekursion: {0}", detRecursiv(matrix));
		Console.writeln("Die Determinante ist laut Gauﬂ:      {0}", detByGauﬂ(matrix));
	}
	
	public static double detRecursiv(double[][] matrix) throws Exception{
		int dimension = matrix.length;
		if(dimension < 2)
			throw new Exception("Die Matrix muss mindestens 2x2 sein.");
		for(double[] r : matrix)
			if(r.length != dimension)
				throw new Exception("Die Matrix muss Quadratisch sein.");
		return _detRecursiv(matrix);
	}
	private static double _detRecursiv(double[][] matrix){
		int dimension = matrix.length;
		if(dimension == 2)
			return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
		double[][] subdeterminant = new double[dimension - 1][dimension - 1];
		double result = 1;
		for(int selectedRow = 0; selectedRow < dimension; selectedRow++){
			for(int copyRow = 0; copyRow < dimension - 1; copyRow++)
				for(int copySpan = 1; copySpan < dimension; copySpan++)
					subdeterminant[copyRow][copySpan - 1] = matrix[copyRow >= selectedRow ? copyRow + 1 : copyRow][copySpan];
			result *= (selectedRow % 2 * -2 + 1) * matrix[selectedRow][0] *  _detRecursiv(subdeterminant);
		}
		return result;
	}
	public static double detByGauﬂ(double[][] matrix) throws Exception{
		int dimension = matrix.length;
		if(dimension < 2)
			throw new Exception("Die Matrix muss mindestens 2x2 sein.");
		for(double[] r : matrix)
			if(r.length != dimension)
				throw new Exception("Die Matrix muss Quadratisch sein.");
		return _detByGauﬂ(matrix);
	}
	public static double _detByGauﬂ(double[][] matrix){
		for(int i = 0; i < matrix.length; i++)
			for(int j = i + 1; j < matrix.length; j++){
				for(int k = i + 1; k < matrix.length; k++)
					if(matrix[i][i] == 0)
						return 0;
					else
						matrix[j][k] -= matrix[i][k] * matrix[j][i] / matrix[i][i];
				matrix[j][i] = 0;
			}
		double result = 1;
		for(int i = 0; i < matrix.length; i++)
			result *= matrix[i][i];
		return result;
	}
}