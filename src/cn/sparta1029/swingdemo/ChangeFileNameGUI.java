package cn.sparta1029.swingdemo;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import java.io.File;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChangeFileNameGUI extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2512820068895880128L;
	private JPanel contentPane;
	private JTable tableCurrentFileName, tableNewFileName;
	private JTextField textNewFileName;
	private JButton btnComfirm;
	private JLabel lblNewLabel;
	private DefaultTableModel tableCurrentFileNameModel, tableNewFileNameModel;
	private JButton btnFileSelect;
	private File[] files;
	String[][] tempFile = null;
	Vector<String> vectordefault;
	Vector<Vector<String>> vectorData = new Vector<Vector<String>>();
	private JTextField textNumberList;
	String batchFileName, numberList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangeFileNameGUI frame = new ChangeFileNameGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// 转自 http://blog.csdn.net/xiaolang85/article/details/3370219
	public void FitTableColumns(JTable myTable) {
		JTableHeader header = myTable.getTableHeader();
		int rowCount = myTable.getRowCount();

		Enumeration<TableColumn> columns = myTable.getColumnModel()
				.getColumns();
		while (columns.hasMoreElements()) {
			TableColumn column = (TableColumn) columns.nextElement();
			int col = header.getColumnModel().getColumnIndex(
					column.getIdentifier());
			int width = (int) myTable
					.getTableHeader()
					.getDefaultRenderer()
					.getTableCellRendererComponent(myTable,
							column.getIdentifier(), false, false, -1, col)
					.getPreferredSize().getWidth();
			for (int row = 0; row < rowCount; row++) {
				int preferedWidth = (int) myTable
						.getCellRenderer(row, col)
						.getTableCellRendererComponent(myTable,
								myTable.getValueAt(row, col), false, false,
								row, col).getPreferredSize().getWidth();
				width = Math.max(width, preferedWidth);
			}
			header.setResizingColumn(column); // 此行很重要
			column.setWidth(width + myTable.getIntercellSpacing().width);
		}
	}

	public ChangeFileNameGUI() {

		if (UIManager.getLookAndFeel().isSupportedLookAndFeel()) {
			final String platform = UIManager.getSystemLookAndFeelClassName();
			// If the current Look & Feel does not match the platform Look &
			// Feel,
			// change it so it does.
			if (!UIManager.getLookAndFeel().getName().equals(platform)) {
				try {
					UIManager.setLookAndFeel(platform);
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		}

		this.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		vectordefault = new Vector<String>();
		tableCurrentFileNameModel = new DefaultTableModel();
		tableNewFileNameModel = new DefaultTableModel();

		Vector<String> vectorColumnNames = new Vector<String>();
		vectorColumnNames.addElement("文件名");
		vectorColumnNames.addElement("大小");

		vectordefault.addElement("");
		vectordefault.addElement("");
		vectorData.addElement(vectordefault);
		tableCurrentFileNameModel.setDataVector(vectorData, vectorColumnNames);
		tableNewFileNameModel.setDataVector(vectorData, vectorColumnNames);

		tableCurrentFileName = new JTable(tableCurrentFileNameModel);
		tableCurrentFileName.setRowHeight(20);
		tableCurrentFileName.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		FitTableColumns(tableCurrentFileName);

		JScrollPane JSPCurrentFileName = new JScrollPane(tableCurrentFileName);
		contentPane.add(JSPCurrentFileName);
		JSPCurrentFileName.setBounds(10, 10, 300, 280);

		tableNewFileName = new JTable(tableNewFileNameModel);
		tableNewFileName.setRowHeight(20);
		tableNewFileName.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		FitTableColumns(tableNewFileName);

		JScrollPane JSPNewFileName = new JScrollPane(tableNewFileName);
		JSPNewFileName.setBounds(334, 10, 300, 280);
		contentPane.add(JSPNewFileName);

		JLabel label = new JLabel(">>");
		label.setBounds(315, 140, 20, 15);
		contentPane.add(label);

		textNewFileName = new JTextField("[Yuru Yuri San Hai][**].xml");
		textNewFileName.setBounds(20, 330, 610, 25);
		contentPane.add(textNewFileName);
		textNewFileName.setColumns(10);

		JButton btnTest = new JButton("\u6D4B\u8BD5");
		btnTest.setBounds(295, 405, 60, 30);
		btnTest.addActionListener(this);
		btnTest.setActionCommand("test");
		contentPane.add(btnTest);

		btnComfirm = new JButton("\u786E\u5B9A");
		btnComfirm.setBounds(390, 405, 60, 30);
		contentPane.add(btnComfirm);
		btnComfirm.addActionListener(this);
		btnComfirm.setActionCommand("comfirm");

		lblNewLabel = new JLabel(
				"**\u8868\u793A\u6279\u91CF\u66F4\u6539\u6570\u5B57\uFF08\u52A0\u62D3\u5C55\u540D\uFF09 [Yuru Yuri San Hai][01].xml\u2192[Yuru Yuri San Hai][**].xml");
		lblNewLabel.setBounds(20, 300, 565, 25);
		contentPane.add(lblNewLabel);

		btnFileSelect = new JButton("\u589E\u52A0");
		btnFileSelect.setBounds(200, 405, 60, 30);
		contentPane.add(btnFileSelect);
		btnFileSelect.addActionListener(this);
		btnFileSelect.setActionCommand("addfile");

		JLabel lblNewLabel_1 = new JLabel(
				"\u6570\u5B57\u5E8F\u5217\uFF08\u7528/\u5206\u5272\uFF09");
		lblNewLabel_1.setBounds(20, 365, 120, 25);
		contentPane.add(lblNewLabel_1);

		textNumberList = new JTextField("10/11/12/13/01/02/03/04/05/06/07/08/09/");
		textNumberList.setBounds(139, 365, 490, 25);
		contentPane.add(textNumberList);
		textNumberList.setColumns(10);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("test".equals(e.getActionCommand())) {
			try {
				// TODO 需判断是否数字序列（可能为字符串），以及批量文件名是否符合规范（是否包含**）
				batchFileName = textNewFileName.getText().toString().trim();
				numberList = textNumberList.getText().toString();
				tempFile = new String[files.length][2];
				Long tempFileLength;
				for (int j = tableNewFileNameModel.getRowCount() - 1; j >= 0; j--)
					tableNewFileNameModel.removeRow(j);
				if ("".equals(batchFileName))
					JOptionPane.showMessageDialog(null, "请输入文件名", "警告",
							JOptionPane.ERROR_MESSAGE);
				else {
					if ("".equals(numberList))
						JOptionPane.showMessageDialog(null, "请输入数字序列", "警告",
								JOptionPane.ERROR_MESSAGE);
					else {
						String[] number = numberList.split("/");
						int length = files.length;
						if (number.length == length)// 未选择文件会出现空指针异常
						{
							for (int i = 0; i < number.length; i++) {
								tempFileLength = files[i].length();
								// replaceAll使用正则表达式替换，不能直接用**，需换为\\x2A\\x2A
								// tempFile[i][0]=batchFileName.replaceAll("\\x2A\\x2A",
								// number[i]);
								tempFile[i][0] = batchFileName.replace("**",
										number[i]);
								tempFile[i][1] = tempFileLength.toString();
								System.out.println(files[i].getAbsolutePath());
								Vector<String> vectorNew = new Vector<String>();
								vectorNew.addElement(tempFile[i][0]);
								vectorNew.addElement(tempFile[i][1]);
								tableNewFileNameModel.addRow(vectorNew);
							}
						} else {
							String errorDialog = "请根据列表中文件名输入" + files.length
									+ "个由/分割的数字";
							JOptionPane.showMessageDialog(null, errorDialog,
									"警告", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				FitTableColumns(tableNewFileName);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "出现异常：" + ex.getMessage(),
						"警告", JOptionPane.ERROR_MESSAGE);
			}
		}

		if ("addfile".equals(e.getActionCommand())) {

			JFileChooser jFileChooser = new JFileChooser("./");// 默认目录

			Mp4FileFilter mp4Filter = new Mp4FileFilter(); // Mp4过滤器
			MkvFileFilter mkvFilter = new MkvFileFilter();
			AviFileFilter aviFilter = new AviFileFilter();
			XmlFileFilter xmlFilter = new XmlFileFilter();
			AssFileFilter assFilter = new AssFileFilter();
			SrtFileFilter srtFilter = new SrtFileFilter();

			jFileChooser.addChoosableFileFilter(mp4Filter);// 增加文件过滤器
			jFileChooser.addChoosableFileFilter(mkvFilter);
			jFileChooser.addChoosableFileFilter(aviFilter);
			jFileChooser.addChoosableFileFilter(xmlFilter);
			jFileChooser.addChoosableFileFilter(assFilter);
			jFileChooser.addChoosableFileFilter(srtFilter);

			jFileChooser.setMultiSelectionEnabled(true);// 多选

			int i = jFileChooser.showOpenDialog(null);
			if (i == JFileChooser.APPROVE_OPTION) {

				for (int j = tableCurrentFileNameModel.getRowCount() - 1; j >= 0; j--)
					tableCurrentFileNameModel.removeRow(j);
				files = jFileChooser.getSelectedFiles();

				for (i = 0; i < files.length; i++) {
					System.out.println(files[i].getAbsolutePath());
					Vector<String> vectorCurrent = new Vector<String>();
					vectorCurrent.addElement(files[i].getName());
					vectorCurrent.addElement(String.valueOf(files[i].length()));
					tableCurrentFileNameModel.addRow(vectorCurrent);
				}
				FitTableColumns(tableCurrentFileName);
			} else {
				System.out.println("没有选中文件");
			}
		}

		if ("comfirm".equals(e.getActionCommand())) {
			try {
				// TODO 需判断是否数字序列（可能为字符串），以及批量文件名是否符合规范（是否包含**）
				batchFileName = textNewFileName.getText().toString().trim();
				numberList = textNumberList.getText().toString();
				tempFile = new String[files.length][2];
				Long tempFileLength;
				for (int j = tableNewFileNameModel.getRowCount() - 1; j >= 0; j--)
					tableNewFileNameModel.removeRow(j);
				if ("".equals(batchFileName))
					JOptionPane.showMessageDialog(null, "请输入文件名", "警告",
							JOptionPane.ERROR_MESSAGE);
				else {
					if ("".equals(numberList))
						JOptionPane.showMessageDialog(null, "请输入数字序列", "警告",
								JOptionPane.ERROR_MESSAGE);
					else {
						String[] number = numberList.split("/");
						int length = files.length;
						if (number.length == length)// 未选择文件会出现空指针异常
						{
							for (int i = 0; i < number.length; i++) {
								tempFileLength = files[i].length();
								tempFile[i][0] = batchFileName.replace("**",
										number[i]);
								tempFile[i][1] = tempFileLength.toString();
								System.out.println(files[i].getAbsolutePath());
								Vector<String> vectorNew = new Vector<String>();
								vectorNew.addElement(tempFile[i][0]);
								vectorNew.addElement(tempFile[i][1]);
								tableNewFileNameModel.addRow(vectorNew);
							}
						} else {
							String errorDialog = "请根据列表中文件名输入" + files.length
									+ "个由/分割的数字";
							JOptionPane.showMessageDialog(null, errorDialog,
									"警告", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				FitTableColumns(tableNewFileName);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "出现异常：" + ex.getMessage(),
						"警告", JOptionPane.ERROR_MESSAGE);
			}
			String parentPath;
			parentPath = files[0].getParent();
			for (int i = 0; i < files.length; i++) {
				tempFile[i][0] = parentPath + "\\" + tempFile[i][0];
				files[i].renameTo(new File(tempFile[i][0]));
				System.out.println(tempFile[i][0]);
			}
		}
	}

	// 文件过滤器（mp4，mkv，avi，xml，ass，srt）
	class Mp4FileFilter extends FileFilter {
		public String getDescription() {
			return "*.mp4";
		}

		public boolean accept(File file) {
			String name = file.getName();
			return name.toLowerCase().endsWith(".mp4");
		}
	}

	class MkvFileFilter extends FileFilter {
		public String getDescription() {
			return "*.mkv";
		}

		public boolean accept(File file) {
			String name = file.getName();
			return name.toLowerCase().endsWith(".mkv");
		}
	}

	class AviFileFilter extends FileFilter {
		public String getDescription() {
			return "*.avi";
		}

		public boolean accept(File file) {
			String name = file.getName();
			return name.toLowerCase().endsWith(".avi");
		}
	}

	class XmlFileFilter extends FileFilter {
		public String getDescription() {
			return "*.xml";
		}

		public boolean accept(File file) {
			String name = file.getName();
			return name.toLowerCase().endsWith(".xml");
		}
	}

	class AssFileFilter extends FileFilter {
		public String getDescription() {
			return "*.ass";
		}

		public boolean accept(File file) {
			String name = file.getName();
			return name.toLowerCase().endsWith(".ass");
		}
	}

	class SrtFileFilter extends FileFilter {
		public String getDescription() {
			return "*.srt";
		}

		public boolean accept(File file) {
			String name = file.getName();
			return name.toLowerCase().endsWith(".srt");
		}
	}

}
