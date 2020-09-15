package PJJ;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* SiteInfoManager.java */
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableRowSorter;
public class SiteInfoManager extends JFrame{
	
	JCheckBoxMenuItem citem2_3;
	JCheckBoxMenuItem citem3_1;
	JCheckBoxMenuItem citem3_2;
	
	private JTextField tf_sitename;
	private JTextField tf_address;
	private JTextField tf_ID;
	private JTextField tf_password;
	private JTextArea ta_memo;
	
	public JComboBox<String> mCombo1;
	private String str_cb1;
	public JComboBox<String> mCombo2;
	private String str_cb2;
	
	private String sitename;
	private String siteaddr;
	private String ID;
	private String password;
	
	JButton btn_write;
	JButton btn_input;
	JButton btn_edit;
	
	private JTextField tf_ID2;
	private JTextField tf_password2;
	private JCheckBox cb_show;
	
	JComboBox<String> search_Combo;
	JTextField tf_filter;
	
	public String[] cb_cate;
	public String[] cb_pref;
	
	JComboBox<String> category_Combo;
	JComboBox<String> site_Combo;
	
	JLabel lb;
	int sitenum;
	
	private JTable mTable;
	private HashSet<SiteInfo> Data = new HashSet<SiteInfo>();
	SiteInfoList infoList = new SiteInfoList(Data);
	InfoTableModel ifm = new InfoTableModel(infoList) {
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if (citem2_3.isSelected()) {
				return true;
			}
			else {
				return false;
			}
		}
	};
	
	Set<String> set;
	
	public SiteInfoManager() {
		super("����� �����̰� ���� ���ͳ� ���� ����");
		
		createMenu();
		add(createLeftPanel(), BorderLayout.WEST);
		createTab();
		add(createBottomPanel(), BorderLayout.SOUTH);
		
		setSize(1000, 700);
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				LoginDialog dig = new LoginDialog(SiteInfoManager.this);
				
				double x = SiteInfoManager.this.getLocationOnScreen().getX();
				double y = SiteInfoManager.this.getLocationOnScreen().getY();
				
				int wx = SiteInfoManager.this.getWidth();
				int hy = SiteInfoManager.this.getHeight();
				
				double mx = (x + wx) / 2;
				double my = (y + hy) / 2;
				
				double dx = dig.getWidth();
				double dy = dig.getHeight();
				
				double setX = mx - (dx / 2);
				double setY = my - (dy / 2);
				dig.setLocation((int)setX, (int)setY);
				
				String s = null;
				try {
					//�����б�
					File file=new File("LOGIN.txt");
					FileReader fr=null;
					BufferedReader br = null;
					
					String read=null;
					
					fr=new FileReader(file);
					br=new BufferedReader(fr);
					s=br.readLine();
					
					if (fr!=null)fr.close();
					if(br!=null)br.close();
					
					}
					catch(Exception e1) {
						e1.getStackTrace();
					}
				if (s.equals("<<�ڵ� �α��� ������>>")) {
					dig.setVisible(false);
					citem3_1.setSelected(true);
				}
				else dig.setVisible(true);
				
			}
		});
		
		// ǥ �׸� ���� �� ����Ǵ� ������
		mTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = mTable.getSelectedRow();
				tf_sitename.setText((String)mTable.getValueAt(row, 2));
				tf_address.setText((String)mTable.getValueAt(row, 3));
				tf_ID.setText(ifm.id);
				tf_password.setText(ifm.pwd);
				ta_memo.setText(ifm.memo);
				
				mCombo1.setSelectedItem((String)mTable.getValueAt(row, 0));
				mCombo2.setSelectedItem((String)mTable.getValueAt(row, 1));
				
				if (cb_show.isSelected()) {
					tf_ID2.setText(ifm.id);
					tf_password2.setText(ifm.pwd);
				}
				
				else {
					tf_ID2.setText("");
					tf_password2.setText("");
				}
				
				tf_address.setEditable(false);
				
				btn_write.setEnabled(false);
				btn_input.setVisible(false);
				btn_edit.setVisible(true);
			}
		});
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SiteInfoManager.this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				CloseMethod();
			}
		});
		
		setVisible(true);
	}
	
	private void createMenu() {
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);
		
		JMenu menu1 = new JMenu("����(F)");
		menu1.setMnemonic('F');
		JMenu menu2 = new JMenu("����(M)");
		menu2.setMnemonic('M');
		JMenu menu3 = new JMenu("����(S)");
		menu3.setMnemonic('S');
		
		menubar.add(menu1);
		menubar.add(menu2);
		menubar.add(menu3);
		
		JMenuItem item1_1 = new JMenuItem("���� ���Ͽ��� ��������(I)...");
		item1_1.setMnemonic('I');
		JMenuItem item1_2 = new JMenuItem("���� ���Ϸ� ��������(E)...");
		item1_2.setMnemonic('E');
		JMenuItem item1_3 = new JMenuItem("����(S)...");
		item1_3.setMnemonic('S');
		JMenuItem item1_4 = new JMenuItem("�α׾ƿ�(O)");
		item1_4.setMnemonic('O');
		JMenuItem item1_5 = new JMenuItem("����(X)");
		item1_5.setMnemonic('X');
		
		item1_3.addActionListener(SaveListener);
		item1_4.addActionListener(LogoutListener);
		item1_5.addActionListener(CloseListener);

		
		menu1.add(item1_1);
		menu1.add(item1_2);
		menu1.add(item1_3);
		menu1.addSeparator();
		menu1.add(item1_4);
		menu1.add(item1_5);
		
		JMenuItem item2_1 = new JMenuItem("�����(U)");
		item2_1.setMnemonic('U');
		JMenuItem item2_2 = new JMenuItem("����Ʈ �з�(C)");
		item2_2.setMnemonic('C');
		citem2_3 = new JCheckBoxMenuItem("���� ǥ ����(D)");
		citem2_3.setMnemonic('D');
		
		item2_2.addActionListener(CategoryListener);
		
		menu2.add(item2_1);
		menu2.add(item2_2);
		menu2.add(citem2_3);
		
		citem3_1 = new JCheckBoxMenuItem("�ڵ� �α���(L)");
		citem3_1.setMnemonic('L');
		citem3_2 = new JCheckBoxMenuItem("�������� ���� ���� ����ϱ�(V)");
		citem3_2.setMnemonic('V');		
		
		String s = null;
		try {
			//�����б�
			File file=new File("LOGIN.txt");
			FileReader fr=null;
			BufferedReader br = null;
			
			
			String read=null;
			
			fr=new FileReader(file);
			br=new BufferedReader(fr);
			s=br.readLine();
			
			if (fr!=null)fr.close();
			if(br!=null)br.close();
			
			}
			catch(Exception e) {
				e.getStackTrace();
			}
		if (s.equals("<<�ڵ� �α��� ������>>")) {
			citem3_1.setSelected(true);
		}
		else citem3_1.setSelected(false);
		citem3_1.addItemListener(AutoLoginListener);
		citem3_2.addItemListener(StateListener);
		
		menu3.add(citem3_1);
		menu3.add(citem3_2);
	}
