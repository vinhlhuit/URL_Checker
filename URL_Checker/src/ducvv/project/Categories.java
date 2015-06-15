package ducvv.project;

public class Categories {
	
	// Negative

	// Malware_or_viruses
	public static final int MALWARE_OR_VIRUSES = 101;
	// Poor customer experience
	public static final int POOR_EXPERIENCE = 102;
	// Phishing
	public static final int PHISHING = 103;
	// Scam
	public static final int SCAM = 104;
	// Potentially illegal
	public static final int POTENITALLY_ILLEGAL = 105;

	// Questionable

	// Misleading claims or unethical
	public static int MISLEADING_CLAIMS = 201;
	// Privacy risks
	public static int PRIVACY_RICKS = 202;
	// Suspicious
	public static int SUSPICIOUS = 203;
	// Hate, discrimination
	public static int HATE_DISCRIMINATION = 204;
	// Spam
	public static int SPAM = 205;
	// Potentially unwanted programs
	public static int UNWANTED_PROGRAMS = 206;
	// Ads / pop-ups
	public static int ADS_POPUPS = 207;

	// Neutral

	// Online tracking
	public static int ONLINE_TRACKING = 301;
	// Alternative or controversial medicine
	public static int MEDICINE = 302;
	// Opinions, religion, politics
	public static int RELIGION_POLITICS = 303;
	// Other
	public static int OTHER = 304;

	// good site
	public static int GOOD_SITE = 501;

	// child safety

	// Adult content
	public static int ADULT_CONTENT = 401;
	// Incidental nudity
	public static int INCIDENTAL_NUDITY = 402;
	// Gruesome or shocking
	public static int GRUESOME_OR_SHOCKING = 403;
	// Site for kids
	public static int SITE_FOR_KIDS = 404;

	private boolean is101 = false;
	private boolean is102 = false;
	private boolean is103 = false;
	private boolean is104 = false;
	private boolean is105 = false;
	
	private boolean is201 = false;
	private boolean is202 = false;
	private boolean is203 = false;
	private boolean is204 = false;
	private boolean is205 = false;
	private boolean is206 = false;
	private boolean is207 = false;
	
	private boolean is301 = false;
	private boolean is302 = false;
	private boolean is303 = false;
	private boolean is304 = false;
	
	private boolean is401 = false;
	private boolean is402 = false;
	private boolean is403 = false;
	private boolean is404 = false;
	
	private boolean is501 = false;
	
	private int c101 = 0;
	private int c102 = 0;
	private int c103 = 0;
	private int c104 = 0;
	private int c105 = 0;
	
	private int c201 = 0;
	private int c202 = 0;
	private int c203 = 0;
	private int c204 = 0;
	private int c205 = 0;
	private int c206 = 0;
	private int c207 = 0;
	
	private int c301 = 0;
	private int c302 = 0;
	private int c303 = 0;
	private int c304 = 0;
	
	private int c401 = 0;
	private int c402 = 0;
	private int c403 = 0;
	private int c404 = 0;
	
	private int c501 = 0;
	
	
	public boolean get_Is101() {
		return is101;
	}

	public void set_Is101(boolean is101) {
		this.is101 = is101;
	}

	public boolean get_Is102() {
		return is102;
	}

	public void set_Is102(boolean is102) {
		this.is102 = is102;
	}

	public boolean get_Is103() {
		return is103;
	}

	public void set_Is103(boolean is103) {
		this.is103 = is103;
	}

	public boolean get_Is104() {
		return is104;
	}

	public void set_Is104(boolean is104) {
		this.is104 = is104;
	}

	public boolean get_Is105() {
		return is105;
	}

	public void set_Is105(boolean is105) {
		this.is105 = is105;
	}

	public boolean get_Is201() {
		return is201;
	}

	public void set_Is201(boolean is201) {
		this.is201 = is201;
	}

	public boolean get_Is202() {
		return is202;
	}

	public void set_Is202(boolean is202) {
		this.is202 = is202;
	}

	public boolean get_Is203() {
		return is203;
	}

	public void set_Is203(boolean is203) {
		this.is203 = is203;
	}

