package com.ideamov.utils;

public class CharUtil {
	
	/**
	 * 字符串首字母转化大写
	 * 2017-06-17
	 *  myt
	 */
	static public String firstUpper(String str)
	{
		char [] chars = str.toCharArray();
		
		for(int i=0; i<chars.length; i++)
		{
			if(i==0)
			{
				if (chars[i] >= 'a' && chars[i] <= 'z') {
				chars[i] -=32;
				}
			}
			
		}
		return String.valueOf(chars);
	}
 

}
