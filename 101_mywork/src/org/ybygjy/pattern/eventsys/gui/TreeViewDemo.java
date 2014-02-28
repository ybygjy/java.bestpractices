package org.ybygjy.pattern.eventsys.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * 以TreeView为示例,研究事件处理机制
 * @author WangYanCheng
 * @version 2010-9-14
 */
public class TreeViewDemo extends JPanel implements TreeSelectionListener {
    /** serial */
    private static final long serialVersionUID = -2404781788017346441L;
    /** editorPanel */
    private JEditorPane htmlPanel;
    /** treeInst */
    private JTree treeInst;
    /** urlInst */
    private URL helpURL;
    /** line style */
    private static String lineStyle = "Horizontal";
    /** system look and feel */
    private static boolean useSystemLookAndFeel = false;

    /**
     * Constructor
     */
    public TreeViewDemo() {
        super(new GridLayout(1, 0));
//        DefaultMutableTreeNode topNode = new DefaultMutableTreeNode("根");
//        createNodes(topNode);
//        treeInst = new JTree(topNode);
        treeInst = new JTree();
        treeInst.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        treeInst.addTreeSelectionListener(this);
        //treeInst.setModel(new DefaultTreeModel(topNode));
        treeInst.setEditable(true);
        treeInst.getModel().addTreeModelListener(new TreeModelLisImpl());
        treeInst.putClientProperty("JTree.lineStyle", lineStyle);
        JScrollPane treeView = new JScrollPane(treeInst);
        htmlPanel = new JEditorPane();
        htmlPanel.setEditable(false);
        initHelp();
        JScrollPane htmlView = new JScrollPane(htmlPanel);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(treeView);
        splitPane.setBottomComponent(htmlView);
        Dimension minimumSize = new Dimension(100, 50);
        htmlView.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(100);
        splitPane.setPreferredSize(new Dimension(500, 300));
        add(splitPane);
    }

    /**
     * doInitHelp
     */
    private void initHelp() {
        String s = "TreeDemoHelp.html";
        helpURL = getClass().getResource(s);
        if (null != helpURL) {
            displayURL(helpURL);
        }
    }

    /**
     * do display
     * @param urlInst urlInst
     */
    private void displayURL(URL urlInst) {
        try {
            htmlPanel.setPage(urlInst);
        } catch (IOException ioe) {
            htmlPanel.setText("File Not Found!");
            ioe.printStackTrace();
        }
    }

    /**
     * 实体
     * @author WangYanCheng
     * @version 2010-9-14
     */
    private class BookInfo {
        /** 名称 */
        public String bookName;
        /** url */
        public URL bookURL;

        /**
         * 构造
         * @param book book
         * @param fileName fileName
         */
        public BookInfo(String book, String fileName) {
            bookName = book;
            bookURL = getClass().getResource(fileName);
            if (bookURL == null) {
                System.err.println("Couldn't find file: " + fileName);
            }
        }

        @Override
        public String toString() {
            return bookName;
        }
    }

    /**
     * doCreate Nodes
     * @param nodeInst nodeInst
     */
    private void createNodes(DefaultMutableTreeNode nodeInst) {
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode book = null;
        category = new DefaultMutableTreeNode("A");
        nodeInst.add(category);
        book = new DefaultMutableTreeNode(new BookInfo("A.1", "A_1.html"));
        category.add(book);
        book = new DefaultMutableTreeNode(new BookInfo("A.2", "A_2.html"));
        category.add(book);
    }

    /**
     * {@inheritDoc}
     */
    public void valueChanged(TreeSelectionEvent e) {
        JTree treeInst = (JTree) e.getSource();
        DefaultMutableTreeNode eventNode = (DefaultMutableTreeNode) treeInst.getLastSelectedPathComponent();
        if (null == eventNode) {
            return;
        }
        Object nodeInfo = eventNode.getUserObject();
        if (eventNode.isLeaf()) {
            if (nodeInfo instanceof BookInfo) {
                BookInfo bookInfo = (BookInfo) nodeInfo;
                displayURL(bookInfo.bookURL);
            }
        } else {
            displayURL(helpURL);
        }
        /*DefaultTreeModel dtmInst = (DefaultTreeModel) treeInst.getModel();
        if (null != dtmInst) {
            dtmInst.removeNodeFromParent(eventNode);
        }*/
    }

    /**
     * createAndShowUI
     */
    private static void createAndShowUI() {
        if (useSystemLookAndFeel) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        JFrame frame = new JFrame("TreeDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TreeViewDemo());
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * TreeModel->TreeModelListener
     * @author WangYanCheng
     * @version 2010-9-15
     */
    class TreeModelLisImpl implements TreeModelListener {
        /** treePath */
        TreePath tpInst;

        /**
         * {@inheritDoc}
         */
        public void treeNodesChanged(TreeModelEvent e) {
            tpInst = e.getTreePath();
            System.out.println("treeNodesChanged::" + tpInst.toString());
        }

        /**
         * {@inheritDoc}
         */
        public void treeNodesInserted(TreeModelEvent e) {
            tpInst = e.getTreePath();
            System.out.println("treeNodesInserted::" + tpInst.toString());
        }

        /**
         * {@inheritDoc}
         */
        public void treeNodesRemoved(TreeModelEvent e) {
            tpInst = e.getTreePath();
            System.out.println("treeNodesRemoved::" + tpInst.toString());
        }

        /**
         * {@inheritDoc}
         */
        public void treeStructureChanged(TreeModelEvent e) {
            tpInst = e.getTreePath();
            System.out.println("treeNodesRemoved::" + tpInst.toString());
        }
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowUI();
            }
        });
    }
}
