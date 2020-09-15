package PJJ;

/* InfoTableModel.java */
import java.util.*;
import javax.swing.table.AbstractTableModel;
public class InfoTableModel extends AbstractTableModel {
	
	private Vector<String> mColumnNames = new Vector<String>();
	
	private HashSet<SiteInfo> Data = new HashSet<SiteInfo>();
	String id, pwd, memo;
	
	public InfoTableModel(SiteInfoList h) {
		String[] s = {"분류", "선호도", "사이트 이름", "사이트 주소"};
		mColumnNames = new Vector<String>(s.length);
		
		for (int c = 0; c < s.length; c++) {
			mColumnNames.addElement(s[c]);
		}
		
		Data = h.getSiteInfoList();
	}
	
	@Override
	public int getColumnCount() {
		return mColumnNames.size();
	}

	@Override
	public int getRowCount() {
		return Data.size();
	}

	@Override
	public String getColumnName(int column) {
		return (String)mColumnNames.get(column);
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		List<SiteInfo> list = new ArrayList<SiteInfo>();
		list.addAll(Data);
		SiteInfo ss = list.get(row);
		
		id = ss.getID();
		pwd = ss.getPWD();
		memo = ((SiteDetailInfo)ss).getMemo();
		
		switch (column) {
		case 0: return ((SiteDetailInfo)ss).getCategory();
		case 1: return ((SiteDetailInfo)ss).getLike();
		case 2: return ss.getSiteName();
		case 3: return ss.getURL();
		}
		
		return null;
	}
	
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		List<SiteInfo> list = new ArrayList<SiteInfo>(Data);
		SiteInfo ss = list.get(rowIndex);
		
		switch(columnIndex) {
		case 0: 
			((SiteDetailInfo)ss).setCategory((String)aValue); 
			break;
		case 1: 
			((SiteDetailInfo)ss).setLike((String)aValue);
			break;
		case 2:
			ss.setSiteName((String)aValue);
			break;
		case 3: 
			ss.setURL((String)aValue); 
			break;
		}
	}

	public void addRow(Object[] objects) {
		// TODO Auto-generated method stub
		
	}
}