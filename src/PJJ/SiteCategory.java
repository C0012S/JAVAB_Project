package PJJ;
/* SiteCategory.java */
public class SiteCategory {
	protected String category_name;
	
	SiteCategory(String category_name) {
		this.category_name = category_name;
	}
	
	void setCategory(String name) {
		this.category_name = name;
	}
	
	String getCategory() {
		return category_name;
	}
}