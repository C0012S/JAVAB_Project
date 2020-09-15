package PJJ;
/* SiteCategoryList.java */
import java.util.*;

public class SiteCategoryList {
	HashSet<SiteCategory> items = new HashSet<SiteCategory>();
	SiteCategory st;
	
	public SiteCategoryList(HashSet<SiteCategory> h) {
		items.addAll(h);
	}

	void setSiteCategoryList(HashSet<SiteCategory> items) {
		this.items = items;
	}
	
	HashSet getSiteCategoryList() {
		return items;
	}
}