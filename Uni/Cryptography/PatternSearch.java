/**
 * 
 * @author Tobsic
 *
 */
public class PatternSearch {
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
	
	/**
	 * Search a patter in a key using a-z0-9
	 * @param key in which you want to look for the pattern
	 * @param length length of the pattern
	 * @param count minimum count of existig of the pattern in the key
	 */
	public static void findPattern(String key, int length, int count){
		key = key.toLowerCase();
		BruteForce bruteForce = new BruteForce(length, length);
		for(String pattern : bruteForce){
			String[] parts = key.split(pattern);
			if(parts.length > count)
				Console.writeln("Matching pattern \"{0}\": {1}", pattern, join(parts, " " + pattern + " "));
		}
	}
}
