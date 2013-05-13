public class Det{
	public static void main(String[] args) throws Exception{
		Console.clear();
		int dem = Console.getInteger("Anzahl der Spalten/Zeilen");
		double[][] matrix = new double[dem][dem];
		for(int rowIndex = 0; rowIndex < dem; rowIndex++)
			for(int columnIndex = 0; columnIndex < dem; columnIndex++)
				matrix[rowIndex][columnIndex] = Console.getDouble("Wert f¸r " + (rowIndex + 1) + ". Zeile, " + (columnIndex + 1) + ". Spalte");
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
				for(int copyColumn = 1; copyColumn < dimension; copyColumn++)
					subdeterminant[copyRow][copyColumn - 1] = matrix[copyRow >= selectedRow ? copyRow + 1 : copyRow][copyColumn];
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
		for(int pivotIndex = 0; pivotIndex < matrix.length; pivotIndex++)
			for(int rowIndex = pivotIndex + 1; rowIndex < matrix.length; rowIndex++){
				for(int spanIndex = pivotIndex + 1; spanIndex < matrix.length; spanIndex++)
					if(matrix[pivotIndex][pivotIndex] == 0)
						return 0;
					else
						matrix[rowIndex][spanIndex] -= matrix[pivotIndex][spanIndex] * matrix[rowIndex][pivotIndex] / matrix[pivotIndex][pivotIndex];
				matrix[rowIndex][pivotIndex] = 0;
			}
		double result = 1;
		for(int i = 0; i < matrix.length; i++)
			result *= matrix[i][i];
		return result;
	}
}