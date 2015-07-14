package org.ybygjy.util;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.ybygjy.basic.file.FileGrep;
import org.ybygjy.gui.GBC;

/**
 * 请求特定的文件分析出URL并访问
 * @author WangYanCheng
 * @version 2015年7月12日
 */
public class RequestFileURL {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				UILayer uiLayer = new UILayer();
				uiLayer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				uiLayer.setVisible(true);
			}
		});
	}
}
//UI层
class UILayer extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private JFileChooser fileChooser;
	private JTextField filePathText;
	private JTextArea console;
	public UILayer() {
		this.setSize(300, 300);
		this.contentPanel = new JPanel();
		//1.指定文件位置
		Action openFilePath = new OpenFilePathDialog();
		//2.触发执行逻辑
		Action executeAction = new ExecuteAction();
		
		//一个文本框
		filePathText = new JTextField(10);
		//两个按钮
		JButton selectFileLocalBtn = new JButton(openFilePath);
		JButton executeBtn = new JButton(executeAction);
		//一个提示框
		console = new JTextArea(8,10);
		console.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(console); 
		
		JPanel northPanel = new JPanel();
		GridBagLayout gridBagLayout = new GridBagLayout();
		northPanel.setLayout(gridBagLayout);
		northPanel.add(new JLabel("文件位置:"), new GBC(0, 0).setAnchor(GridBagConstraints.CENTER).setWeight(0, 0));
		northPanel.add(filePathText, new GBC(1,0).setFill(GridBagConstraints.BOTH).setWeight(100, 100));
		northPanel.add(selectFileLocalBtn, new GBC(2, 0).setWeight(100, 100).setAnchor(GridBagConstraints.NORTHWEST));
		//文件选择对话框
		this.fileChooser = new JFileChooser();
		
		this.add(northPanel, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(executeBtn, BorderLayout.SOUTH);
		pack();
	}
//Action层
	/**
	 * 选择文件
	 * @author WangYanCheng
	 * @version 2015年7月12日
	 */
	class OpenFilePathDialog extends AbstractAction {
		/**
		 * serialize number
		 */
		private static final long serialVersionUID = 1L;
		public OpenFilePathDialog() {
			this.putValue(Action.NAME, "...");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			int returnFlag = fileChooser.showOpenDialog(contentPanel);
			if (JFileChooser.APPROVE_OPTION == returnFlag) {
				File selectFile = fileChooser.getSelectedFile();
				filePathText.setText(selectFile.getAbsolutePath());
			} else {
				System.out.println(returnFlag);
			}
		}
	}
	class ExecuteAction extends AbstractAction {
		private static final long serialVersionUID = 2782193112648958049L;

		public ExecuteAction() {
			this.putValue(Action.NAME, "执行");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			File fileInst = null;
			if (filePathText.getText() == null
					|| (fileInst = new File(filePathText.getText())) == null) {
				JOptionPane.showMessageDialog(contentPanel, "请选择文件！");
			} else {
				ExecuteTasks executeTask = new ExecuteTasks(fileInst, this);
				new Thread(executeTask).start();
			}
		}
		public void callBack(final String resultMsg) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					console.append(resultMsg);
				}
			});
		}
	}
//逻辑层
	/**
	 * 执行任务
	 * @author WangYanCheng
	 * @version 2015年7月13日
	 */
	class ExecuteTasks implements Runnable {
		private ExecuteAction executeAction;
		private File fileInst;
		public ExecuteTasks(File fileInst, ExecuteAction executeAction) {
			this.executeAction = executeAction;
			this.fileInst = fileInst;
		}
		@Override
		public void run() {
			FileGrep fileGrep = FileGrep.getInst(Charset.forName("UTF-8"));
			String[] fileContents = null;
			try {
				fileContents = fileGrep.readFileContent(fileInst);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(contentPanel, "文件内容读取失败！" + e1.getMessage());
			}
			if (fileContents != null) {
				for(int i = 0; i < fileContents.length; i++) {
					boolean result = FileGrep.doConnURL(fileContents[i]);
					this.executeAction.callBack(result + "处理=>" + fileContents[i] + "\n");
				}
			}
		}
	}
}
