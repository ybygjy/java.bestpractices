package org.ybygjy.basic.file;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;

/**
 * 操作系统打开文件或链接
 * @author WangYanCheng
 * @version 2011-10-18
 */
public class OpenFile4OS {
    /**微软系统*/
    public static String osWindow = "Windows";
    /**苹果系统*/
    public static String osMac = "Mac OS";
    /**操作系统名称*/
    public static final String osName = System.getProperty("os.name");;
    /**利用JDK1.6特性打开/浏览文件*/
    private static OpenFile4Java of4jInst = new OpenFile4Java();
    /**Window平台下打开/浏览文件*/
    private static OpenFile4Window of4Win = new OpenFile4Window();
    /**Mac OS平台下打开/浏览文件*/
    private static OpenFile4Mac of4Mac = new OpenFile4Mac();
    /**Linux 平台下打开/浏览文件*/
    private static OpenFile4Linux of4Linux = new OpenFile4Linux();
    /**
     * 构造函数
     */
    public OpenFile4OS() {
    }

    /**
     * 打开文件
     * @param fileInst 文件实例
     * @return rtnFlag true/false
     * @throws IOException 抛出异常做Log
     */
    public static boolean doOpenFile(File fileInst) throws IOException {
        if (osName.startsWith(osMac)) {
            return of4Mac.openFile(fileInst);
        } else if (osName.startsWith(osWindow)) {
            return of4Win.openFile(fileInst);
        } else {
            if (of4jInst.openFile(fileInst)) {
                System.out.println("文件被打开了");
                return true;
            }
        }
        return false;
    }

    /**
     * 打开链接地址
     * @param urlInst 地址实例
     * @throws IOException IOException
     */
    public static boolean doOpenURL(URL urlInst) throws IOException {
        if (osName.startsWith(osWindow)) {
            return of4Win.browse(urlInst);
        } else if (osName.startsWith(osMac)) {
            return of4Mac.browse(urlInst);
        } else {
            return of4jInst.browse(urlInst);
        }
    }

    /**
     * 测试入口
     * @param args 参数列表
     * @throws IOException 抛出异常做Log
     */
    public static void main(String[] args) throws IOException {
        String tmpFile = "C:\\Documents and Settings\\dell\\桌面\\Oracle体系结构.ppt";
        String tmpFile2 = "C:\\build\\DBCompare.LOG";
        File file = new File(tmpFile);
        OpenFile4OS of4OsInst = new OpenFile4OS();
        of4OsInst.doOpenFile(file);
        of4OsInst.doOpenURL(new URL("http://www.baidu.com"));
    }
}

/**
 * Linux/Unix系统下打开文件或链接
 * @author WangYanCheng
 * @version 2011-10-19
 */
class OpenFile4Linux {
    /**UNIX系统浏览连接命令集*/
    public static String[] unixBrowseCmds = {"www-browser", "firefox", "opera", "konqueror", "mozilla", "netscape", "w3m"};
    /**Unix系统文件打开方式命令集*/
    public static String[] unixOpenCmds = {"run-mailcap", "pager", "less", "more"};
    
    public boolean browse(URL urlInst) throws IOException {
        for (String cmd : unixBrowseCmds) {
            if (unixCommandExists(cmd)) {
                Runtime.getRuntime().exec(new String[]{cmd, urlInst.toString()});
                return true;
            }
        }
        return false;
    }
    public boolean openFile(File fileInst) throws IOException {
        for (String cmd : unixOpenCmds) {
            if (unixCommandExists(cmd)) {
                Runtime.getRuntime().exec(new String[]{cmd, fileInst.getAbsolutePath()});
                return true;
            }
        }
        return false;
    }
    public static boolean unixCommandExists(String cmd) throws IOException {
        Process procInst = Runtime.getRuntime().exec(new String[]{"which", cmd});
        boolean finished = false;
        do {
            try {
                procInst.waitFor();
                finished = true;
            } catch (InterruptedException ine) {
                ine.printStackTrace();
            }
        } while (!finished);
        return procInst.exitValue() == 0;
    }
}
/**
 * Window操作系统下打开文件或链接
 * @author WangYanCheng
 * @version 2011-10-18
 */
