
public class RechteckTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Rechteck r = new Rechteck(30, 40);
		System.out.println(r);
		System.out.println("Fläche:\t" + r.Fläche());
		System.out.println("Umfang:\t" + r.Umfang());
		System.out.println("Diagonale:\t" + r.Diagonale());
	}

}
