
public class CryptoTest {
	public static void main(String[] args) {
		BruteForce bf = new BruteForce(0,2, "abc".toCharArray());
		for(String k : bf)
			Console.writeln(k);
		
		for(int i = 0; i < 1000; i++){
			String key = "";
			while(key.length() < 50)
				key += Random.NrChar();
			//Console.writeln("Key: {0}", key);
			PatternSearch.findPattern(key, 3, 2);
		}
	}
}