	public boolean get_Is204() {
		return is204;
	}

	public void set_Is204(boolean is204) {
		this.is204 = is204;
	}

	public boolean get_Is205() {
		return is205;
	}

	public void set_Is205(boolean is205) {
		this.is205 = is205;
	}

	public boolean get_Is206() {
		return is206;
	}

	public void set_Is206(boolean is206) {
		this.is206 = is206;
	}

	public boolean get_Is207() {
		return is207;
	}

	public void set_Is207(boolean is207) {
		this.is207 = is207;
	}

	public boolean get_Is301() {
		return is301;
	}

	public void set_Is301(boolean is301) {
		this.is301 = is301;
	}

	public boolean get_Is302() {
		return is302;
	}

	public void set_Is302(boolean is302) {
		this.is302 = is302;
	}

	public boolean get_Is303() {
		return is303;
	}

	public void set_Is303(boolean is303) {
		this.is303 = is303;
	}

	public boolean get_Is304() {
		return is304;
	}

	public void set_Is304(boolean is304) {
		this.is304 = is304;
	}

	public boolean get_Is401() {
		return is401;
	}

	public void set_Is401(boolean is401) {
		this.is401 = is401;
	}

	public boolean get_Is402() {
		return is402;
	}

	public void set_Is402(boolean is402) {
		this.is402 = is402;
	}

	public boolean get_Is403() {
		return is403;
	}

	public void set_Is403(boolean is403) {
		this.is403 = is403;
	}

	public boolean get_Is404() {
		return is404;
	}

	public void set_Is404(boolean is404) {
		this.is404 = is404;
	}

	public boolean get_Is501() {
		return is501;
	}

	public void set_Is501(boolean is501) {
		this.is501 = is501;
	}

	public int getC101() {
		return c101;
	}

	public void setC101(int c101) {
		this.c101 = c101;
	}

	public int getC102() {
		return c102;
	}

	public void setC102(int c102) {
		this.c102 = c102;
	}

	public int getC103() {
		return c103;
	}

	public void setC103(int c103) {
		this.c103 = c103;
	}

	public int getC104() {
		return c104;
	}

	public void setC104(int c104) {
		this.c104 = c104;
	}

	public int getC105() {
		return c105;
	}

	public void setC105(int c105) {
		this.c105 = c105;
	}

	public int getC201() {
		return c201;
	}

	public void setC201(int c201) {
		this.c201 = c201;
	}

	public int getC202() {
		return c202;
	}

	public void setC202(int c202) {
		this.c202 = c202;
	}

	public int getC203() {
		return c203;
	}

	public void setC203(int c203) {
		this.c203 = c203;
	}

	public int getC204() {
		return c204;
	}

	public void setC204(int c204) {
		this.c204 = c204;
	}

	public int getC205() {
		return c205;
	}

	public void setC205(int c205) {
		this.c205 = c205;
	}

	public int getC206() {
		return c206;
	}

	public void setC206(int c206) {
		this.c206 = c206;
	}

	public int getC207() {
		return c207;
	}

	public void setC207(int c207) {
		this.c207 = c207;
	}

	public int getC301() {
		return c301;
	}

	public void setC301(int c301) {
		this.c301 = c301;
	}

	public int getC302() {
		return c302;
	}

	public void setC302(int c302) {
		this.c302 = c302;
	}

	public int getC303() {
		return c303;
	}

	public void setC303(int c303) {
		this.c303 = c303;
	}

	public int getC304() {
		return c304;
	}

	public void setC304(int c304) {
		this.c304 = c304;
	}

	public int getC401() {
		return c401;
	}

	public void setC401(int c401) {
		this.c401 = c401;
	}

	public int getC402() {
		return c402;
	}

	public void setC402(int c402) {
		this.c402 = c402;
	}

	public int getC403() {
		return c403;
	}

	public void setC403(int c403) {
		this.c403 = c403;
	}

	public int getC404() {
		return c404;
	}

	public void setC404(int c404) {
		this.c404 = c404;
	}

	public int getC501() {
		return c501;
	}

	public void setC501(int c501) {
		this.c501 = c501;
	}

}
