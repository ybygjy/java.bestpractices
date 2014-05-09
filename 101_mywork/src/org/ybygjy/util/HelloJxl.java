package org.ybygjy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 负责EXCEL文件的操作
 * @author WangYanCheng
 * @version 2010-11-15
 */
public class HelloJxl {
    /***/
    private String fileName = "ExcelTest";
    /**
     * 创建EXCEL文件
     * @throws IOException IOException
     * @throws BiffException BiffException
     */
    public void doCreateExcel() throws BiffException, IOException {
        URL fileUrl = this.getClass().getResource("");
        File excelFile = new File(fileUrl.getFile(), fileName);
        WritableWorkbook wwb = Workbook.createWorkbook(excelFile);
        wwb.createSheet("JxlStudy", 0);
        Label label = new Label(0, 0, "createExcel");
        WritableSheet sheet = null;
        try {
            sheet = wwb.getSheet(0);
            sheet.addCell(label);
            sheet.addCell(new jxl.write.Number(1, 0, 789.123));
            wwb.write();
            wwb.close();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        String[] sheetNames = wwb.getSheetNames();
        for (String sheetName : sheetNames) {
            System.out.println(sheetName);
        }
    }

    /**
     * 读取Excel文件
     * @throws IOException IOException
     * @throws BiffException BiffException
     */
    public void doReadExcel() throws BiffException, IOException {
        URL fileUrl = this.getClass().getResource(fileName);
        File excelFile = new File(fileUrl.getFile());
        if (!excelFile.exists()) {
            System.err.println("文件" + excelFile.getPath() + "不存在!");
        }
        Workbook workBook = Workbook.getWorkbook(excelFile);
        Sheet sheet = workBook.getSheet(0);
        Cell cell = sheet.getCell(0, 0);
        String cellContent = cell.getContents();
System.out.println(cellContent);
        workBook.close();
    }

    /**
     * 修改Excel
     * @throws IOException IOException
     * @throws BiffException BiffException
     * @throws WriteException WriteException
     */
    public void doModifyExcel() throws BiffException, IOException, WriteException {
        URL fileUrl = this.getClass().getResource(fileName);
        File excelFile = new File(fileUrl.getFile());
        Workbook workBook = Workbook.getWorkbook(excelFile);
        WritableWorkbook writableBook = Workbook.createWorkbook(excelFile, workBook);
        WritableSheet writableSheet = writableBook.createSheet("第二页", 1);
        writableSheet.addCell(new Label(0, 0, "第二页数据"));
        writableBook.write();
        writableBook.close();
    }
    /**
     * 利用File引用Workbook实例
     * @return file file
     */
    public File doRefWorkbook() {
        File file = null;
        try {
            file = File.createTempFile(String.valueOf(System.currentTimeMillis()), null);
            file.deleteOnExit();
            WritableWorkbook wwb = Workbook.createWorkbook(file);
            WritableSheet wsheet = wwb.createSheet("Sheet1", 0);
            wsheet.addCell(new Label(0, 0, "HelloWorld"));
            wwb.write();
            wwb.close();
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * doWrite
     * @param file file
     */
    public void doWrite(File file) {
        try {
            FileChannel fileChannel = new FileInputStream(file).getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            WritableByteChannel wbChannel = Channels.newChannel(new FileOutputStream(new File("C:\\JxlStudy.xls")));
            while (fileChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                wbChannel.write(byteBuffer);
                byteBuffer.clear();
            }
            fileChannel.close();
            wbChannel.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        doDeleteFile(file);
    }

    /**
     * doDeletefile
     * @param file file
     */
    public void doDeleteFile(File file) {
        if (file.exists()) {
            System.out.println(file.getPath() + ">>isDeleted:" + file.delete());
        }
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        HelloJxl helloJxl = new HelloJxl();
        try {
            helloJxl.doCreateExcel();
            helloJxl.doReadExcel();
            helloJxl.doModifyExcel();
            File tmpFile = helloJxl.doRefWorkbook();
            helloJxl.doWrite(tmpFile);
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
