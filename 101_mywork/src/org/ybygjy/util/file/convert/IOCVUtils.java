package org.ybygjy.util.file.convert;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/** IO 工具类 */
public class IOCVUtils {
    /**
     * 获取目录的全部文件
     * 
     * @param dir
     * @return
     */
    public static List<File> listFile(File dir) {
        File[] files = dir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });
        return new ArrayList<File>(Arrays.asList(files));
    }
    /**
     * 获取目录的全部文件, 指定扩展名的文件
     * 
     * @param dir
     * @param ext
     *            文件扩展名
     * @return
     */
    public static List<File> listFile(File dir, final String ext) {
        File[] files = dir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isFile() && 
                    pathname.getName().endsWith(ext);
            }
        });
        return new ArrayList<File>(Arrays.asList(files));
    }
    /**
     * 递归获取目录的全部文件
     * 
     * @param dir
     * @return
     */
    public static List<File> listAll(File dir) {
        List<File> all = listFile(dir);
        File[] subs = dir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        for (File sub : subs) {
            all.addAll(listAll(sub));
        }
        return all;
    }
    /**
     * 递归获取目录的全部文件, 指定扩展名的文件
     * 
     * @param dir
     * @param ext
     *            文件扩展名
     * @return
     */
    public static List<File> listAll(String dir, String ext) {
        File dirFile = new File(dir);
        return listAll(dirFile, ext);
    }
    /**
     * 递归获取目录的全部文件, 指定扩展名的文件
     * 
     * @param dir
     * @param ext
     *            文件扩展名
     * @return
     */
    public static List<File> listAll(File dir, String ext) {
        List<File> all = listFile(dir, ext);
        File[] subs = dir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        for (File sub : subs) {
            all.addAll(listAll(sub, ext));
        }
        return all;
    }
    /**
     * 复制文件
     */
    public static void cp(String from, String to) {
        cp(new File(from), new File(to));
    }
    /**
     * 复制文件
     */
    public static void cp(File from, File to) {
        try {
            FileInputStream in;
            in = new FileInputStream(from);
            OutputStream out = new FileOutputStream(to);
            cp(in, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // in.close();
        // out.close();
    }
    /**
     * 复制文件
     */
    public static void cp(InputStream in, OutputStream out) {
        try {
            // 1K byte 的缓冲区!
            byte[] buf = new byte[1024];
            int count;
            while ((count = in.read(buf)) != -1) {
                // System.out.println(count);
                out.write(buf, 0, count);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 文件改名
     */
    public static boolean rename(String source, String target) {
        File file = new File(source);
        File targetFile = new File(target);
        return file.renameTo(targetFile);
    }
    /** 源文件编码 */
    public static String sourceEncoding = null;
    /** 目标编码 */
    public static String targetEncoding = "utf-8";
    /**
     * 用途：文件内容转编码,来源互联网
     * 
     * @param sourceFile
     * @param targetFile
     * @throws IOException
     */
    public static void changeEncoding(File sourceFile, File targetFile)
            throws IOException {
        FileInputStream fin = null;
        FileOutputStream fout = null;
        FileChannel fcin = null;
        FileChannel fcout = null;
        if (sourceEncoding == null) {
            IOCVUtils.sourceEncoding = System.getProperty("file.encoding");
        }
        try {
            fin = new FileInputStream(sourceFile);
            fout = new FileOutputStream(targetFile);
            fcin = fin.getChannel();
            fcout = fout.getChannel();
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
            while (true) {
                buffer.clear();
                int r = fcin.read(buffer);
                if (r == -1) {
                    break;
                }
                buffer.flip();
                fcout.write(ByteBuffer.wrap(Charset.forName(sourceEncoding)
                        .decode(buffer).toString().getBytes(targetEncoding)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fin != null) {
                fin.close();
            }
            if (fcin != null) {
                fcin.close();
            }
            if (fout != null)
                fout.close();
            if (fcout != null)
                fcout.close();
        }
    }
    /**
     * 用途：文件内容转编码
     * 
     * @param sourceFile
     * @param targetFile
     * @throws IOException
     */
    public static void changeEncoding(String sourceFile, String targetFile)
            throws IOException {
        File fl1 = new File(sourceFile);
        File fo1 = new File(targetFile);
        changeEncoding(fl1, fo1);
    }
    /**
     * 用途：文件内容转编码
     * 
     * @param sourceFile
     * @param targetFile
     * @param sourceEncoding
     *            源文件编码 默认源文件的系统存储编码 
     *            System.getProperty("file.encoding");
     * @param targetEncoding
     *            目标编码 默认utf-8
     * @throws IOException
     */
    public static void changeEncoding(String sourceFile, String targetFile,
            String sourceEncoding, String targetEncoding) throws IOException {
        IOCVUtils.sourceEncoding = sourceEncoding;
        IOCVUtils.targetEncoding = targetEncoding;
        changeEncoding(sourceFile, targetFile);
    }
    public static void main(String[] args) throws IOException {
         File file = new File("D://我的文档//workspace//FinishWork");
         List<File> list = listAll(file);
         System.out.println(list.size());
         for(File f: list){
         System.out.println(f);
         }
//      String from = "E://TDDOWNLOAD//QvodSetupPlus42222.exe";
//      String to = "E://TDDOWNLOAD222//";
//      cpDir(from, to, true);
    }
}