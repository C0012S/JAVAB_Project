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
		super("상희와 명현이가 만든 인터넷 계정 관리");
		
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
					//파일읽기
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
				if (s.equals("<<자동 로그인 설정함>>")) {
					dig.setVisible(false);
					citem3_1.setSelected(true);
				}
				else dig.setVisible(true);
				
			}
		});
		
		// 표 항목 선택 시 실행되는 리스너
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
		
		JMenu menu1 = new JMenu("파일(F)");
		menu1.setMnemonic('F');
		JMenu menu2 = new JMenu("관리(M)");
		menu2.setMnemonic('M');
		JMenu menu3 = new JMenu("설정(S)");
		menu3.setMnemonic('S');
		
		menubar.add(menu1);
		menubar.add(menu2);
		menubar.add(menu3);
		
		JMenuItem item1_1 = new JMenuItem("엑셀 파일에서 가져오기(I)...");
		item1_1.setMnemonic('I');
		JMenuItem item1_2 = new JMenuItem("엑셀 파일로 내보내기(E)...");
		item1_2.setMnemonic('E');
		JMenuItem item1_3 = new JMenuItem("저장(S)...");
		item1_3.setMnemonic('S');
		JMenuItem item1_4 = new JMenuItem("로그아웃(O)");
		item1_4.setMnemonic('O');
		JMenuItem item1_5 = new JMenuItem("종료(X)");
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
		
		JMenuItem item2_1 = new JMenuItem("사용자(U)");
		item2_1.setMnemonic('U');
		JMenuItem item2_2 = new JMenuItem("사이트 분류(C)");
		item2_2.setMnemonic('C');
		citem2_3 = new JCheckBoxMenuItem("직접 표 수정(D)");
		citem2_3.setMnemonic('D');
		
		item2_2.addActionListener(CategoryListener);
		
		menu2.add(item2_1);
		menu2.add(item2_2);
		menu2.add(citem2_3);
		
		citem3_1 = new JCheckBoxMenuItem("자동 로그인(L)");
		citem3_1.setMnemonic('L');
		citem3_2 = new JCheckBoxMenuItem("계정정보 보기 상태 기억하기(V)");
		citem3_2.setMnemonic('V');		
		
		String s = null;
		try {
			//파일읽기
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
		if (s.equals("<<자동 로그인 설정함>>")) {
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
			HSSFSheet sheet = new HSSFWorkbook(); // 새 엑셀 생성
			HSSFSheet sheet = workbook.createSheet("시트"); // 새 시트 생성
			HSSFRow row = sheet.createRow(0); // 엑셀의 행은 0 번부터 시작
			HSSFCell cell = row.createCell(0); // 행의 셀은 0 번부터 시작
			
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
	private ActionListener SaveListener = new ActionListener() { // 저장 리스너
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
					 JOptionPane.showMessageDialog(SiteInfoManager.this, "저장되었습니다.", "Message", JOptionPane.INFORMATION_MESSAGE);		}
	};
	
	private ActionListener LogoutListener = new ActionListener() { // 로그아웃 리스너
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
			
			lb.setText(sitenum + "개의 사이트가 등록되어 있습니다.");
		}
	};
	
	private ActionListener CloseListener = new ActionListener() { // 종료 리스너
		public void actionPerformed(ActionEvent e) {
			CloseMethod();
		}
	};
	
	private void CloseMethod() { // 종료 메소드 -> 저장 후 종료할 것인지 묻는 것도 생각해 보기
		int result = JOptionPane.showConfirmDialog(SiteInfoManager.this, "정말 종료하시겠습니까?", "종료 확인", JOptionPane.OK_CANCEL_OPTION);
		
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

	private ActionListener CategoryListener = new ActionListener() { // 사이트 분류 리스너
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
	
//	자동 로그인 메소드 -> 종료하면 종료 전 상태 기억하는 방법이 뭔지 모르겠음 -> 자동 로그인 설정 여부, 파일의 저장 값에 따라 대화상자 표시 여부 결정
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
	
	private ItemListener StateListener = new ItemListener() { // 계정 정보 보기 상태 기억하는 리스너
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
		Border etchedTitleBorder = BorderFactory.createTitledBorder(etchedBorder, "입력 / 수정");
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
		
		JLabel lb_sitename = new JLabel("사이트명");
		tf_sitename = new JTextField(15);
		
		JLabel lb_address = new JLabel("주소(URL) http://");
		tf_address = new JTextField(10);
		
		JLabel lb_ID = new JLabel("아  이  디");
		tf_ID = new JTextField(10);
		
		JLabel lb_password = new JLabel("비밀번호");
		tf_password = new JTextField(10);
		
		tf_sitename.addKeyListener(TextButtonListener);
		tf_address.addKeyListener(TextButtonListener);
		tf_ID.addKeyListener(TextButtonListener);
		tf_password.addKeyListener(TextButtonListener);
		
		Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		Border etchedTitleBorder1 = BorderFactory.createTitledBorder(etchedBorder, "기본 정보");
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
		
		JLabel lb_memo = new JLabel("메        모");
		ta_memo = new JTextArea(5, 15);
		
		Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		Border etchedTitleBorder2 = BorderFactory.createTitledBorder(etchedBorder, "추가 정보");
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
		
		JLabel lb_category = new JLabel("분        류");
	//	String[] cb_cate = {"일반", "정보", "포털", "학교"};
	//	mCombo1 = new JComboBox<String>(cb_cate);		
		mCombo1 = new JComboBox();
		mCombo1.addItem("일반");
		str_cb1 = (String) mCombo1.getSelectedItem();
		
		try {
			//파일읽기
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
		
		JLabel lb_like = new JLabel("선  호  도");
		String[] cb_pref = {"미지정", "☆", "☆☆", "☆☆☆", "☆☆☆☆", "☆☆☆☆☆"};
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
		
		btn_write = new JButton("새로 작성(N)");
		btn_input = new JButton("입력(I)");
		btn_edit = new JButton("수정(E)");
		
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
	
	private ActionListener InputListener = new ActionListener() { // 입력 버튼 리스너
		public void actionPerformed(ActionEvent e) {
			sitename = tf_sitename.getText();
			siteaddr = tf_address.getText();
			ID = tf_ID.getText();
			password = tf_password.getText();
			//mCombo1, mCombo2의 getText는 각각 콤보박스 이벤트창에  str_cb1, str_cb2로 입력받아놓음			

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
			lb.setText(sitenum + "개의 사이트가 등록되어 있습니다.");
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
		JButton btn = new JButton("그래프"); //일단 아무거나 적어둔 거
		
		tab.addTab("사이트 목록", TabPanel());
		tab.addTab("등록현황", btn);
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
		Border etchedTitleBorder = BorderFactory.createTitledBorder(etchedBorder, "검색 / 정렬");
		p.setBorder(etchedTitleBorder);
		
		p.add(SearchPanel(), BorderLayout.WEST);
		p.add(SortPanel(), BorderLayout.CENTER);
		
		return p;
	}

	private JPanel SearchPanel() {
		JPanel p1 = new JPanel();
		
		Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		Border etchedTitleBorder1 = BorderFactory.createTitledBorder(etchedBorder, "검색");
		p1.setBorder(etchedTitleBorder1);

	//	String[] search = {"전체"};
	//	JComboBox<String> search_Combo = new JComboBox<String>(search);
		/*JComboBox<String>*/ search_Combo = new JComboBox<String>();
		search_Combo.addItem("전체");
		search_Combo.addItem("일반");
		
		try {
			//파일읽기
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

		JLabel lb_filter = new JLabel("필터");
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
		Border etchedTitleBorder2 = BorderFactory.createTitledBorder(etchedBorder, "정렬");
		p2.setBorder(etchedTitleBorder2);
		
		String[] category1 = {"분류","선호도","사이트 이름","사이트 주소"};
		category_Combo = new JComboBox<String>(category1);
		
		String[] category2 = {"분류","선호도","사이트 이름","사이트 주소"};
		site_Combo = new JComboBox<String>(category2);
		
		JButton sort = new JButton("정렬");
		JButton normal = new JButton("기본");

		
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
			
			//BufferedReader를 사용한다.
			fr = new FileReader(file);
			br = new BufferedReader(fr);

			//BufferedReader(fr)를 이용하여 1라인씩 읽어와서 출력함.
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
		
		JButton btn_delete = new JButton("삭제");
		JButton btn_repaint = new JButton("새로고침");


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
		
		cb_show = new JCheckBox("계정정보 보기");
		JLabel lb_ID2 = new JLabel("  아이디");
		tf_ID2 = new JTextField(8);
		JLabel lb_password2 = new JLabel("비밀번호");
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
				Font font = new Font("휴먼 모음T",Font.BOLD,13); 
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
		lb = new JLabel(sitenum + "개의 사이트가 등록되어 있습니다.");
		
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
		super(f, "사용자 로그인", true);
		
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
		Border etchedTitleBorder = BorderFactory.createTitledBorder(etchedBorder, "안녕하세요?");
		p.setBorder(etchedTitleBorder);
		
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		
		JLabel lb = new JLabel("비밀번호");
		JTextField tf = new JTextField(10);
		JButton btn_login = new JButton("로그인");
		btn_close = new JButton("종료");
			
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
					
					//BufferedReader를 사용한다.
					fr = new FileReader(file);
					br = new BufferedReader(fr);

					//BufferedReader(fr)를 이용하여 1라인씩 읽어와서 출력함.
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
					showMessage("로그인 되었습니다.");
					setVisible(false);
				}
				else {
					login_num = login_num + 1;
					warningshowMessage("비밀번호를 다시 입력해주세요.\n" + "일치하지 않습니다. (" + login_num + " /5)");
					tf.setText("");
					if (login_num == 5) {
						warningshowMessage("로그인할 수 없습니다.");
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
		JOptionPane.showMessageDialog(this, message, "경고", JOptionPane.ERROR_MESSAGE);		
	}
		
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "로그인", JOptionPane.INFORMATION_MESSAGE);		
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
		super(f, "사이트 분류 관리", true);
		
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
		Border etchedTitleBorder1 = BorderFactory.createTitledBorder(etchedBorder, "등록 항목");
		pLeft.setBorder(etchedTitleBorder1);

		pLeft.add(CategoryListPanel());
		
		return pLeft;
	}
	
	private JPanel CategoryListPanel() {
		JPanel plist = new JPanel();
		
		HashSet<String> beforeset = new HashSet<String>();

	//	String[] str_category = {"정보", "포털", "학교"};
		list = new JList<String>(new DefaultListModel<String>());
	//	model = (DefaultListModel<String>)list.getModel();
		//model.addElement(str_category[0]);
		model = (DefaultListModel<String>)list.getModel();	

		// 파일 읽기
		try {
			File file = new File("OUTPUT.txt");
			FileReader fr = null;
			BufferedReader br = null;				
			String read = null;
			
			//BufferedReader를 사용한다.
			fr = new FileReader(file);
			br = new BufferedReader(fr);

			//BufferedReader(fr)를 이용하여 1라인씩 읽어와서 출력함.
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
		Border etchedTitleBorder2 = BorderFactory.createTitledBorder(etchedBorder, "편집 내용");
		pEdit.setBorder(etchedTitleBorder2);

		pEdit.add(EditElementPanel(), BorderLayout.NORTH);
		
		return pEdit;
	}
	
	private JPanel EditElementPanel() {
		JPanel pElement = new JPanel(new GridLayout(2, 1));
		JPanel pElement_1 = new JPanel();
		JPanel pElement_2 = new JPanel();
		
		JLabel name = new JLabel("항목 이름");
		tfname = new JTextField(12);
		
		pElement_1.add(name);
		pElement_1.add(tfname);
		
		JButton btn_delete = new JButton("삭제(D)");
		btn_delete.setMnemonic('D');
		btn_delete.addActionListener(DeleteList);
		
		JButton btn_add = new JButton("추가(A)");
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
		
		JButton btn_new = new JButton("신규(N)");
		btn_new.setMnemonic('N');
		btn_new.addActionListener(NewButton);
		
		JButton btn_done = new JButton("완료(D)");
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