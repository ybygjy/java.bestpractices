package org.ybygjy.util.file.convert;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
/**
 *@author Hj, Date: 2011-5-17
 * Email: Hj-545@qq.com
 */
public class Console {
    
    private FrmMain frm;
    
    private boolean flag = true;
    private String sourceEncoding = "gbk";
    private String targetEncoding = "utf-8";
    
    private String type;
    
    private String filepath;
    private File selectFile;
    
    private String saveFilepath;
    private File selectSaveDir;
    
    public Console(){}
    public void exit() {
        int choose = 
            JOptionPane.showConfirmDialog(
                null, 
                "你确定要退出吗？",
                "确认退出", 
                JOptionPane.YES_NO_OPTION);
        if (choose == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    /** 选择文件或文件夹  */
    public void selectFile() {
        JFileChooser jf = new JFileChooser();
        jf.setDialogTitle("选择文件或文件夹");
        jf.setFileSelectionMode(
                JFileChooser.FILES_AND_DIRECTORIES);
//      FileNameExtensionFilter filter = 
//              new FileNameExtensionFilter(
//              "java&php&txt&jsp&sql", 
//              "java", "php", "txt", "sql", "jsp");
//      jf.setFileFilter(filter);
        int result = jf.showOpenDialog(frm);
        jf.setVisible(true);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectFile = jf.getSelectedFile();
            todoChooser(selectFile);
        }
    }
    
    /** 选择保存路径  */
    public void selectDir() {
        JFileChooser jf = new JFileChooser();
        jf.setDialogTitle("选择保存文件夹");
        jf.setFileSelectionMode(
                JFileChooser.DIRECTORIES_ONLY);
        int result = jf.showOpenDialog(frm);
        jf.setVisible(true);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectSaveDir = jf.getSelectedFile();
            todoChooserDir(selectSaveDir);
        }
    }
    
    /** 选择保存文件夹后做的事情  */
    private void todoChooserDir(File selectSaveDir) {
        if (selectSaveDir.exists()) {
            saveFilepath = 
                selectSaveDir.getAbsolutePath();
            frm.txt_dir.setText(saveFilepath);
            frm.txt_show.append("\n\r");
            frm.txt_show.append(
                        "保存路径: " + saveFilepath);
        } else {
            this.selectSaveDir = null;
            JOptionPane.showMessageDialog(
                    null, 
                    "文件夹不存在!");
        }
    }
    
    /**  选择文件或文件夹后做的事情 */
    private void todoChooser(File selectFile) {
        if (selectFile.exists()) {
            filepath = 
                selectFile.getAbsolutePath();
            frm.txt_file.setText(filepath);
            frm.txt_show.append("\n\r");
            
            if (selectFile.isFile()) {
                flag = true;
                frm.txt_show.append(
                        "文件: " + filepath);
            } else {
                flag = false;
                frm.txt_show.append(
                        "目录: " + filepath);
            }
        } else {
            this.selectFile = null;
            JOptionPane.showMessageDialog(
                    null, 
                    "文件或文件夹不存在!");
        }
    }
    public void setFrm(FrmMain frm) {
        this.frm = frm;
    }
    public FrmMain getFrm() {
        return frm;
    }
    
    /** 显示窗体  */
    public void show() {
        frm.setVisible(true);
    }
    
    /** 设置源编码  */
    public void sourceEncodingChoose() {
        sourceEncoding =    (String) 
            frm.jbx_source.getSelectedItem();
//      System.out.println(sourceEncoding);
    }
    
    /** 设置目标编码  */
    public void targetEncodingChoose() {
        targetEncoding = (String) 
            frm.jbx_target.getSelectedItem();
//      System.out.println(targetEncoding);
    }
    /** 执行按钮转换的方法  */
    public void change() {
        if(selectFile == null){
            JOptionPane.showMessageDialog(
                    null, 
                    "请选择文件或文件夹!");
        }
        
        String sourcePath = frm.txt_file.getText();
        String targetPath = sourcePath + ".bak";
        
        if(flag){
            todoChange(sourcePath, targetPath);
        }else{
            todoAllChage();
        }
    }   
    
    /** 文件夹转换方法  */
    private void todoAllChage() {
        if((type = frm.txt_type.getText()) == null){
            JOptionPane.showMessageDialog(
                    frm,
            "你选择的是文件夹,请指定文件过滤,如:.java");
            return;
        }
        List<File> list = 
            IOCVUtils.listAll(filepath, type);
        if(list.size() == 0){
            JOptionPane.showMessageDialog(frm,
            "该文件夹下面没有你需要的文件!");
        }else{
            frm.txt_show.append("\n\r");
            frm.txt_show.append("你选中的文件夹包含" + list.size() + "个文件:");
            for(File ls:list){
                frm.txt_show.append("\n\r");
                frm.txt_show.append(ls.getAbsolutePath());
            }
            int choose = JOptionPane.showConfirmDialog(frm, 
                    "你确定你要转换这些文件吗?",
                    "Message", JOptionPane.YES_NO_OPTION);
            if (choose == JOptionPane.YES_OPTION) {
                frm.txt_show.append("\n\r");
                frm.txt_show.append("执行批量转换.....");
                IOCVUtils.sourceEncoding = sourceEncoding;
                IOCVUtils.targetEncoding = targetEncoding;
                for(File ls:list){
                    String sourcePath = ls.getAbsolutePath();
                    String targetPath = sourcePath + ".bak";
                    todoChange(sourcePath, targetPath);
                }
            }else{
                return;
            }
        }
        
    }
    
    /** 文件转换方法  */
    private void todoChange(String sourcePath, String targetPath) {
        IOCVUtils.rename(sourcePath, targetPath);
        try {
            IOCVUtils.changeEncoding(targetPath, 
                    sourcePath, 
                    sourceEncoding, 
                    targetEncoding);
            frm.txt_show.append("\n\r");
            frm.txt_show.append("文件转换成功: " + sourcePath);
            frm.txt_show.append("\n\r");
            frm.txt_show.append(targetPath + "为源文件备份!");
        } catch (IOException e) {
//          e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }   
}