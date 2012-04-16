package index;

public class EncodeUtil {
	
	//whether the string is in Chinese
	public static boolean isChinese(String str) {
		boolean flag = true;
		for(int i = 0; i < str.length(); i++)
			if ((str.charAt(i) < 0x4e00) || (str.charAt(i) > 0x9fbb)){  
				flag = false; break;
			}
		return flag;
	}
}
