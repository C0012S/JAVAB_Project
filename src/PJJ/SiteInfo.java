package PJJ;
/* SiteInfo.java */
public class SiteInfo {
	protected String site_name;
	protected String URL;
	protected String ID;
	protected String PWD;
	
	SiteInfo(String site_name, String URL, String ID, String PWD) {
		this.site_name = site_name;
		this.URL = URL;
		this.ID = ID;
		this.PWD = PWD;
	}
	
	protected void setSiteName(String site) {
		this.site_name = site;
	}
	
	protected String getSiteName() {
		return site_name;
	}
	
	protected void setURL(String url) {
		this.URL = url;
	}
	
	protected String getURL() {
		return URL;
	}
	
	void setID(String id) {
		this.ID = id;
	}
	
	String getID() {
		return ID;
	}
	
	void setPWD(String pwd) {
		this.PWD = pwd;
	}
	
	String getPWD() {
		return PWD;
	}
	
	public int hashCode() {
		return URL.hashCode();
	}
	
	public boolean equals(Object obj) {
		SiteInfo s = (SiteInfo) obj;
		return (s.URL.equals(this.URL));
	}
}