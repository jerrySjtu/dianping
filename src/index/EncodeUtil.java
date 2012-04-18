package index;

public class EncodeUtil {
	
	public static void main(String[] args) {
		System.out.println(isChinese("?"));
		System.out.println(isChinese('?'));
	}
	
	//whether the string is in Chinese
	public static boolean isChinese(String str) {
		boolean flag = true;
		for(int i = 0; i < str.length(); i++)
			if ((str.charAt(i) < 0x4e00) || (str.charAt(i) > 0x9fbb)){  
				flag = false; break;
			}
		return flag;
	}
	
	//whether the string is in Chinese
	public static boolean isChinese(char c) {
		return ((c >= 0x4e00) && (c <= 0x9fbb)); 
	}
}
