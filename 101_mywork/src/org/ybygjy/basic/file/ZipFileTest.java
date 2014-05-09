package org.ybygjy.basic.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipFileTest {
    public void restoreZip() {
        HttpURLConnection urlConn = null;
        try {
            URL url = new URL(
                "https://mapi.alipay.com/gateway.do?_input_charset=UTF-8&service=bptb_result_file&partner=2088111775240527&file_name=201405041413246710.csv&sign=c8cad26504544a9d3c5e222ebf3d283e&sign_type=MD5");
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("GET");
            urlConn.setReadTimeout(5 * 1000 * 60);
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.getOutputStream().flush();
            urlConn.getOutputStream().close();
            InputStream ins = urlConn.getInputStream();
            ZipInputStream zisInst = new ZipInputStream(ins);
            ZipEntry zipEntry = null;
            String targetPath = "d:\\";
            while ((zipEntry = zisInst.getNextEntry()) != null) {
                String zipPath = zipEntry.getName();
                try {
                    if (zipEntry.isDirectory()) {
                        File zipFolder = new File(targetPath, File.separator + zipPath);
                        if (!zipFolder.exists()) {
                            zipFolder.mkdirs();
                        }
                    } else {
                        File file = new File(targetPath + File.separator + zipPath);
                        System.out.println(file.getAbsolutePath());
                        if (!file.exists()) {
                            File pathDir = file.getParentFile();
                            pathDir.mkdirs();
                            file.createNewFile();
                        }
                        FileOutputStream fos = new FileOutputStream(file);
                        int bread;
                        while ((bread = zisInst.read()) != -1) {
                            fos.write(bread);
                        }
                        fos.close();
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != urlConn) {
                urlConn.disconnect();
            }
        }
    }

    public static void main(String[] args) {
        new ZipFileTest().restoreZip();
    }
}
