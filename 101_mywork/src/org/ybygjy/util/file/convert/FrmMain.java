package org.ybygjy.util.file.convert;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
public class FrmMain extends JFrame implements ActionListener {
    private static final long serialVersionUID = 8906317933020631699L;
    /** 按钮 转换 */
    JButton btn_change;
    /** 按钮 退出 */
    JButton btn_exit;
    /** 标签 文件或文件夹 */
    JLabel lab_file;
    /** 输入文本 文件路径 */
    JTextField txt_file;
    /** 按钮 "." 选择文件或文件夹 */
    JButton btn_selectFile;
    
    /** 标签 保存文件夹 */
    JLabel lab_dir;
    /** 输入文本 保存路径 */
    JTextField txt_dir;
    /** 按钮 "." 选择保存文件夹 */
    JButton btn_selectDir;
    
    /** 输入块 转换信息 */
    JTextArea txt_show;
    
    /** 标签 选择编码: */
    JLabel lab_encoding;
    /** 下拉列表 源编码 */
    JComboBox jbx_source;
    /** 下拉列表 目标编码 */
    JComboBox jbx_target;
    /** 标签 文件过滤: */
    JLabel lab_type;
    /** 输入文本 过滤类型,如".java" */
    JTextField txt_type;
    /** 程序版本 */
    private String version = "V1.50base";
    /** 程序标题 */
    private String title = 
        "JAVA文件编码转换工具" + version;
    
    /** 下拉列表值:默认编码 */
    private String[] encoding =
        "gbk,utf-8,gb2312,utf-16".split(",");
    
    private Console console;
    
    public FrmMain(){
        init();
    };
    public FrmMain(Console console) {
        this.setConsole(console);
        init();
    }
    private void init() {
        this.setTitle(title);
        this.setContentPane(createContent());
        this.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
//      this.setVisible(true);
    }
    /** 主面板 */
    private Container createContent() {
        JPanel pane = new JPanel(new BorderLayout());
        pane.setBorder(new EmptyBorder(8, 8, 8, 8));
        pane.add(BorderLayout.NORTH, createSelect());
        pane.add(BorderLayout.CENTER, createShowPane());
        pane.add(BorderLayout.SOUTH, createBtnPane());
        return pane;
    }
    
    /** 选择面板  */
    private JPanel createSelect(){
        JPanel pane = new JPanel(new BorderLayout());
//      pane.setBorder(new EmptyBorder(8, 0, 8, 0));
        pane.add(BorderLayout.NORTH, 
                        createSelectFile());
        pane.add(BorderLayout.CENTER, 
                        createSelectSaveDir());
        pane.add(BorderLayout.SOUTH, 
                        createSelectEncoding());
        return pane;
    }
    
    /** 选择文件面板 */
    private JPanel createSelectFile() {
        JPanel pane = new JPanel(new BorderLayout());
        lab_file = new JLabel("文件或文件夹: ");
        txt_file = new JTextField();
        txt_file.setEditable(false);
        btn_selectFile = new JButton(".");
        btn_selectFile.addActionListener(this);
        pane.add(BorderLayout.WEST, lab_file);
        pane.add(BorderLayout.CENTER, txt_file);
        pane.add(BorderLayout.EAST, btn_selectFile);
        return pane;
    }
    
    /** 选择保存路径 */
    private JPanel createSelectSaveDir() {
        JPanel pane = new JPanel(new BorderLayout());
        pane.setBorder(new EmptyBorder(8, 0, 0, 0));
        lab_dir = new JLabel("保存文件夹:");
        txt_dir = new JTextField();
        txt_dir.setEditable(false);
        btn_selectDir = new JButton(".");
        btn_selectDir.addActionListener(this);
        pane.add(BorderLayout.WEST, lab_dir);
        pane.add(BorderLayout.CENTER, txt_dir);
        pane.add(BorderLayout.EAST, btn_selectDir);
        return pane;
    }
    /** 编码,过滤面板 */
    private JPanel createSelectEncoding() {
        JPanel pane = new JPanel(
                new FlowLayout(FlowLayout.LEFT));
        pane.setBorder(new EmptyBorder(8, 0, 0, 0));
        lab_encoding = new JLabel("选择编码:");
        jbx_source = new JComboBox(encoding);
        jbx_source.setSelectedIndex(0);// 默认显示第0个对象值
        jbx_source.addActionListener(this);
        jbx_target = new JComboBox(encoding);
        jbx_target.setSelectedIndex(1);
        jbx_target.addActionListener(this);
        lab_type = new JLabel("文件过滤:");
        txt_type = new JTextField();
        txt_type.setColumns(10);// 设置列长度,否则显示有问题
        txt_type.setText(".java");
        pane.add(lab_encoding);
        pane.add(jbx_source);
        pane.add(jbx_target);
        pane.add(lab_type);
        pane.add(txt_type);
        return pane;
    }
    /** 输出信息面板 */
    private JScrollPane createShowPane() {
        JScrollPane pane = new JScrollPane();
        pane.setBorder(new TitledBorder("信息"));
        txt_show = new JTextArea(10, 10);
        txt_show.setEnabled(false);
        txt_show.setLineWrap(true);// 设置自动换行
        txt_show.setFont(new Font("宋体", Font.BOLD, 13));
        txt_show.setDisabledTextColor(Color.red);
        pane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pane.getViewport().add(txt_show);
        return pane;
    }
    /** 转换退出按钮面板 */
    private Component createBtnPane() {
        JPanel pane = new JPanel();
        btn_change = new JButton("转换");
        btn_change.addActionListener(this);
        btn_exit = new JButton("退出");
        btn_exit.addActionListener(this);
        pane.add(btn_change);
        pane.add(btn_exit);
        this.getRootPane().
            setDefaultButton(btn_change);
        return pane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object temp = e.getSource();
        
        if(temp == btn_change){
            console.change();
        }else if(temp == btn_selectFile){
            console.selectFile();
        }else if(temp == jbx_source){
            console.sourceEncodingChoose();
        }else if(temp == jbx_target){
            console.targetEncodingChoose();
        }else if(temp == btn_selectDir){
            console.selectDir();
        }else if(temp == btn_exit){
            console.exit();
        }
    }
    public void setConsole(Console console) {
        this.console = console;
    }
    public Console getConsole() {
        return console;
    }
}