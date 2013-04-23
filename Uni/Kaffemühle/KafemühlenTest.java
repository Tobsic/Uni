
public class KafemühlenTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Kaffemühle km = new Kaffemühle(250, 100);
			km.füllen(150);
			km.mühlen(50);
			km.entnehmen(10);
			System.out.print(km);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
