package ducvv.project;

import org.json.JSONException;
import org.json.JSONObject;

public class SiteInfo {

	// constant
	// threshold
	public static final int THRESHOLD_CONFIDENCE_CATEGORIES = 5;

	public static final int THRESHOLD_CONFIDENCE_REPUTATION = 5;
	// confidence value just 5 for safe
	public static final int THRESHOLD_CHILD_SAFETY = 5;
	// rate site
	public static final int THRESHOLD_EXCELLENT_SITE = 80;

	public static final int THRESHOLD_GOOD_SITE = 60;

	public static final int THRESHOLD_UNSATISFACTORY_SITE = 40;

	public static final int THRESHOLD_POOR_SITE = 20;

	public static final int THRESHOLD_VERYPOOR_SITE = 0;

	private String url; // url of website
	private Result result; // contain responseCode and string of json result
	private int r0_trustworthiness_value; // reputation for "0"
	private int r4_child_safety_value; // reputation for "4"
	private int c0_trustworthiness_value; // confidence for "0"
	private int c4_child_safety_value; // confidence for "4"
	private JSONObject jObject; // jsonObject contain information about website
	public Categories categories; // contain information about categories
									// received from WOT

	// flag check if result contain zero/ four
	private boolean zero = false;
	private boolean four = false;

	private int trustworthiness_stt = -1; // value of reputation of
											// trustworthiness if it good
	private int child_safety_stt = -1;

	// constant reputation
	// Excellent
	public static final int EXCELLENT = 1;
	// Good
	public static final int GOOD = 2;
	// Unsatisfactory
	public static final int UNSATISFACTORY = 3;
	// Poor
	public static final int POOR = 4;
	// Very poor
	public static final int VERY_POOR = 5;

	public static final int UNKNOWN = 6;

	public SiteInfo() {
		this.categories = new Categories();
	}

	public void set_url(String url) {
		if (url != null) {
			this.url = url;
		}
	}

	public String get_url() {
		return this.url;
	}

	public void set_result(Result result) {
		this.result.set_responseCode(result.get_responseCode());
		this.result.set_result(result.get_result());
	}

	public Result get_Result() {
		Result a = new Result();
		a.set_responseCode(this.result.get_responseCode());
		a.set_result(this.result.get_result());
		return a;
	}