/*	
	private ActionListener ExcelSaveListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			HSSFSheet sheet = new HSSFWorkbook(); // �� ���� ����
			HSSFSheet sheet = workbook.createSheet("��Ʈ"); // �� ��Ʈ ����
			HSSFRow row = sheet.createRow(0); // ������ ���� 0 ������ ����
			HSSFCell cell = row.createCell(0); // ���� ���� 0 ������ ����
			
			try {
				FileOutputStream fileoutputstream = new FileOutputStream("ExcelSave.xlsx");
				workbook.write(fileoutputstream);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	};
*/	
	private ActionListener SaveListener = new ActionListener() { // ���� ������
		public void actionPerformed(ActionEvent e) {
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter("TABLE.txt"));
						PrintWriter pw= new PrintWriter(bw,true);

						for (int i=0;i<mTable.getColumnCount();i++) {
							for (int j=0;j<mTable.getRowCount();j++) {
								pw.write((String) mTable.getValueAt(i,j));
								pw.write("\t");
							}
							pw.write("\n");
						}
						pw.close();
					}
					
					catch (Exception e1) {
			           e1.getStackTrace();
					}
					 JOptionPane.showMessageDialog(SiteInfoManager.this, "����Ǿ����ϴ�.", "Message", JOptionPane.INFORMATION_MESSAGE);		}
	};
	
	private ActionListener LogoutListener = new ActionListener() { // �α׾ƿ� ������
		public void actionPerformed(ActionEvent e) {
			Data.removeAll(Data);
			mTable.updateUI();
			
			tf_sitename.setText("");
			tf_address.setText("");
			tf_ID.setText("");
			tf_password.setText("");
			mCombo1.setSelectedIndex(0);
			mCombo2.setSelectedIndex(0);
			ta_memo.setText("");
			
			btn_edit.setVisible(false);
			btn_input.setVisible(true);
			
			btn_write.setEnabled(false);
			btn_input.setEnabled(false);
			
			lb.setText(sitenum + "���� ����Ʈ�� ��ϵǾ� �ֽ��ϴ�.");
		}
	};
	
	private ActionListener CloseListener = new ActionListener() { // ���� ������
		public void actionPerformed(ActionEvent e) {
			CloseMethod();
		}
	};
	
	private void CloseMethod() { // ���� �޼ҵ� -> ���� �� ������ ������ ���� �͵� ������ ����
		int result = JOptionPane.showConfirmDialog(SiteInfoManager.this, "���� �����Ͻðڽ��ϱ�?", "���� Ȯ��", JOptionPane.OK_CANCEL_OPTION);
		
		if (result == JOptionPane.OK_OPTION) {
			System.exit(0);
		}
		else if (result == JOptionPane.CANCEL_OPTION) {
			SiteInfoManager.this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		}
		else {
			SiteInfoManager.this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		}
	}

	private ActionListener CategoryListener = new ActionListener() { // ����Ʈ �з� ������
		public void actionPerformed(ActionEvent e) {
			CategoryManageDialog dig = new CategoryManageDialog(SiteInfoManager.this);
			
			double x = SiteInfoManager.this.getLocationOnScreen().getX();
			double y = SiteInfoManager.this.getLocationOnScreen().getY();
			
			int wx = SiteInfoManager.this.getWidth();
			int hy = SiteInfoManager.this.getHeight();
			
			double mx = (x + wx) / 2;
			double my = (y + hy) / 2;
			
			double dx = dig.getWidth();
			double dy = dig.getHeight();
			
			double setX = mx - (dx / 2);
			double setY = my - (dy / 2);
			dig.setLocation((int)setX, (int)setY);
			
			dig.setVisible(true);
		}
	};
	