class OpenFile4Window {
    /**
     * 浏览地址
     * @param urlInst 地址实例
     * @return rtnFlag true/false
     * @throws IOException IOException
     */
    public boolean browse(URL urlInst) throws IOException {
        Runtime.getRuntime().exec(new String[]{"rundll32", "url.dll,FileProtocolHandler", urlInst.toString()});
        return true;
    }
    /**
     * 打开文件
     * @param fileInst 文件实例
     * @return rtnFlag true/false
     * @throws IOException IOException
     */
    public boolean openFile(File fileInst) throws IOException {
        Runtime.getRuntime().exec(new String[]{"rundll32", "shell32.dll,ShellExec_RunDLL", fileInst.getAbsoluteFile().toString()});
        return true;
    }
}
/**
 * Mac OS 系统实现
 * @author WangYanCheng
 * @version 2011-10-18
 */
class OpenFile4Mac {
    /**
     * Mac OS系统下浏览地址
     * @param urlInst 地址实例
     * @return rtnFlag true/false
     */
    public boolean browse(URL urlInst) {
        Class tmpClass = getAppleFileManagerClass();
        if (null == tmpClass) {
            return false;
        }
        Method openURL;
        try {
            openURL = tmpClass.getDeclaredMethod("openURL", String.class);
            openURL.invoke(null, urlInst.toString());
            return true;
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    /**
     * Mac OS系统下打开文件
     * @param fileInst 文件实例
     * @return rtnFlag true/false
     * @throws IOException IOException
     */
    public boolean openFile(File fileInst) throws IOException {
        return browse(new URL(fileInst.getAbsolutePath()));
    }
    /**
     * 取系统特有实例
     * @return rtnClass rtnClass
     */
    public Class getAppleFileManagerClass() {
        try {
            return Class.forName("com.apple.eio.FileManager");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
/**
 * 使用java.awt.Desktop
 * @author WangYanCheng
 * @version 2011-10-18
 */
class OpenFile4Java {
    public boolean browse(URL urlInst) {
        Class desktopClass = getDesktopClass();
        if (null == desktopClass) {
            return false;
        }
        Object desktopObj = getDesktopClassInst(desktopClass);
        if (null == desktopObj) {
            return false;
        }
        try {
            Method browseMethod = desktopClass.getDeclaredMethod("browse", URI.class);
            browseMethod.invoke(desktopObj, new URI(urlInst.toExternalForm()));
            return true;
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 打开文件
     * @param fileInst 文件实例
     * @return rtnFlag true/false
     * @throws IOException 抛出异常做Log
     */
    public boolean openFile(File fileInst) throws IOException {
        Class desktopClass = getDesktopClass();
        if (null == desktopClass) {
            return false;
        }
        Object desktopInstance = getDesktopClassInst(desktopClass);
        if (null == desktopInstance) {
            return false;
        }
        try {
            Method openMethod = desktopClass.getDeclaredMethod("open", File.class);
            openMethod.invoke(desktopInstance, fileInst);
            return true;
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 取类定义实例
     * @return rtnClass rtnClass
     */
    private Class getDesktopClass() {
        try {
            return Class.forName("java.awt.Desktop");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取类实例
     * @param classTmpl 类定义实例
     * @return rtnObj rtnObj
     */
    private Object getDesktopClassInst(Class classTmpl) {
        try {
            Method isDesktopSupportedMethod = classTmpl.getDeclaredMethod("isDesktopSupported");
            boolean isDesktopSupported = (Boolean) isDesktopSupportedMethod.invoke(null);
            if (!isDesktopSupported) {
                System.out.println("当前JDK版本不支持java.awt.Desktop特性！");
            }
            Method getDesktopMethod = classTmpl.getDeclaredMethod("getDesktop");
            return getDesktopMethod.invoke(null);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}