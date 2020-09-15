package PJJ;
/* SiteInfoList.java */
import java.util.*;

public class SiteInfoList {
	HashSet<SiteInfo> infos = new HashSet<SiteInfo>();
	
	public SiteInfoList(HashSet<SiteInfo> h) {
		infos = h;
	}
	
	HashSet getSiteInfoList() {
		return infos;
	}

	public void delete(SiteInfo s) {
		for (Iterator<SiteInfo> it = infos.iterator(); it.hasNext();) {
			SiteInfo s1 = it.next();
			
			if (s1.equals(s)) {
				it.remove();
			}
	}
}
}