//	�ڵ� �α��� �޼ҵ� -> �����ϸ� ���� �� ���� ����ϴ� ����� ���� �𸣰��� -> �ڵ� �α��� ���� ����, ������ ���� ���� ���� ��ȭ���� ǥ�� ���� ����
	private ItemListener AutoLoginListener = new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
			FileWriter f = null;
			LoginDialog dig = new LoginDialog(SiteInfoManager.this);
			
			try {
				f = new FileWriter("LOGIN.txt");
				String line;
				if (citem3_1.isSelected()) {
					line = "true";
				}
				else {
					line = "false";
				}
				f.write(line, 0, line.length());
				
				if (line.equals("true")) {
					dig.setVisible(false);
				}
				
				f.close();
			}
			catch (IOException ioe) {	}
/*			if (citem3_1.isSelected()) {
				LoginDialog dig = new LoginDialog(SiteInfoManager.this);
				
				dig.setVisible(false);
			}	
*/			
		}
	};
	
	private ItemListener StateListener = new ItemListener() { // ���� ���� ���� ���� ����ϴ� ������
		public void itemStateChanged(ItemEvent e) {
			if (citem3_2.isSelected()) {
				cb_show.setSelected(true);
				cb_show.setEnabled(false);
			}
			else {
				cb_show.setSelected(false);
				cb_show.setEnabled(true);
			}
		}
	};

	private JPanel createLeftPanel() {
		JPanel p = new JPanel(new BorderLayout());

		Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		Border etchedTitleBorder = BorderFactory.createTitledBorder(etchedBorder, "�Է� / ����");
		p.setBorder(etchedTitleBorder);
		
		
		JPanel p_1 = new JPanel(new BorderLayout());
		JPanel p_2 = new JPanel();
		p_1.add(LeftPanel1(), BorderLayout.NORTH);
		p_1.add(LeftPanel2(), BorderLayout.CENTER);
		
		p.add(p_1, BorderLayout.NORTH);
		p.add(LeftPanel3(), BorderLayout.CENTER);
		p.add(p_2, BorderLayout.SOUTH);
		
		return p;
	}
	
	private JPanel LeftPanel1() {
		JPanel p1 = new JPanel();
		JPanel p1_1 = new JPanel();
		JPanel p1_2 = new JPanel();
		JPanel p1_3 = new JPanel();
		JPanel p1_4 = new JPanel();
		
		JLabel lb_sitename = new JLabel("����Ʈ��");
		tf_sitename = new JTextField(15);
		
		JLabel lb_address = new JLabel("�ּ�(URL) http://");
		tf_address = new JTextField(10);
		
		JLabel lb_ID = new JLabel("��  ��  ��");
		tf_ID = new JTextField(10);
		
		JLabel lb_password = new JLabel("��й�ȣ");
		tf_password = new JTextField(10);
		
		tf_sitename.addKeyListener(TextButtonListener);
		tf_address.addKeyListener(TextButtonListener);
		tf_ID.addKeyListener(TextButtonListener);
		tf_password.addKeyListener(TextButtonListener);
		
		Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		Border etchedTitleBorder1 = BorderFactory.createTitledBorder(etchedBorder, "�⺻ ����");
		p1.setBorder(etchedTitleBorder1);
		
		p1_1.add(lb_sitename);
		p1_1.add(tf_sitename);
		p1_1.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		p1_2.add(lb_address);
		p1_2.add(tf_address);
		p1_2.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		p1_3.add(lb_ID);
		p1_3.add(tf_ID);
		p1_3.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		p1_4.add(lb_password);
		p1_4.add(tf_password);
		p1_4.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		p1.add(p1_1);
		p1.add(p1_2);
		p1.add(p1_3);
		p1.add(p1_4);

		p1.setLayout(new GridLayout(4, 1));
		
		return p1;
	}
	
	private KeyListener TextButtonListener = new KeyListener() {
		public void keyTyped(KeyEvent e) {
			ButtonEnabled();
		}

		public void keyPressed(KeyEvent e) {
			ButtonEnabled();
		}

		public void keyReleased(KeyEvent e) {
			ButtonEnabled();
		}		
	};

	private JPanel LeftPanel2() {
		JPanel p2 = new JPanel(new GridLayout(2, 1));
		JPanel p2_3 = new JPanel();
		
		JLabel lb_memo = new JLabel("��        ��");
		ta_memo = new JTextArea(5, 15);
		
		Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		Border etchedTitleBorder2 = BorderFactory.createTitledBorder(etchedBorder, "�߰� ����");
		p2.setBorder(etchedTitleBorder2);
		
		p2_3.add(lb_memo);
		p2_3.add(ta_memo);
		p2_3.setLayout(new FlowLayout(FlowLayout.LEFT));

		p2.add(LeftPanel2_1());
		p2.add(p2_3);

		return p2;
	}
	
	private JPanel LeftPanel2_1() {
		JPanel p2_1 = new JPanel();
		JPanel p2_2 = new JPanel();
		JPanel p = new JPanel(new GridLayout(2, 1));
		set = new HashSet<String>();
		
		JLabel lb_category = new JLabel("��        ��");
	//	String[] cb_cate = {"�Ϲ�", "����", "����", "�б�"};
	//	mCombo1 = new JComboBox<String>(cb_cate);		
		mCombo1 = new JComboBox();
		mCombo1.addItem("�Ϲ�");
		str_cb1 = (String) mCombo1.getSelectedItem();
		
		try {
			//�����б�
			File file = new File("OUTPUT.txt");
			FileReader fr = null;
			BufferedReader br = null;
			
			String read = null;
			
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			while ((read = br.readLine()) != null) {
				mCombo1.addItem(read);
			}
			
			if (fr != null)	fr.close();
			if(br != null)	br.close();
			
		}
		catch(Exception e) {
			e.getStackTrace();
		}
		
		mCombo1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb1 = (JComboBox<String>)e.getSource();
				str_cb1 = (String)cb1.getSelectedItem();
			}
		});
		
		JLabel lb_like = new JLabel("��  ȣ  ��");
		String[] cb_pref = {"������", "��", "�١�", "�١١�", "�١١١�", "�١١١١�"};
		mCombo2 = new JComboBox<String>(cb_pref);
		str_cb2 = (String) mCombo2.getSelectedItem();
		
		mCombo2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb2 = (JComboBox)e.getSource();
				str_cb2 = (String)cb2.getSelectedItem();
			}
		});
		
		p2_1.add(lb_category);
		p2_1.add(mCombo1);
		p2_1.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		p2_2.add(lb_like);
		p2_2.add(mCombo2);
		p2_2.setLayout(new FlowLayout(FlowLayout.LEFT));

		p.add(p2_1);
		p.add(p2_2);
		
		return p;
	}
	
	private JPanel LeftPanel3() {
		JPanel p3 = new JPanel();
		
		btn_write = new JButton("���� �ۼ�(N)");
		btn_input = new JButton("�Է�(I)");
		btn_edit = new JButton("����(E)");
		
		btn_write.setEnabled(false);
		btn_input.setEnabled(false);		
		btn_edit.setVisible(false);
		
		p3.add(btn_write);
		p3.add(btn_input);
		p3.add(btn_edit);
		p3.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		btn_write.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tf_sitename.setText("");
				tf_address.setText("");
				tf_ID.setText("");
				tf_password.setText("");
				ta_memo.setText("");
				
				mCombo1.setSelectedIndex(0);
				mCombo2.setSelectedIndex(0);
				
				btn_write.setEnabled(false);
			}
		});
		
		btn_input.addActionListener(InputListener);
		btn_edit.addActionListener(EditListener);
		
		return p3;
	}
	
	private void ButtonEnabled() {
		int lt_sitename = tf_sitename.getText().length();
		int lt_address = tf_address.getText().length();
		int lt_ID = tf_ID.getText().length();
		int lt_password = tf_password.getText().length();
		
		if ((lt_sitename > 0) || (lt_address > 0) || (lt_ID > 0) || (lt_password > 0)) {
			btn_write.setEnabled(true);
		}
		else {
			btn_write.setEnabled(false);
		}
		
		if ((lt_sitename > 0) && (lt_address > 0) && (lt_ID > 0) && (lt_password > 0)) {
			btn_input.setEnabled(true);
		}
		else {
			btn_input.setEnabled(false);
		}
	}
	
	private ActionListener InputListener = new ActionListener() { // �Է� ��ư ������
		public void actionPerformed(ActionEvent e) {
			sitename = tf_sitename.getText();
			siteaddr = tf_address.getText();
			ID = tf_ID.getText();
			password = tf_password.getText();
			//mCombo1, mCombo2�� getText�� ���� �޺��ڽ� �̺�Ʈâ��  str_cb1, str_cb2�� �Է¹޾Ƴ���			

			String memo = ta_memo.getText();
			
			SiteInfo ss = new SiteDetailInfo(sitename, siteaddr, ID, password, str_cb1, memo, str_cb2);
			Data.add(ss);
			mTable.setModel(ifm);
			
			for (Iterator<SiteInfo> it = Data.iterator(); it.hasNext();) {
				SiteInfo s = it.next();
				
				if (s.equals(ss)) {
					s.setSiteName(ss.getSiteName());
					s.setID(ss.getID());
					s.setPWD(ss.getPWD());
					((SiteDetailInfo)s).setCategory(((SiteDetailInfo)ss).getCategory());
					((SiteDetailInfo)s).setLike(((SiteDetailInfo)ss).getLike());
					((SiteDetailInfo)s).setMemo(((SiteDetailInfo)ss).getMemo());
				}
			}
			
			mTable.updateUI();
	
			tf_sitename.setText("");
			tf_address.setText("");
			tf_ID.setText("");
			tf_password.setText("");
			mCombo1.setSelectedIndex(0);
			mCombo2.setSelectedIndex(0);
			ta_memo.setText("");
			
			btn_write.setEnabled(false);
			btn_input.setEnabled(false);
			
			sitenum = mTable.getRowCount();
			lb.setText(sitenum + "���� ����Ʈ�� ��ϵǾ� �ֽ��ϴ�.");
		}
	};

	private ActionListener EditListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			int select = mTable.getSelectedRow();
			mTable.setValueAt(str_cb1, select, 0);
			mTable.setValueAt(str_cb2, select, 1);
			mTable.setValueAt(tf_sitename.getText(), select, 2);
			
			mTable.updateUI();
		}
	};
	void createTab() {
		JTabbedPane tab = new JTabbedPane();
		JButton btn = new JButton("�׷���"); //�ϴ� �ƹ��ų� ����� ��
		
		tab.addTab("����Ʈ ���", TabPanel());
		tab.addTab("�����Ȳ", btn);
		SiteInfoManager.this.add(tab, BorderLayout.CENTER);
	}
	
	JPanel TabPanel() {
		JPanel p = new JPanel(new BorderLayout());
		
		p.add(SearchAndSortPanel(), BorderLayout.NORTH);
		p.add(createTable(),BorderLayout.CENTER);
		p.add(createShowInform(),BorderLayout.SOUTH);
		
		return p;
	}
	
	private JPanel SearchAndSortPanel() {
		JPanel p = new JPanel(new BorderLayout());
		
		Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		Border etchedTitleBorder = BorderFactory.createTitledBorder(etchedBorder, "�˻� / ����");
		p.setBorder(etchedTitleBorder);
		
		p.add(SearchPanel(), BorderLayout.WEST);
		p.add(SortPanel(), BorderLayout.CENTER);
		
		return p;
	}

	private JPanel SearchPanel() {
		JPanel p1 = new JPanel();
		
		Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		Border etchedTitleBorder1 = BorderFactory.createTitledBorder(etchedBorder, "�˻�");
		p1.setBorder(etchedTitleBorder1);

	//	String[] search = {"��ü"};
	//	JComboBox<String> search_Combo = new JComboBox<String>(search);
		/*JComboBox<String>*/ search_Combo = new JComboBox<String>();
		search_Combo.addItem("��ü");
		search_Combo.addItem("�Ϲ�");
		
		try {
			//�����б�
			File file = new File("OUTPUT.txt");
			FileReader fr = null;
			BufferedReader br = null;
			
			String read=null;
			
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			while((read = br.readLine()) != null) {
				search_Combo.addItem(read);
			}
			
			if (fr != null)	fr.close();
			if(br != null)	br.close();
			
		}
		catch(Exception e) {
			e.getStackTrace();
		}

		JLabel lb_filter = new JLabel("����");
		tf_filter = new JTextField(5);
		
		search_Combo.addActionListener(ComboFilterListener);
		tf_filter.addActionListener(FilterListener);
		
		p1.add(search_Combo);
		p1.add(lb_filter);
		p1.add(tf_filter);
		
		return p1;
	}
	
	private ActionListener ComboFilterListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			TableRowSorter<InfoTableModel> rowSorter;
			rowSorter = new TableRowSorter<InfoTableModel>(ifm);
			
			if (search_Combo.getSelectedIndex() == 0) {
				rowSorter.setRowFilter(null);
			}
			else {
				rowSorter.setRowFilter(RowFilter.regexFilter((String)search_Combo.getSelectedItem()));
			}
			
			mTable.setRowSorter(rowSorter);
		}
	};
	
	private ActionListener FilterListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			TableRowSorter<InfoTableModel> rowSorter;
			rowSorter = new TableRowSorter<InfoTableModel>(ifm);
			
			String text = tf_filter.getText();
			
			if (text.trim().length() == 0) {
				rowSorter.setRowFilter(null);
			}
			else {
				rowSorter.setRowFilter(RowFilter.regexFilter(text));
			}			
			
			mTable.setRowSorter(rowSorter);
		}
	};
	
	private JPanel SortPanel() {
		JPanel p2 = new JPanel();
		
		Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		Border etchedTitleBorder2 = BorderFactory.createTitledBorder(etchedBorder, "����");
		p2.setBorder(etchedTitleBorder2);
		
		String[] category1 = {"�з�","��ȣ��","����Ʈ �̸�","����Ʈ �ּ�"};
		category_Combo = new JComboBox<String>(category1);
		
		String[] category2 = {"�з�","��ȣ��","����Ʈ �̸�","����Ʈ �ּ�"};
		site_Combo = new JComboBox<String>(category2);
		
		JButton sort = new JButton("����");
		JButton normal = new JButton("�⺻");

		
		sort.addActionListener(SortListener);
		normal.addActionListener(NormalListener);

		
		p2.add(category_Combo);
		p2.add(site_Combo);
		p2.add(sort);
		p2.add(normal);
		
		return p2;
	}

	private ActionListener SortListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			TableRowSorter<InfoTableModel> sorter;
			sorter = new TableRowSorter<InfoTableModel>(ifm);
			mTable.setRowSorter(sorter);

			java.util.List <RowSorter.SortKey> sortKeys = new java.util.ArrayList<RowSorter.SortKey>();
			
			if (category_Combo.getSelectedIndex() == 0) {
				sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
				switch (site_Combo.getSelectedIndex()) {
					case 0:
						break;
					case 1:
						sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
						break;
					case 2:
						sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
						break;
					case 3:
						sortKeys.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
						break;
				}
			}
			
			if (category_Combo.getSelectedIndex() == 1) {
				sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
				switch (site_Combo.getSelectedIndex()) {
					case 0:
						sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
						break;
					case 1:
						break;
					case 2:
						sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
						break;
					case 3:
						sortKeys.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
						break;
				}
			}
			
			if (category_Combo.getSelectedIndex() == 2) {
				sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
				switch (site_Combo.getSelectedIndex()) {
					case 0:
						sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
						break;
					case 1:
						sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
						break;
					case 2:
						break;
					case 3:
						sortKeys.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
						break;
				}
			}
			
			if (category_Combo.getSelectedIndex() == 3) {
				sortKeys.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
				switch (site_Combo.getSelectedIndex()) {
					case 0:
						sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
						break;
					case 1:
						sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
						break;
					case 2:
						sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
						break;
					case 3:
						break;
				}
			}
			
			sorter.setSortKeys(sortKeys);
		}
	};
	
	private ActionListener NormalListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			TableRowSorter<InfoTableModel> sorter;
			sorter = new TableRowSorter<InfoTableModel>(ifm);
			mTable.setRowSorter(sorter);

			java.util.List <RowSorter.SortKey> sortKeys = new java.util.ArrayList<RowSorter.SortKey>();
			sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
			sorter.setSortKeys(sortKeys);
		}
	};
	
	private JPanel createTable() {
		JPanel p = new JPanel(new BorderLayout());
		
		
		mTable = new JTable(ifm);
		
		try {
			File file = new File("TABLE.txt");
			FileReader fr = null;
			BufferedReader br = null;				
			String read = null;
			
			//BufferedReader�� ����Ѵ�.
			fr = new FileReader(file);
			br = new BufferedReader(fr);

			//BufferedReader(fr)�� �̿��Ͽ� 1���ξ� �о�ͼ� �����.
			int row=0;
			while((read = br.readLine()) != null) {
				//mTable.addRow();
				String value[] = read.split("\t");
				
//				ifm.addRow(new Object[] {value[0],value[2],value[3],value[3]});
//				for (int i=0;i<value.length;i++) {
//					mTable.setValueAt(value[i],row, i);
//					row+=1;
//				}
			}		

			if (fr != null)	fr.close();
			if(br!=null)	br.close();		
		}

		catch(Exception e) {
			e.getStackTrace();
		}
		
		
		p.add(new JScrollPane(mTable), BorderLayout.CENTER);
		
		return p;
	}
	
	private JPanel createShowInform() {
		JPanel p=new JPanel(new BorderLayout());
		JPanel p_1=new JPanel(new FlowLayout());
		
		JButton btn_delete = new JButton("����");
		JButton btn_repaint = new JButton("���ΰ�ħ");


		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = mTable.getSelectedRow();
				SiteInfo sf = new SiteDetailInfo((String)mTable.getValueAt(row, 2), (String)mTable.getValueAt(row, 3), ifm.id, ifm.pwd, (String)mTable.getValueAt(row, 0), ifm.memo, (String)mTable.getValueAt(row,  1));
				infoList.delete(sf);		
				mTable.updateUI();
			//	System.out.println(mTable.getSelectedRow() + " delete");
			}
		});
		btn_repaint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				java.awt.EventQueue.invokeLater(new Runnable() { 
				    public void run() { 
				    	SiteInfoManager frame = new SiteInfoManager(); 
				     frame.setVisible(true); 
				    } 
				}); 
			}
			
		});
				
		
		p.add(Inform_panel(), BorderLayout.WEST);
		p_1.add(btn_delete, FlowLayout.LEFT);
		p_1.add(btn_repaint, FlowLayout.LEFT);
		p.add(p_1,BorderLayout.EAST);
		
		return p;
	}
	
	private JPanel Inform_panel() {
		JPanel p = new JPanel();
		
		cb_show = new JCheckBox("�������� ����");
		JLabel lb_ID2 = new JLabel("  ���̵�");
		tf_ID2 = new JTextField(8);
		JLabel lb_password2 = new JLabel("��й�ȣ");
		tf_password2 = new JTextField(8);
		
		cb_show.addItemListener(InfoShowListener);
		
		p.add(cb_show);
		p.add(lb_ID2);
		p.add(tf_ID2);
		p.add(lb_password2);
		p.add(tf_password2);
		
		tf_ID2.setEditable(false);
		tf_password2.setEditable(false);
		
		tf_ID2.setOpaque(false);
		tf_password2.setOpaque(false);

		return p;
	}
	
	private ItemListener InfoShowListener = new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
			if (cb_show.isSelected()) {
				Font font = new Font("�޸� ����T",Font.BOLD,13); 
				tf_ID2.setFont(font);
				tf_password2.setFont(font);
								
				tf_ID2.setText(tf_ID.getText());
				tf_password2.setText(tf_password.getText());
			//	tf_ID2.setText(ifm.id);
			//	tf_password2.setText(ifm.pwd);
			}
			
			else {
				tf_ID2.setText("");
				tf_password2.setText("");
			}
		}
	};

	private JPanel createBottomPanel() {
		JPanel p = new JPanel();
		lb = new JLabel(sitenum + "���� ����Ʈ�� ��ϵǾ� �ֽ��ϴ�.");
		
		p.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		p.add(lb);
		p.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		return p;
	}
}

