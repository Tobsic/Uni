
public class Random {
	/**
	 * Random upper case char or a number
	 * @return random upper case char or number
	 */
	public static char NrChar(){
		int r = (int)(Math.random() * 36) + 48;
		return (char)(r > 57 ? r + 7 : r);
	}
	/**
	 * Random number as char
	 * @return random number
	 */
	public static char Nr(){
		return (char)((Math.random() * 10) + 48);
	}
}
