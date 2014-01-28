package org.ybygjy.ui2.demo2;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

/**
 * 线程与Swing
 * @author WangYanCheng
 * @version 2012-10-31
 */
public class SwingThread extends JFrame implements Observer {
    /**
     * serial ID
     */
    private static final long serialVersionUID = 6365731511993325046L;
    /** 全局对象 */
    private Map<String, JTextField> context;
    /** 输入类组件ID前缀 */
    private static String INPUT_FIELD_PREFIX = "INS_";
    /** 输出类组件ID前缀 */
    private static String OUTPUT_FIELD_PREFIX = "OUS_";

    /**
     * Constructor
     */
    public SwingThread() {
        context = new HashMap<String, JTextField>();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel ctxPanel = new JPanel();
        ctxPanel.setBorder(new TitledBorder("计算斐波那契数："));
        System.out.println(getContentPane().getLayout());
        GridLayout glInst = new GridLayout(10, 1);
        ctxPanel.setLayout(glInst);
        getContentPane().add(ctxPanel);
        createTask(ctxPanel);
        pack();
    }

    /**
     * 创建任务
     * @param mainPanel 主面板
     */
    private void createTask(JPanel mainPanel) {
        for (int i = 1; i <= 10; i++) {
            JLabel tmplJLIn = new JLabel("输入：");
            JLabel tmplJLOu = new JLabel("输出：");
            JPanel jp = new JPanel();
            jp.setBorder(new TitledBorder("第".concat(String.valueOf(i)).concat("组")));
            jp.add(tmplJLIn);
            JTextField jtf = new JTextField(5);
            final String inputKey = INPUT_FIELD_PREFIX.concat(String.valueOf(i));
            regInsComp(inputKey, jtf);
            jp.add(jtf);
            jp.add(tmplJLOu);
            JTextField jtfo = new JTextField(5);
            final String outputKey = OUTPUT_FIELD_PREFIX.concat(String.valueOf(i));
            regInsComp(outputKey, jtfo);
            jp.add(jtfo);
            JButton jtnBtn = new JButton("计算");
            jtnBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    FibBean fbInst = new FibBean();
                    fbInst.setInputKey(inputKey);
                    fbInst.setOutputKey(outputKey);
                    fbInst.setFibTerm(getInsValue(inputKey));
                    new FibonacciTask(SwingThread.this, fbInst);
                }
            });
            jp.add(jtnBtn);
            mainPanel.add(jp);
        }
    }

    /**
     * 注册组件
     * @param key key
     * @param jtf obj
     */
    private void regInsComp(String key, JTextField jtf) {
        context.put(key, jtf);
    }

    /**
     * 取组件值
     * @param key key
     * @return rtnV 默认为0
     */
    private long getInsValue(String key) {
        return context.containsKey(key) ? Integer.parseInt(context.get(key).getText()) : 0;
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SwingThread().setVisible(true);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    public void update(Observable o, Object arg) {
        if (arg instanceof FibBean) {
            final FibBean fibBean = (FibBean) arg;
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    context.get(fibBean.getOutputKey()).setText(String.valueOf(fibBean.getFibValue()));
                }
            });
        }
    }
}