	public int set_json(String result) {
		try {
			this.jObject = new JSONObject(result);
			return 1;
		} catch (JSONException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int set_json() {
		try {
			this.jObject = new JSONObject(this.url);
			return 1;
		} catch (JSONException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public JSONObject get_json() {
		return this.jObject;
	}

	public void set_r0_trustworthiness_value(int value) {
		if (value >= 0 && value <= 100) {
			this.r0_trustworthiness_value = value;
		} else {
			this.r0_trustworthiness_value = -1;
		}
	}

	public int get_r0_trustworthiness_value() {
		return this.r0_trustworthiness_value;
	}

	public void set_r4_child_safety_value(int value) {
		if (value >= 0 && value <= 100) {
			this.r4_child_safety_value = value;
		} else {
			this.r4_child_safety_value = -1;
		}
	}

	public int get_r4_child_safety_value() {
		return this.r4_child_safety_value;
	}

	public void set_c0_trustworthiness_value(int value) {
		if (value >= 0 && value <= 100) {
			this.c0_trustworthiness_value = value;
		} else {
			this.c0_trustworthiness_value = -1;
		}
	}

	public int get_c0_trustworthiness_value() {
		return this.c0_trustworthiness_value;
	}

	public void set_c4_child_safety_value(int value) {
		if (value >= 0 && value <= 100) {
			this.c4_child_safety_value = value;
		} else {
			this.c4_child_safety_value = -1;
		}
	}

	public int get_c4_child_safety_value() {
		return this.c4_child_safety_value;
	}

	public Categories get_Categories() {
		return categories;
	}

	public void set_Categories(Categories categories) {
		this.categories = categories;
	}

	public boolean getZero() {
		return zero;
	}

	public void setZero(boolean zero) {
		this.zero = zero;
	}

	public boolean getFour() {
		return four;
	}

	public void setFour(boolean four) {
		this.four = four;
	}

	public int gettrustworthiness_stt() {
		return trustworthiness_stt;
	}

	public void settrustworthiness_stt(int trustworthiness_stt) {
		this.trustworthiness_stt = trustworthiness_stt;
	}

	public int getChild_safety_stt() {
		return child_safety_stt;
	}

	public void setChild_safety_stt(int child_safety_stt) {
		this.child_safety_stt = child_safety_stt;
	}

	// handle "0" (Trustworthiness) and "4" (Child safety)
	// "0" Trustworthiness : “How much do you trust this site?”
	// "4" : Child safety : “How suitable is this site for children?”
	private int get_zero_and_four() {
		if (this.jObject.length() == 0) {
			// nothing to handle
			// this site need to check in google API
			return -1;
		}
		// have data
		else {
			String strvalue = null; // string contain json result
			int index = 0; // index
			int first = 0; // reputation
			int second = 0; // confidence
			try {
				// handle "0"
				if (this.jObject.has("0")) {
					strvalue = this.jObject.getString("0");
					try {
						// remove character [
						strvalue = strvalue.substring(1);
						// get index of character ,
						index = strvalue.indexOf(',');
						// get first value (r0)
						first = Integer.parseInt(strvalue.substring(0, index));
						// get second value c0
						second = Integer.parseInt(strvalue.substring(index + 1,
								strvalue.length() - 1));
						// set value r0 and c0
						this.set_r0_trustworthiness_value(first);
						this.set_c0_trustworthiness_value(second);
						this.setZero(true); // result contain "0"
					} catch (NumberFormatException e) {
					}

				} else {
					this.set_r0_trustworthiness_value(-1);
					this.set_c0_trustworthiness_value(-1);
					this.setZero(false); // result contain "0"
				}
				// end handle 0
				// handle "4"
				if (this.jObject.has("4")) {
					strvalue = this.jObject.getString("4");
					try {
						// remove character [
						strvalue = strvalue.substring(1);
						// get index of character ,
						index = strvalue.indexOf(',');
						// get first value (r0)
						first = Integer.parseInt(strvalue.substring(0, index));
						// get second value c0
						second = Integer.parseInt(strvalue.substring(index + 1,
								strvalue.length() - 1));
						// set value r4 and c4
						this.set_r4_child_safety_value(first);
						this.set_c4_child_safety_value(second);
						this.setFour(true); // result contain "4"
					} catch (NumberFormatException e) {
					}
				} else {
					this.set_r4_child_safety_value(-1);
					this.set_c4_child_safety_value(-1);
					this.setFour(false); // result contain "4"
				}
				// end handle 4

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// end try catch
			return 1;
		}

	}

	// handle categories
	public void handle_categories() {
		if (this.jObject.length() == 0) {
			// nothing to handle
			// this site need to check in google API
		}
		// have data
		else {
			String confidence;
			JSONObject temp = null;
			try {
				if (this.jObject.has("categories")) {
					temp = this.jObject.getJSONObject("categories");
					// virus, malware
					if (temp.has("101") == true) {
						confidence = temp.getString("101");
						this.categories.setC101(Integer.parseInt(confidence));
						if (this.categories.getC101() >= SiteInfo.THRESHOLD_CONFIDENCE_CATEGORIES
								|| this.result.getGoogleAPI_responseCode()
										.equals("200")) {
							this.categories.set_Is101(true);
						} else
							this.categories.set_Is101(false);
					} else
						this.categories.set_Is101(false);

					if (temp.has("102") == true) {
						confidence = temp.getString("102");
						this.categories.setC102(Integer.parseInt(confidence));
						if (this.categories.getC102() >= SiteInfo.THRESHOLD_CONFIDENCE_CATEGORIES) {
							this.categories.set_Is102(true);
						} else
							this.categories.set_Is102(false);
					} else
						this.categories.set_Is102(false);

					if (temp.has("103") == true) {
						confidence = temp.getString("103");
						this.categories.setC103(Integer.parseInt(confidence));
						if (this.categories.getC103() >= SiteInfo.THRESHOLD_CONFIDENCE_CATEGORIES) {
							this.categories.set_Is103(true);
						} else
							this.categories.set_Is103(false);
					} else
						this.categories.set_Is103(false);

					if (temp.has("104") == true) {
						confidence = temp.getString("104");
						this.categories.setC104(Integer.parseInt(confidence));
						if (this.categories.getC104() >= SiteInfo.THRESHOLD_CONFIDENCE_CATEGORIES) {
							this.categories.set_Is104(true);
						} else
							this.categories.set_Is104(false);
					} else
						this.categories.set_Is104(false);

					if (temp.has("105") == true) {
						confidence = temp.getString("105");
						this.categories.setC105(Integer.parseInt(confidence));
						if (this.categories.getC105() >= SiteInfo.THRESHOLD_CONFIDENCE_CATEGORIES) {
							this.categories.set_Is105(true);
						} else
							this.categories.set_Is105(false);
					} else
						this.categories.set_Is105(false);

					if (temp.has("201") == true) {
						confidence = temp.getString("201");
						this.categories.setC201(Integer.parseInt(confidence));
						if (this.categories.getC201() >= SiteInfo.THRESHOLD_CONFIDENCE_CATEGORIES) {
							this.categories.set_Is201(true);
						} else
							this.categories.set_Is201(false);
					} else
						this.categories.set_Is201(false);

					if (temp.has("202") == true) {
						confidence = temp.getString("202");
						this.categories.setC202(Integer.parseInt(confidence));
						if (this.categories.getC202() >= SiteInfo.THRESHOLD_CONFIDENCE_CATEGORIES) {
							this.categories.set_Is202(true);
						} else
							this.categories.set_Is202(false);
					} else
						this.categories.set_Is202(false);

					if (temp.has("203") == true) {
						confidence = temp.getString("203");
						this.categories.setC203(Integer.parseInt(confidence));
						if (this.categories.getC203() >= SiteInfo.THRESHOLD_CONFIDENCE_CATEGORIES) {
							this.categories.set_Is203(true);
						} else
							this.categories.set_Is203(false);
					} else
						this.categories.set_Is203(false);

					if (temp.has("204") == true) {
						confidence = temp.getString("204");
						this.categories.setC204(Integer.parseInt(confidence));
						if (this.categories.getC204() >= SiteInfo.THRESHOLD_CONFIDENCE_CATEGORIES) {
							this.categories.set_Is204(true);
						} else
							this.categories.set_Is204(false);
					} else
						this.categories.set_Is204(false);

					if (temp.has("205") == true) {
						confidence = temp.getString("205");
						this.categories.setC205(Integer.parseInt(confidence));
						if (this.categories.getC205() >= SiteInfo.THRESHOLD_CONFIDENCE_CATEGORIES) {
							this.categories.set_Is205(true);
						} else
							this.categories.set_Is205(false);
					} else
						this.categories.set_Is205(false);

					if (temp.has("206") == true) {
						confidence = temp.getString("206");
						this.categories.setC206(Integer.parseInt(confidence));
						if (this.categories.getC206() >= SiteInfo.THRESHOLD_CONFIDENCE_CATEGORIES) {
							this.categories.set_Is206(true);
						} else
							this.categories.set_Is206(false);
					} else
						this.categories.set_Is206(false);

					if (temp.has("207") == true) {
						confidence = temp.getString("207");
						this.categories.setC207(Integer.parseInt(confidence));
						if (this.categories.getC207() >= SiteInfo.THRESHOLD_CONFIDENCE_CATEGORIES) {
							this.categories.set_Is207(true);
						} else
							this.categories.set_Is207(false);
					} else
						this.categories.set_Is207(false);

					if (temp.has("301") == true) {
						confidence = temp.getString("301");
						this.categories.setC301(Integer.parseInt(confidence));
						if (this.categories.getC301() >= SiteInfo.THRESHOLD_CONFIDENCE_CATEGORIES) {
							this.categories.set_Is301(true);
						} else
							this.categories.set_Is301(false);
					} else
						this.categories.set_Is301(false);

					if (temp.has("302") == true) {
						confidence = temp.getString("302");
						this.categories.setC302(Integer.parseInt(confidence));
						if (this.categories.getC302() >= SiteInfo.THRESHOLD_CONFIDENCE_CATEGORIES) {
							this.categories.set_Is302(true);
						} else
							this.categories.set_Is302(false);
					} else
						this.categories.set_Is302(false);

					if (temp.has("303") == true) {
						confidence = temp.getString("303");
						this.categories.setC303(Integer.parseInt(confidence));
						if (this.categories.getC303() >= SiteInfo.THRESHOLD_CONFIDENCE_CATEGORIES) {
							this.categories.set_Is303(true);
						} else
							this.categories.set_Is303(false);
					} else
						this.categories.set_Is303(false);

					if (temp.has("304") == true) {
						confidence = temp.getString("304");
						this.categories.setC304(Integer.parseInt(confidence));
						if (this.categories.getC304() >= SiteInfo.THRESHOLD_CONFIDENCE_CATEGORIES) {
							this.categories.set_Is304(true);
						} else
							this.categories.set_Is304(false);
					} else
						this.categories.set_Is304(false);

					if (temp.has("401") == true) {
						confidence = temp.getString("401");
						this.categories.setC401(Integer.parseInt(confidence));
						if (this.categories.getC401() >= 10) {
							this.categories.set_Is401(true);
						} else
							this.categories.set_Is401(false);
					} else
						this.categories.set_Is401(false);

					if (temp.has("402") == true) {
						confidence = temp.getString("402");
						this.categories.setC402(Integer.parseInt(confidence));
						if (this.categories.getC402() >= SiteInfo.THRESHOLD_CONFIDENCE_CATEGORIES) {
							this.categories.set_Is402(true);
						} else
							this.categories.set_Is402(false);
					} else
						this.categories.set_Is402(false);

					if (temp.has("403") == true) {
						confidence = temp.getString("403");
						this.categories.setC403(Integer.parseInt(confidence));
						if (this.categories.getC403() >= SiteInfo.THRESHOLD_CONFIDENCE_CATEGORIES) {
							this.categories.set_Is403(true);
						} else
							this.categories.set_Is403(false);
					} else
						this.categories.set_Is403(false);

					if (temp.has("404") == true) {
						confidence = temp.getString("404");
						this.categories.setC404(Integer.parseInt(confidence));
						if (this.categories.getC404() >= SiteInfo.THRESHOLD_CONFIDENCE_CATEGORIES) {
							this.categories.set_Is404(true);
						} else
							this.categories.set_Is404(false);
					} else
						this.categories.set_Is404(false);
					// set threshold for this ??????
					if (temp.has("501") == true) {
						confidence = temp.getString("501");
						this.categories.setC501(Integer.parseInt(confidence));
						if (this.categories.getC501() >= SiteInfo.THRESHOLD_CONFIDENCE_CATEGORIES) {
							this.categories.set_Is501(true);
						} else
							this.categories.set_Is501(false);
					} else
						this.categories.set_Is501(false);
				} else {
					this.categories.set_Is101(false);
					this.categories.set_Is102(false);
					this.categories.set_Is103(false);
					this.categories.set_Is104(false);
					this.categories.set_Is105(false);
					this.categories.set_Is201(false);
					this.categories.set_Is202(false);
					this.categories.set_Is203(false);
					this.categories.set_Is204(false);
					this.categories.set_Is205(false);
					this.categories.set_Is206(false);
					this.categories.set_Is207(false);
					this.categories.set_Is301(false);
					this.categories.set_Is302(false);
					this.categories.set_Is303(false);
					this.categories.set_Is304(false);
					this.categories.set_Is501(false);
					this.categories.set_Is401(false);
					this.categories.set_Is402(false);
					this.categories.set_Is403(false);
					this.categories.set_Is404(false);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void handle_result_zero_four() {
		// call method handle_zero_and_four() to get reputation and confidence
		// of two component trustworthiness and child safety
		int kq = this.get_zero_and_four();
		// set type of site depend on value of reputation and confidence of
		// site, with confidence value is greater than 10
		if (kq == 1) {
			if (this.getZero()) {

				if (this.get_c0_trustworthiness_value() >= SiteInfo.THRESHOLD_CONFIDENCE_REPUTATION) {

					if (this.get_r0_trustworthiness_value() >= SiteInfo.THRESHOLD_EXCELLENT_SITE) {
						this.settrustworthiness_stt(SiteInfo.EXCELLENT);

					}
					if (this.get_r0_trustworthiness_value() >= SiteInfo.THRESHOLD_GOOD_SITE
							&& this.get_r0_trustworthiness_value() < SiteInfo.THRESHOLD_EXCELLENT_SITE) {
						this.settrustworthiness_stt(SiteInfo.GOOD);

					}
					if (this.get_r0_trustworthiness_value() >= SiteInfo.THRESHOLD_UNSATISFACTORY_SITE
							&& this.get_r0_trustworthiness_value() < SiteInfo.THRESHOLD_GOOD_SITE) {
						this.settrustworthiness_stt(SiteInfo.UNSATISFACTORY);

					}
					if (this.get_r0_trustworthiness_value() >= SiteInfo.THRESHOLD_POOR_SITE
							&& this.get_r0_trustworthiness_value() < SiteInfo.THRESHOLD_UNSATISFACTORY_SITE) {
						this.settrustworthiness_stt(SiteInfo.POOR);

					}
					if (this.get_r0_trustworthiness_value() >= SiteInfo.THRESHOLD_VERYPOOR_SITE
							&& this.get_r0_trustworthiness_value() < SiteInfo.THRESHOLD_POOR_SITE) {
						this.settrustworthiness_stt(SiteInfo.VERY_POOR);
					}
				} else {
					this.settrustworthiness_stt(0);
				}
			} else {
				this.settrustworthiness_stt(SiteInfo.UNKNOWN);
			}

			if (this.getFour()) {

				if (this.get_c4_child_safety_value() >= SiteInfo.THRESHOLD_CHILD_SAFETY) {

					if (this.get_r4_child_safety_value() >= SiteInfo.THRESHOLD_EXCELLENT_SITE) {
						this.setChild_safety_stt(SiteInfo.EXCELLENT);

					} else if (this.get_r4_child_safety_value() >= SiteInfo.THRESHOLD_GOOD_SITE) {
						this.setChild_safety_stt(SiteInfo.GOOD);

					} else if (this.get_r4_child_safety_value() >= SiteInfo.THRESHOLD_UNSATISFACTORY_SITE) {
						this.setChild_safety_stt(SiteInfo.UNSATISFACTORY);

					} else if (this.get_r4_child_safety_value() >= SiteInfo.THRESHOLD_POOR_SITE) {
						this.setChild_safety_stt(SiteInfo.POOR);

					} else if (this.get_r4_child_safety_value() >= SiteInfo.THRESHOLD_VERYPOOR_SITE) {
						this.setChild_safety_stt(SiteInfo.VERY_POOR);
					}
				} else {
					// not enough confidence.
				}
			} else {
				this.setChild_safety_stt(SiteInfo.UNKNOWN);
			}
		} else {
			// do something
		}

	}

}
