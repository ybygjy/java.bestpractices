package org.ybygjy.ui.layout;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * GridBagLayout学习
 * @author WangYanCheng
 * @version 2012-9-3
 */
public class GridBagLayoutTest_02 extends JPanel {
    /**
     * serial number
     */
    private static final long serialVersionUID = 3806062656882037283L;
    private JButton btn1,btn2,btn3;
    private JButton btn4;
    private JButton btn5;
    private JButton btn6;
    private JButton btn7;
    private JButton btn8;
    private JButton btn9,btn10;
    /**
     * Constructor
     */
    public GridBagLayoutTest_02() {
        GridBagLayout gblInst = new GridBagLayout();
        GridBagConstraints gbcInst = new GridBagConstraints();
        this.setLayout(gblInst);
        gbcInst.fill = GridBagConstraints.BOTH;//组件的扩展横/纵
        gbcInst.weightx = 1.0;//组件横向权重(权重默认值为0)
        gbcInst.weighty = 1.0;
        //gbcInst.insets = new Insets(5, 5, 5, 5);
        gbcInst.ipady = 50;
        makeButton("Btn1", gblInst, gbcInst);
        makeButton("Btn2", gblInst, gbcInst);
        makeButton("Btn3", gblInst, gbcInst);
        gbcInst.gridwidth = GridBagConstraints.REMAINDER;//标识该组件是最后一个，该属性类似Table的colspan/rowspan
        makeButton("Btn4", gblInst, gbcInst);
        gbcInst.weightx = 0.0;//设置组件权重(默认值) reset to the default
        makeButton("Btn5", gblInst, gbcInst);
        gbcInst.gridwidth = GridBagConstraints.RELATIVE;//标识该组件是倒数第二个
        makeButton("Btn6", gblInst, gbcInst);
        gbcInst.gridwidth = GridBagConstraints.REMAINDER;//标识该组件是最后一个
        makeButton("Btn7", gblInst, gbcInst);
        gbcInst.gridwidth = 1;//设置该组件占用一列
        gbcInst.gridheight = 2;//设置该组件占用二行
        gbcInst.weighty = 0.0;//设置纵向权重(权重默认值为0)
        makeButton("Btn8", gblInst, gbcInst);
        gbcInst.weighty = 0;//纵向权重恢复默认值
        gbcInst.gridwidth = GridBagConstraints.REMAINDER;//标识该组件是最后一个
        gbcInst.gridheight = 1;//设置该组件占用一列
        makeButton("Btn9", gblInst, gbcInst);
        makeButton("Btn10", gblInst, gbcInst);
    }
    /**
     * 封装Button组件处理
     * @param name 组件名称
     * @param gridBag {@link GridBagLayout}
     * @param gridBagConstraint {@link GridBagConstraints}
     */
    private void makeButton(String name, GridBagLayout gridBag, GridBagConstraints gridBagConstraint) {
        JButton btn = new JButton(name);
        gridBag.setConstraints(btn, gridBagConstraint);
        this.add(btn);
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        JFrame jframeInst = new JFrame();
        jframeInst.setLayout(new BorderLayout());
        jframeInst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframeInst.getContentPane().add(new GridBagLayoutTest_02());
        jframeInst.pack();
        jframeInst.setSize(jframeInst.getPreferredSize());
        jframeInst.setVisible(true);
    }
}
