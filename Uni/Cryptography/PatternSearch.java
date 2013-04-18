
public class PatternSearch {
	public static char randomNrChar(){
		int r = (int)(Math.random() * 36) + 48;
		return (char)(r > 57 ? r + 7 : r);
	}
	public static char randomNr(){
		return (char)((Math.random() * 10) + 48);
	}
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
		String key;
		for(int i = 0; i < 50; i++){
			key = "";
			while(key.length() < 50)
				key += randomNrChar();
			Console.writeln("Pattern: {0}", findPattern(key, 2));
		}
	}
	
	public static String findPattern(String key, int length){
		key = key.toUpperCase();
		String ary;
		for(int i = 0; i < 100; i++){
			ary= "";
			while(ary.length() < length)
				ary += randomNrChar();
			String[] parts = key.split(ary);
			if(parts.length > 2)
				return join(parts, " " + ary + " ");
		}
		return "No matchings found";
	}
}