class LoginDialog extends JDialog {

	private JButton btn_close;
	private int login_num = 0;
	private String password;

	LoginDialog(JFrame f) {
		super(f, "����� �α���", true);
		
		buildGUI();
		
		setSize(100, 100);
		pack();
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			}
		});
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	private void buildGUI() {
		JPanel p = new JPanel(new GridLayout(2, 1));
		Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		Border etchedTitleBorder = BorderFactory.createTitledBorder(etchedBorder, "�ȳ��ϼ���?");
		p.setBorder(etchedTitleBorder);
		
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		
		JLabel lb = new JLabel("��й�ȣ");
		JTextField tf = new JTextField(10);
		JButton btn_login = new JButton("�α���");
		btn_close = new JButton("����");
			
		p1.add(lb);
		p1.add(tf);
		
		btn_login.addActionListener(new ActionListener() {
			@SuppressWarnings("unlikely-arg-type")
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				
				try {
					File file = new File("password.txt");
					FileReader fr = null;
					BufferedReader br = null;	
					String read;
					
					//BufferedReader�� ����Ѵ�.
					fr = new FileReader(file);
					br = new BufferedReader(fr);

					//BufferedReader(fr)�� �̿��Ͽ� 1���ξ� �о�ͼ� �����.
					while((read = br.readLine()) != null) {
						password=read;
					}				
					
					if (fr != null)	fr.close();
					if(br!=null)	br.close();		
				}

				catch(Exception e1) {
					e1.getStackTrace();
				}
				if (tf.getText().equals(password)) {
					showMessage("�α��� �Ǿ����ϴ�.");
					setVisible(false);
				}
				else {
					login_num = login_num + 1;
					warningshowMessage("��й�ȣ�� �ٽ� �Է����ּ���.\n" + "��ġ���� �ʽ��ϴ�. (" + login_num + " /5)");
					tf.setText("");
					if (login_num == 5) {
						warningshowMessage("�α����� �� �����ϴ�.");
						System.exit(0);
					}
				} 
			}
		});
		
		btn_close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				 System.exit(0);
			}
		});
		
		p2.add(btn_login);
		p2.add(btn_close);

		p.add(p1);
		p.add(p2);
		
		add(p);
	}
	
	public void warningshowMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "���", JOptionPane.ERROR_MESSAGE);		
	}
		
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "�α���", JOptionPane.INFORMATION_MESSAGE);		
	}
}

