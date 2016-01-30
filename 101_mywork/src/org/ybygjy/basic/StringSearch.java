package org.ybygjy.basic;

public class StringSearch {
	public static void main(String[] args) {
		String pathLocation = "classpath*:META-INF/spring/*.xml";
		int prefixEnd = pathLocation.indexOf(':') + 1;
		int rootDirEnd = pathLocation.length();
System.out.println(rootDirEnd + ":" + prefixEnd);
		while (rootDirEnd > prefixEnd) {
			rootDirEnd = pathLocation.lastIndexOf('/', rootDirEnd - 2) + 1;
			System.out.println(rootDirEnd);
		}
System.out.println(rootDirEnd + ":::" + pathLocation.lastIndexOf('/', 19));
	}
}
