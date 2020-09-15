package PJJ;
/* SiteDetailInfo.java */
public class SiteDetailInfo extends SiteInfo {
	private String category;
	private String memo;
	private String like;
	
	SiteDetailInfo(String site_name, String URL, String ID, String PWD, String category, String memo, String like) {
		super(site_name, URL, ID, PWD);
		this.category = category;
		this.memo = memo;
		this.like = like;
	}
	
	protected void setCategory(String cty) {
		this.category = cty;
	}
	
	protected String getCategory() {
		return category;
	}
	
	void setMemo(String memo) {
		this.memo = memo;
	}
	
	String getMemo() {
		return memo;
	}
	
	protected void setLike(String like) {
		this.like = like;
	}
	
	protected String getLike() {
		return like;
	}
}