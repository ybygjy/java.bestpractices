package org.ybygjy.web.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 封装文件常用公共操作
 * @author WangYanCheng
 * @version 2010-8-31
 */
public class FileUtils {
    /**
     * 取得操作系统支持的临时文件夹路径
     * @return rtnStr rtnStr
     */
    public static String getTmpFilePath() {
        String rtnStr = System.getProperty("java.io.tmpdir");
        return rtnStr;
    }

    /**
     * 调用OS命令打开文件<code>适用于调试</code>
     * <ol>
     * <li>Desktop办法在打开.html后缀文件时不起作用.</li>
     * </ol>
     * @param file fileInst
     */
    public static void doOpenFile4OS(File file) {
        FileOpenMgr.doOpen(file);
    }

    /**
     * 打开链接，可支持本地文件浏览(打开)
     * @param uri {@link URI}
     */
    public static void browseResource(URI uri) {
        FileOpenMgr.doBrowse(uri);
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        // FileUtils.doOpenFile4OS(new File("C:\\AntOpenCommand.java"));
        // FileUtils.doBrowseResource(new
        // File("C:\\AntOpenCommand.java").toURI());
        try {
            URI uri = new URI("http://www.163.com");
            FileUtils.browseResource(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件打开管理
     * @author WangYanCheng
     * @version 2010-12-16
     */
    static class FileOpenMgr {
        /** jdk6 desktop class */
        private static Object desktop;
        /** desktop instance */
        private static Class desktopClass;
        static {
            try {
                desktopClass = Class.forName("java.awt.Desktop");
                if (desktopClass != null) {
                    Method method = desktopClass.getDeclaredMethod("getDesktop");
                    desktop = method.invoke(null);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                desktop = null;
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        /**
         * doOpen
         * @param file file
         */
        protected static void doOpen(File file) {
            if (desktop != null) {
                try {
                    Method method = desktopClass.getDeclaredMethod("open", File.class);
                    if (method != null) {
                        method.invoke(desktop, file);
                    }
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
            }
        }

        /**
         * 打开链接
         * @param uri uri地址
         */
        protected static void doBrowse(URI uri) {
            if (doBrowse4Window(uri)) {
                return;
            }
            if (null != desktop) {
                try {
                    Method method = desktopClass.getDeclaredMethod("browse", URI.class);
                    if (null != method) {
                        method.invoke(desktop, uri);
                    }
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
            }
        }

        /**
         * Window打开链接
         * @param uri {@link URI}
         * @return rtnBool rtnBool
         */
        protected static boolean doBrowse4Window(URI uri) {
            boolean rtnBool = false;
            try {
                String[] param = {"rundll32", "url.dll,FileProtocolHandler", uri.toURL().toString()};
                Runtime.getRuntime().exec(param);
                rtnBool = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return rtnBool;
        }
    }
}
