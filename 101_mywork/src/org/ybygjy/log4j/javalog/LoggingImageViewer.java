package org.ybygjy.log4j.javalog;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

/**
 * Java日志系统图形呈现
 * @author WangYanCheng
 * @version 2011-1-26
 */
public class LoggingImageViewer {
    /** 配置实现类 */
    private static String confClass = "java.util.logging.config.class";
    /** 配置文件 */
    private static String confFile = "java.util.logging.config.file";
    /** LoggerName */
    private static String loggerName = LoggingImageViewer.class.getName();

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        initialLogCtx();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Handler windowHandler = new WindowHandler();
                windowHandler.setLevel(Level.ALL);
                Logger.getLogger(loggerName).addHandler(windowHandler);
                new ImageViewerFrame().setVisible(true);
                Logger.getLogger(loggerName).fine("HelloWorld");
            }
        });
    }

    /**
     * 初始日志环境
     * <ol>
     * <li>日志级别设定</li>
     * <li>日志处理器设定</li>
     * </ol>
     */
    private static void initialLogCtx() {
        if (null == System.getProperty(confClass) && null == System.getProperty(confFile)) {
            Logger.getLogger(loggerName).setLevel(Level.ALL);
            try {
                Handler handler = new FileHandler("./log%u.log", 0, 10);
                Logger.getLogger(loggerName).addHandler(handler);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * 呈现图片内容
 * @author WangYanCheng
 * @version 2011-1-26
 */
class ImageViewerFrame extends JFrame {
    /**
     * serialize number
     */
    private static final long serialVersionUID = 1L;
    /** logger */
    private Logger logger = Logger.getLogger(LoggingImageViewer.class.getName());
    /** label */
    private JLabel label;

    /**
     * Constructor
     */
    public ImageViewerFrame() {
        logger.entering("ImageViewerFrame", "<init>");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("ImageViewer");
        setLocation(300, 0);
        setSize(400, 400);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        JMenuItem openItem = new JMenuItem("Open");
        menu.add(openItem);
        JMenuItem exitItem = new JMenuItem("Exit");
        menu.add(exitItem);
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.fine("Exiting.");
                setVisible(false);
            }
        });
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.entering("ImageViewerFrame.FileOpenListener", "actionPerformed", e);
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("."));
                chooser.setFileFilter(new FileFilter() {
                    @Override
                    public String getDescription() {
                        return "GIF Images";
                    }

                    @Override
                    public boolean accept(File f) {
                        return f.getName().toLowerCase().endsWith(".gif") || f.isDirectory();
                    }
                });
                int r = chooser.showOpenDialog(ImageViewerFrame.this);
                if (r == JFileChooser.APPROVE_OPTION) {
                    String name = chooser.getSelectedFile().getPath();
                    logger.log(Level.FINE, "Reading File {0}", name);
                    label.setIcon(new ImageIcon(name));
                } else {
                    logger.fine("File open dialog canceled.");
                }
                logger.exiting("ImageViewerFrame.FileOpenListener", "actionPerformed");
            }
        });
        label = new JLabel();
//        label.setDebugGraphicsOptions(DebugGraphics.FLASH_OPTION);
        add(label);
        logger.exiting("ImageViewerFrame", "<init>");
//        RepaintManager.currentManager(getRootPane()).setDoubleBufferingEnabled(false);
//        ((JComponent) getContentPane()).setDebugGraphicsOptions(DebugGraphics.FLASH_OPTION);
    }
}

/**
 * 自定义日志处理器
 * @author WangYanCheng
 * @version 2011-1-26
 */
class WindowHandler extends StreamHandler {
    /** jframe日志内容组件容器 */
    private JFrame jframe;

    /**
     * Constructor
     */
    public WindowHandler() {
        jframe = new JFrame();
        final JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        jframe.setSize(new Dimension(200, 200));
        jframe.add(new JScrollPane(textArea));
        jframe.setFocusable(false);
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setOutputStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                textArea.append(String.valueOf((char) b));
            }
        });
    }

    /*
     * (non-Javadoc)
     * @see java.util.logging.StreamHandler#publish(java.util.logging.LogRecord)
     */
    @Override
    public synchronized void publish(LogRecord record) {
        if (!jframe.isVisible()) {
            return;
        }
        super.publish(record);
        flush();
    }
}
