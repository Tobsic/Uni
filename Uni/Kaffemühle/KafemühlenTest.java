
public class Kafem�hlenTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Kaffem�hle km = new Kaffem�hle(250, 100);
			km.f�llen(150);
			km.m�hlen(50);
			km.entnehmen(10);
			System.out.print(km);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
