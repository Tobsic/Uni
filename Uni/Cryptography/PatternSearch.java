/**
 * 
 * @author Tobsic
 *
 */
public class PatternSearch {
	/**
	 * Random upper case char or a number
	 * @return random upper case char or number
	 */
	public static char randomNrChar(){
		int r = (int)(Math.random() * 36) + 48;
		return (char)(r > 57 ? r + 7 : r);
	}
	/**
	 * Random number as char
	 * @return random number
	 */
	public static char randomNr(){
		return (char)((Math.random() * 10) + 48);
	}
	/**
	 * Concat the Values of a string array and seperate them whith a given string 
	 * @param r string array to join
	 * @param d seperator
	 * @return joined string of array values
	 */
	public static String join(String r[],String d)
	{
	        if (r.length == 0) return "";
	        StringBuilder sb = new StringBuilder();
	        int i;
	        for(i=0;i<r.length-1;i++)
	            sb.append(r[i]+d);
	        return sb.toString()+r[i];
	}
	public static void main(String[] args) {
		String key = "";
		while(key.length() < 50)
			key += randomNrChar();
		Console.writeln("Key: {0}", key);
		findPattern(key, 2, 3);
	}
	
	/**
	 * Search a patter in a key using a-z0-9
	 * @param key in which you want to look for the pattern
	 * @param length length of the pattern
	 * @param count minimum count of existig of the pattern in the key
	 */
	public static void findPattern(String key, int length, int count){
		key = key.toLowerCase();
		BruteForce bruteForce = new BruteForce(2, length);
		for(String pattern : bruteForce){
			String[] parts = key.split(pattern);
			if(parts.length > count)
				Console.writeln("Matching pattern \"{0}\": {1}", pattern, join(parts, " " + pattern + " "));
		}
	}
}
