package org.ybygjy.pattern.proxy.tracer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSlider;

/**
 * 监测事件信息测试
 * @author WangYanCheng
 * @version 2011-1-26
 */
public class EventTracerTest {
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new EventTracerFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

/**
 * Swing容器组件
 * @author WangYanCheng
 * @version 2011-1-26
 */
class EventTracerFrame extends JFrame {
    /**
     * serialize number
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public EventTracerFrame() {
        setTitle("EventTracerTest");
        setSize(400, 400);
        add(new JSlider(), BorderLayout.NORTH);
        add(new JButton("Test"), BorderLayout.SOUTH);
        EventTracer tracer = new EventTracer();
        tracer.add(this);
    }
}