class CategoryManageDialog extends JDialog {
	
	JList<String> list;
	DefaultListModel<String> model;
	JTextField tfname;
	String temp;
	Set<String> beforeset;
	HashSet<String> set = new HashSet<String>();

		
	CategoryManageDialog(JFrame f) {
		super(f, "����Ʈ �з� ����", true);
		
		buildGUI();
		
		setSize(100, 100);
		pack();
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	private void buildGUI() {
		JPanel p = new JPanel(new BorderLayout());

		p.add(LeftCategoryPanel(), BorderLayout.WEST);
		p.add(RightCategoryPanel(), BorderLayout.CENTER);
		
		add(p);
	}
	
	private JPanel LeftCategoryPanel() {
		JPanel pLeft = new JPanel();
		Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		Border etchedTitleBorder1 = BorderFactory.createTitledBorder(etchedBorder, "��� �׸�");
		pLeft.setBorder(etchedTitleBorder1);

		pLeft.add(CategoryListPanel());
		
		return pLeft;
	}
	
	private JPanel CategoryListPanel() {
		JPanel plist = new JPanel();
		
		HashSet<String> beforeset = new HashSet<String>();

	//	String[] str_category = {"����", "����", "�б�"};
		list = new JList<String>(new DefaultListModel<String>());
	//	model = (DefaultListModel<String>)list.getModel();
		//model.addElement(str_category[0]);
		model = (DefaultListModel<String>)list.getModel();	

		// ���� �б�
		try {
			File file = new File("OUTPUT.txt");
			FileReader fr = null;
			BufferedReader br = null;				
			String read = null;
			
			//BufferedReader�� ����Ѵ�.
			fr = new FileReader(file);
			br = new BufferedReader(fr);

			//BufferedReader(fr)�� �̿��Ͽ� 1���ξ� �о�ͼ� �����.
			while((read = br.readLine()) != null) {
				beforeset.add(read);
			}		

			for (Iterator i = beforeset.iterator(); i.hasNext();) {
				String c = (String) i.next();
				model.addElement(c);
			}			
			
			if (fr != null)	fr.close();
			if(br!=null)	br.close();		
		}

		catch(Exception e) {
			e.getStackTrace();
		}
	
		plist.add(new JScrollPane(list));
		set.addAll(beforeset);

		return plist;
	}
	
	private JPanel RightCategoryPanel() {
		JPanel pRight = new JPanel(new BorderLayout());
		
		pRight.add(EditPanel(), BorderLayout.CENTER);
		pRight.add(NewAndDoneButtonPanel(), BorderLayout.SOUTH);
		
		return pRight;
	}
	
	private JPanel EditPanel() {
		JPanel pEdit = new JPanel();//new GridLayout(2, 1));
		Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		Border etchedTitleBorder2 = BorderFactory.createTitledBorder(etchedBorder, "���� ����");
		pEdit.setBorder(etchedTitleBorder2);

		pEdit.add(EditElementPanel(), BorderLayout.NORTH);
		
		return pEdit;
	}
	
	private JPanel EditElementPanel() {
		JPanel pElement = new JPanel(new GridLayout(2, 1));
		JPanel pElement_1 = new JPanel();
		JPanel pElement_2 = new JPanel();
		
		JLabel name = new JLabel("�׸� �̸�");
		tfname = new JTextField(12);
		
		pElement_1.add(name);
		pElement_1.add(tfname);
		
		JButton btn_delete = new JButton("����(D)");
		btn_delete.setMnemonic('D');
		btn_delete.addActionListener(DeleteList);
		
		JButton btn_add = new JButton("�߰�(A)");
		btn_add.setMnemonic('A');
		btn_add.addActionListener(AddList);
		
		pElement_2.add(btn_delete);
		pElement_2.add(btn_add);
		
		pElement.add(pElement_1);
		pElement.add(pElement_2);
		
		return pElement;
	}
	
	private ActionListener AddList = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		//	model.addElement(tfname.getText());
			String category = tfname.getText();
			set.add(category);
			
			model.addElement(category);
//			for (Iterator i = set.iterator();i.hasNext();) {
//				String c = (String) i.next();
//				model.addElement(c);
			tfname.setText("");
//				
//			}
		}
	};
	
	private ActionListener DeleteList = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			set.remove(list.getSelectedValue());
			model.removeElement(list.getSelectedValue());
		}
	};
	
	private JPanel NewAndDoneButtonPanel() {
		JPanel pNDB = new JPanel();
		
		JButton btn_new = new JButton("�ű�(N)");
		btn_new.setMnemonic('N');
		btn_new.addActionListener(NewButton);
		
		JButton btn_done = new JButton("�Ϸ�(D)");
		btn_done.setMnemonic('D');
		btn_done.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				FileWriter fw, fw_append;
				String str="";		
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter("OUTPUT.txt"));
						PrintWriter pw= new PrintWriter(bw,true);

						Iterator it = set.iterator();
						while(it.hasNext()) {
							pw.write(it.next()+"\n");
						}
						pw.close();
					}
					
					catch (Exception e1) {
			           e1.getStackTrace();
					}
					setVisible(false);
				}
			
		});
		
		pNDB.add(btn_new);
		pNDB.add(btn_done);
		
		
		return pNDB;
	}
	
	private ActionListener NewButton = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			tfname.setText("");
		}
	};
}