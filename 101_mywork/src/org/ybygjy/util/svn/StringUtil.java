package org.ybygjy.util.svn;

public final class StringUtil {
   private StringUtil (){}
   
   public static boolean hasLength(String str){
	   if (str == null || "".equals(str)){
		   return false;
	   }else{
		   return true;
	   }
   }
   
   public static boolean hasLengthAfterTrim(String str){
	   if (str == null || "".equals(str.trim())){
		   return false;
	   }else{
		   return true;
	   }
	   
   }
   public static String subStrLast1(String s) {
		s = s.trim();
		if (s.endsWith(";")) {
			return s.substring(0, s.length() - 1);
		} else {
			return s;
		}
	}
}
