package org.ybygjy.filecompare;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 文件内容比较
 * @author SunYuJia
 * @author WangYanCheng
 * @version 2011-10-20
 */
public class FileComparator {
	/** 过滤目录*/
	private String[] excludeDirExp = new String[] {".SVN"/*, "TABLE", "TRIGGER", "TYPE", "SEQUENCES"*/};
	/** 过滤文件扩展名*/
	private String[] includeFileExp = new String[]{"PDC", "FNC", "PRC", "SQL", "TRG", "TPS", "VW", "TAB"};
	/** 已检查文件*/
	private List<String> checkedFiles = new ArrayList<String>();
	/** 文件内容不等*/
	private List<InnerClass> unEqualsArr = new ArrayList<InnerClass>();
	/** 未建立有效映射*/
	private List<InnerClass> missedFileArr = new ArrayList<InnerClass>();
	/** 输出流*/
	private OutputStreamWriter ous;
	/**
	 * 打印异常信息
	 */
	public void showMsgs() {
		innerShowMsgs2("找不到对应的文件 " + missedFileArr.size(), missedFileArr);
		innerShowMsgs2("文件不等 " + unEqualsArr.size(), unEqualsArr);
		//innerShowMsgs("参与检测文件", checkedFiles);
	}
	private void innerShowMsgs2(String title, List<InnerClass> tmpList) {
		System.out.println(title);
		writeOus(title);
		for (Iterator<InnerClass> iterator = tmpList.iterator(); iterator.hasNext();) {
			InnerClass icInst = iterator.next();
			System.out.println("\t\t".concat(icInst.srcStr).concat("\t\t").concat(icInst.tarStr));
			writeOus("\t\t".concat(icInst.srcStr).concat("\t\t").concat(icInst.tarStr));
		}
	}

	private void writeOus(String str) {
		if (null == ous) {
			return;
		}
		try {
			ous.write(str);
			ous.write("\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查询文件
	 * @param dir 目录实例
	 * @param fileName 文件名称
	 * @return rtnField/null
	 */
	public File searchFile(File dir, final String fileName) {
		File[] fileArr =  dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return fileName.toUpperCase().equals(name.toUpperCase());
			}
		});
		if (fileArr.length > 0) {
			return fileArr[0];
		} else {
			return null;
		}
	}

	/**
	 * 文件比较入口
	 * @param patchs 文件路径集
	 * @param trunk 参照路径
	 * @throws IOException IO异常
	 */
	public void comparator(String[] patchs, String trunk) throws IOException {
		File trunkDir = new File(trunk);
		for (String tmpPath : patchs) {
			comparator(new File(tmpPath), trunkDir);
		}
		showMsgs();
	}

	/**
	 * 文件比较入口
	 * @param patch 源路径实例
	 * @param trunk 目标路径实例
	 */
	protected void comparator(File patch, File trunk) {
		File[] file = patch.listFiles();
		if (file == null) {
			System.out.println(patch);
			return;
		}
		for (int i = 0; i < file.length; i++) {
			File srcFile = file[i];
			if (checkedFiles.contains(srcFile.getName())) {
				continue;
			}
			if (srcFile.isDirectory() && !filterDir(srcFile)) {
				File f = new File(trunk.getPath() + "/" + srcFile.getName());
				if (!f.exists()) {
					f = trunk;
				}
				comparator(srcFile, f);
			} else if (srcFile.isFile() && filterFile(srcFile)) {
				File trunkFile = searchFile(trunk, srcFile.getName());
				if (trunkFile == null) {
					missedFileArr.add(new InnerClass(srcFile.getPath(), new File(trunk, srcFile.getName()).getPath(), 0));
					continue;
				}
				if (!isEquals(srcFile, trunkFile)) {
					unEqualsArr.add(new InnerClass(srcFile.getPath(), trunkFile.getPath(), 0));
				} else {
//					checkedFiles.add(srcFile.getName());
					checkedFiles.add(srcFile.getAbsolutePath());
				}
			}
		}
	}

	/**
	 * 文件夹过滤
	 * @param dirFile 文件夹实例
	 * @return rtnFlag true/false
	 */
	private boolean filterDir(File dirFile) {
		String dirName = dirFile.getName();
		boolean rtnFlag = false;
		for (String tmpS : excludeDirExp) {
			if (dirName.toUpperCase().endsWith(tmpS)) {
				rtnFlag = true;
				break;
			}
		}
		return rtnFlag;
	}

	/**
	 * 文件过滤
	 * @param patchFile 文件实例
	 * @return true/false
	 */
	private boolean filterFile(File patchFile) {
		boolean rtnFlag = false;
		String fileName = patchFile.getName();
		for (String tmpS : includeFileExp) {
			if ((fileName.toUpperCase()).endsWith(tmpS)) {
				rtnFlag = true;
				break;
			}
		}
		return rtnFlag;
	}

	/**
	 * 文件内容比较
	 * @param file1 源文件
	 * @param file2 目标文件
	 * @return rtnFlag true/false
	 */
	public boolean isEquals(File file1, File file2) {
		try {
			String s1 = org.apache.commons.io.FileUtils.readFileToString(file1, Charset.defaultCharset().displayName());
			String s2 = org.apache.commons.io.FileUtils.readFileToString(file2, Charset.defaultCharset().displayName());
			return s1.trim().equals(s2.trim());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 过滤SVN
	 * @author WangYanCheng
	 * @version 2011-10-20
	 */
	class SvnFileFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return !(name.toUpperCase().endsWith(".SVN"));
		}
	}
	/**
	 * 设置转储流
	 * @param ous
	 */
	public void setOutputStream(OutputStreamWriter ous) {
		this.ous = ous;
	}

	/**
	 * 存储文件夹过滤设定
	 * @param excludeDirExp
	 */
	public void setExcludeDirExp(String[] excludeDirExp) {
		if (null != excludeDirExp) {
			this.excludeDirExp = excludeDirExp;
		}
	}
	
	/**
	 * 存储文件过滤设定
	 * @param includeFileExp
	 */
	public void setIncludeFileExp(String[] includeFileExp) {
		if (null != includeFileExp) {
			this.includeFileExp = includeFileExp;
		}
	}

	/**
	 * 逻辑实体
	 * @author WangYanCheng
	 * @version 2011-10-20
	 */
	protected class InnerClass {
		private String srcStr;
		private String tarStr;
		private int typeFlag;
		public InnerClass(String srcStr, String tarStr, int typeFlag) {
			this.srcStr = srcStr;
			this.tarStr = tarStr;
			this.typeFlag = typeFlag;
		}
		public String getSrcStr() {
			return srcStr;
		}
		public void setSrcStr(String srcStr) {
			this.srcStr = srcStr;
		}
		public String getTarStr() {
			return tarStr;
		}
		public void setTarStr(String tarStr) {
			this.tarStr = tarStr;
		}
		public int getTypeFlag() {
			return typeFlag;
		}
		public void setTypeFlag(int typeFlag) {
			this.typeFlag = typeFlag;
		}
	}
	/**
	 * 测试入口
	 * @param args 参数列表
	 * @throws IOException 抛出异常做Log
	 */
	public static void main(String[] args) throws IOException {
		String[] patchs = new String[1];
		String trunk = "F:\\work\\nstc\\2_事务\\002_N9网上金融部署\\sql\\7_BDG";
		patchs[0] = "F:\\work\\nstc\\2_事务\\002_N9网上金融部署\\sql\\1_3-SAM";
		FileComparator sqlComp = new FileComparator();
		sqlComp.comparator(patchs, trunk);
	}
}
