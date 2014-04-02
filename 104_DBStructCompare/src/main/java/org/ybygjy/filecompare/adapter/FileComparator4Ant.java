package org.ybygjy.filecompare.adapter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.ybygjy.filecompare.FileComparator;



/**
 * 文件比较Ant任务接口
 * @author WangYanCheng
 * @version 2011-10-20
 */
public class FileComparator4Ant extends org.apache.tools.ant.Task  {
	/**源文件集*/
	private List<FilePath> srcFilePathArr = new ArrayList<FilePath>();
	/**参照地址*/
	private String targetPath;
	/**忽略文件夹*/
	private String excludeDir;
	/**包含文件*/
	private String includeFile;
	/**日志文件*/
	private String logFile;
	/**输出流*/
	private OutputStreamWriter ous;
	@Override
	public void execute() throws BuildException {
		if (srcFilePathArr.size() == 0) {
			throw new BuildException("缺少必要路径信息，请检查或添加类似<FilePath path=\"...\"/>标记。。。");
		}
		ous = getLogOutputStream();
		if (null != ous) {
			outputMsg("转储文件路径：".concat(getLogFile()));
		}
		outputMsg("初始任务。。。");
		String[] srcFilePathes = toArray(srcFilePathArr);
		outputMsg("源地址：".concat(Arrays.toString(srcFilePathes)));
		outputMsg("目标地址：".concat(getTargetPath()));
		outputMsg(80);
		FileComparator fcInst = new FileComparator();
		try {
			fcInst.setOutputStream(ous);
			if (excludeDir != null) {
				outputMsg("忽略文件夹：".concat(excludeDir));
				fcInst.setExcludeDirExp(excludeDir.split(","));
			}
			if (includeFile != null) {
				outputMsg("包含文件：".concat(includeFile));
				fcInst.setIncludeFileExp(includeFile.split(","));
			}
			fcInst.comparator(srcFilePathes, getTargetPath());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			outputMsg("任务结束。。。。");
			if (null != ous) {
				outputMsg("关闭转储流。。。");
				outputMsg("转储文件地址：" + getLogFile());
				try {
					ous.flush();
					ous.close();
					ous = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 取日志流
	 * @return rtnOus/null
	 */
	private OutputStreamWriter getLogOutputStream() {
		if (null == getLogFile()) {
			return null;
		}
		try {
			return new OutputStreamWriter(new FileOutputStream(getLogFile(), false));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 取参照路径
	 * @return rtnTargetPath 参照目标路径
	 */
	public String getTargetPath() {
		return targetPath;
	}

	/**
	 * 设置参照路径
	 * @param targetPath targetPath
	 */
	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}
	/**
	 * 取日志文件路径
	 * @return rtnLogFile 文件路径
	 */
	public String getLogFile() {
		return logFile;
	}
	/**
	 * 设置日志文件路径
	 * @param logFile logFile
	 */
	public void setLogFile(String logFile) {
		this.logFile = logFile;
	}
	/**
	 * Nested嵌套对象实例
	 * @return rtnFilePath rtnFilePath
	 */
	public FilePath createFilePath() {
		FilePath filePath = new FilePath();
		srcFilePathArr.add(filePath);
		return filePath;
	}
	/**
	 * 存储忽略文件夹设定
	 * @param excludeDir 忽略文件夹
	 */
	public void setExcludeDir(String excludeDir) {
		this.excludeDir = excludeDir;
	}
	/**
	 * 存储包含文件后缀设定
	 * @param includeFile 包含文件
	 */
	public void setIncludeFile(String includeFile) {
		this.includeFile = includeFile;
	}

	/**
	 * 输出信息
	 * @param str 信息内容
	 */
	private void outputMsg(String str) {
		System.out.println(str);
		try {
			if (ous != null) {
				ous.write(str);
				ous.write("\r\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输出标记符
	 * @param flagCount 标记符长度
	 */
	private void outputMsg(int flagCount) {
		StringBuilder sbud = new StringBuilder();
		for (int i = 0; i < flagCount; i++) {
			sbud.append("=");
		}
		outputMsg(sbud.toString());
	}
	/**
	 * Nested实例(文件路径)
	 * @author WangYanCheng
	 * @version 2011-10-20
	 */
	public class FilePath {
		private String path;
		public FilePath() {}
		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			if ("".equals(path)) {
				throw new BuildException("不是有效的文件地址，请检查<FilePath.../>标签值。。。");
			}
			this.path = path;
		}
		@Override
		public String toString() {
			return this.path;
		}
	}
	/**
	 * 文件路径实体转换成数组
	 * @param filePathes 文件路径集
	 * @return rtnArr rtnArr
	 */
	private String[] toArray(List<FilePath> filePathes) {
		String[] rtnArr = new String[filePathes.size()];
		int i = 0;
		for (Iterator<FilePath> iterator = filePathes.iterator(); iterator.hasNext();) {
			rtnArr[i++] = iterator.next().path;
		}
		return rtnArr;
	}
	public static void main(String[] args) {
		FileComparator4Ant fc4Ant = new FileComparator4Ant();
		fc4Ant.setTargetPath("F:\\work\\nstc\\2_事务\\003_1文件比较\\sql-oracle-trunk\\BDG");
		FilePath filePath = fc4Ant.new FilePath();
		filePath.setPath("F:\\work\\nstc\\2_事务\\003_1文件比较\\TEST\\BDG");
		fc4Ant.srcFilePathArr.add(filePath);
		fc4Ant.execute();
	}
}
