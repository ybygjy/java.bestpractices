package org.ybygjy.ui.comp;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 * 日志呈现组件
 * @author WangYanCheng
 * @version 2012-9-4
 */
public class LogInfoPanel extends JPanel {

    /**
     * serial number
     */
    private static final long serialVersionUID = 1030107360231197231L;
    /** 日志框 */
    private JTextArea jtaInst;
    /** 滚动组件*/
    private JScrollPane jspInst;
    /**
     * 构造函数
     */
    public LogInfoPanel() {
        this.setBorder(new TitledBorder("日志"));
        jtaInst = new JTextArea();
        jspInst = new JScrollPane(jtaInst);
        jspInst.setAutoscrolls(true);
        GroupLayout groupLayout = new GroupLayout(this);
        this.setLayout(groupLayout);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup().addComponent(jspInst));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup().addComponent(jspInst,GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE));
    }

    /**
     * 接收日志内容
     * @param textInst
     */
    public void setText(String textInst) {
        this.jtaInst.setText(textInst);
    }

    /**
     * 取日志内容
     * @return
     */
    public String getText() {
        return this.jtaInst.getText();
    }
    /**
     * 追加内容
     * @param contentStr
     */
    public void append(String contentStr) {
        if (this.jtaInst.getLineCount() > 2000) {
            this.jtaInst.setText("");
            return;
        }
        this.jtaInst.append(contentStr);
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        JFrame jframeInst = new JFrame();
        jframeInst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframeInst.getContentPane().add(new LogInfoPanel());
        jframeInst.pack();
        jframeInst.setVisible(true);
    }
}
