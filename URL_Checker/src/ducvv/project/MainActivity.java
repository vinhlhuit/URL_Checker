package ducvv.project;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button btnCheck, btnContinue, btnCancel, btn;
	Dialog dialog;
	TextView txt_url, txt_trust, txt_child;
	ImageView image_trust, image_child, image_check, image_help;
	LinearLayout layout_categories;
	final Context context = this;
	String url;
	String link, json, jsons;
	Result resultobj;
	ProgressBar bar;

	SiteInfo siteinfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// dialog for checking url
		dialog = new Dialog(context);
		dialog.setContentView(R.layout.dialog_layout);
		dialog.setTitle("Check URL");

		btnCheck = (Button) dialog.findViewById(R.id.dialog_btnCheck);
		btnContinue = (Button) dialog.findViewById(R.id.dialog_btnContinue);
		btnCancel = (Button) dialog.findViewById(R.id.dialog_btnCancel);

		txt_trust = (TextView) dialog
				.findViewById(R.id.dialog_label_trust_repu);
		txt_child = (TextView) dialog.findViewById(R.id.dialog_label_child);
		txt_url = (TextView) dialog.findViewById(R.id.dialog_labelurl);

		image_child = (ImageView) dialog.findViewById(R.id.dialog_image_child);
		image_trust = (ImageView) dialog.findViewById(R.id.dialog_imagetrust);
		image_check = (ImageView) findViewById(R.id.image_check);
		image_help = (ImageView) findViewById(R.id.image_help);
		layout_categories = (LinearLayout) dialog
				.findViewById(R.id.dialog_linearforcategories);

		jsons = "";
		bar = new ProgressBar(this);
		// a = new ProgressDialog(CheckUrl.this);
		siteinfo = new SiteInfo();

		// create TextView Categories
		create_textview_categories();

		final Intent intent = getIntent();
		final String action = intent.getAction();

		if (Intent.ACTION_VIEW.equals(action)) {
			Uri segments = intent.getData(); // get URI
			link = segments.toString(); // get URL
			txt_url.setText(link); // show link

			dialog.show(); // show check URL Dialog
			layout_categories.removeAllViews(); // clear layout categories
			create_textview_categories();
			if (isNetworkAvailable()) {
				// create link to request to wot and google
				String wot_link = create_wot_link(link);
				String google_link = create_google_link(link);
				// start asyntask get result from wot and google
				connectWithHttpGet(wot_link, google_link);
			} else {
				AlertDialog alertDialog = new AlertDialog.Builder(context)
						.create();
				alertDialog.setTitle("ERROR");
				alertDialog
						.setMessage("Can't connect to Internet. Please check your internet connection or try again later");
				alertDialog.setIcon(R.drawable.warning);

				alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
				alertDialog.show();
			}
		}
		image_check.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// go to CheckUrl activity
				Intent myIntent = new Intent(MainActivity.this, CheckUrl.class);
				startActivity(myIntent);
				// animation
				overridePendingTransition(R.anim.pull_in_left,
						R.anim.pull_out_right);
			}
		});
		image_help.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// go to Help Activity
				Intent myIntent = new Intent(MainActivity.this, Help.class);
				startActivity(myIntent);
				// animation
				overridePendingTransition(R.anim.pull_in_right,
						R.anim.pull_out_left);
			}
		});

		btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss(); // end dialog
			}
		});
		btnContinue.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// start web browser
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(link));
				startActivity(intent);
				dialog.dismiss();

			}
		});
		btnCheck.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// check url (later)
				// get url

				layout_categories.removeAllViews();
				create_textview_categories();
				if (isNetworkAvailable()) {
					// create link to request to wot and google
					String wot_link = create_wot_link(link);
					String google_link = create_google_link(link);
					// start asyntask get result from wot and google
					connectWithHttpGet(wot_link, google_link);

					// image_trust.setBackgroundResource(R.drawable.r3);
				} else {
					AlertDialog alertDialog = new AlertDialog.Builder(context)
							.create();
					alertDialog.setTitle("ERROR");
					alertDialog
							.setMessage("Can't connect to Internet. Please check your internet connection or try again later");
					alertDialog.setIcon(R.drawable.warning);

					alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
					alertDialog.show();
				}
			}
		});
	}

	public boolean isNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		// if no network is available networkInfo will be null
		// otherwise check if we are connected
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}

	private void connectWithHttpGet(String wot_url, String google_url) {

		class HttpGetAsyncTask extends AsyncTask<String, Void, Result> {
			private final ProgressDialog dialog = new ProgressDialog(
					MainActivity.this);

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				this.dialog.setMessage("Checking....Please wait...");
				this.dialog.show();
			}

			@Override
			protected Result doInBackground(String... params) {
				Result re = new Result();
				DefaultHttpClient httpClient = new DefaultHttpClient(
						new BasicHttpParams());
				String wot_url = params[0];
				String google_url = params[1];
				String result = null;

				try {
					HttpGet httpGet_wot = null; // for WOT API
					HttpGet httpGet_google = null; // for google API

					HttpEntity httpEntity = null; // for WOT API

					HttpResponse httpResponse_wot = null; // for WOT API
					HttpResponse httpResponse_google = null; // for Google API

					httpGet_wot = new HttpGet(wot_url);
					httpGet_google = new HttpGet(google_url);

					httpResponse_google = httpClient.execute(httpGet_google);
					httpResponse_wot = httpClient.execute(httpGet_wot);

					httpEntity = httpResponse_wot.getEntity();

					int responseCode_google = httpResponse_google
							.getStatusLine().getStatusCode();
					int responseCode_wot = httpResponse_wot.getStatusLine()
							.getStatusCode();

					result = EntityUtils.toString(httpEntity);
					String google_code = String.valueOf(responseCode_google);

					String json;
					json = result.substring(8, result.length() - 1);
					int index = json.indexOf(":");
					json = json.substring(index + 1, json.length() - 2);

					re.set_responseCode(responseCode_wot);
					re.set_result(json);
					re.setGoogleAPI_responseCode(google_code);
					jsons = json;
					return re;
				} catch (Exception e) {
					// Oops
				}
				return null;
			}

			// Argument comes for this method according to the return type of
			// the doInBackground() and
			// it is the third generic type of the AsyncTask
			@Override
			protected void onProgressUpdate(Void... values) {
				// TODO Auto-generated method stub
				super.onProgressUpdate(values);
			}

			@Override
			protected void onPostExecute(Result result) {
				super.onPostExecute(result);
				// resultobj = new Result(result.get_responseCode(),
				// result.get_result(), result.getGoogleAPI_responseCode());
				// txt_trust.setText(result.get_result());
				// String a = result.get_result().toString();
				// json = a;

				this.dialog.cancel();

				// if (result.getGoogleAPI_responseCode().equals("200"))
				// txt_tets.setText("This site contain virus");
				// else {
				// txt_tets.setText("nothing" +
				// result.getGoogleAPI_responseCode());
				// handle later
				// }
				String jsonText = result.get_result();
				if (jsonText.length() < 3) {
					AlertDialog alertDialog = new AlertDialog.Builder(context)
							.create();
					alertDialog.setTitle("ERROR");
					alertDialog
							.setMessage("Incorrect URL. Can't check your URL");
					alertDialog.setIcon(R.drawable.warning);

					alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
					alertDialog.show();
				} else {
					siteinfo.set_url(link);
					txt_url.setText(link);
					// txt_child.setText(jsonText);
					int kq = siteinfo.set_json(jsonText);
					if (kq == -1) {
						Toast.makeText(getApplicationContext(),
								"Can't get trustworthiness from WOT API",
								Toast.LENGTH_SHORT).show();

					}

					// Start handle zero and four
					siteinfo.handle_result_zero_four();
					int child = siteinfo.getChild_safety_stt();
					int trust = siteinfo.gettrustworthiness_stt();

					if (trust == -1) {
						Toast.makeText(getApplicationContext(),
								String.valueOf(trust), Toast.LENGTH_SHORT)
								.show();
					} else {
						// Toast.makeText(getApplicationContext(),
						// String.valueOf(trust), Toast.LENGTH_SHORT).show();

						if (trust == SiteInfo.EXCELLENT) {
							image_trust.setImageDrawable(getResources()
									.getDrawable(R.drawable.r5));

							txt_trust.setText("Excellent");
						}
						if (trust == SiteInfo.GOOD) {
							image_trust.setImageDrawable(getResources()
									.getDrawable(R.drawable.r4));
							txt_trust.setText("Good");
						}
						if (trust == SiteInfo.UNSATISFACTORY) {
							image_trust.setImageDrawable(getResources()
									.getDrawable(R.drawable.r3));
							txt_trust.setText("Unsatisfactory");
						}
						if (trust == SiteInfo.POOR) {
							image_trust.setImageDrawable(getResources()
									.getDrawable(R.drawable.r2));
							txt_trust.setText("Poor");
						}
						if (trust == SiteInfo.VERY_POOR) {
							image_trust.setImageDrawable(getResources()
									.getDrawable(R.drawable.r1));
							txt_trust.setText("Very poor");
						}
						if (trust == SiteInfo.UNKNOWN) {
							image_trust.setImageDrawable(getResources()
									.getDrawable(R.drawable.r0));
							txt_trust.setText("Unknown");
						}

					}

					if (child == -1) {
						Toast.makeText(getApplicationContext(),
								String.valueOf(child), Toast.LENGTH_SHORT)
								.show();
					} else {
						// Toast.makeText(getApplicationContext(),
						// String.valueOf(child), Toast.LENGTH_SHORT).show();

						if (child == SiteInfo.EXCELLENT) {
							image_child.setImageDrawable(getResources()
									.getDrawable(R.drawable.r5));
							txt_child.setText("Excellent");
						}
						if (child == SiteInfo.GOOD) {
							image_child.setImageDrawable(getResources()
									.getDrawable(R.drawable.r4));
							txt_child.setText("Good");
						}
						if (child == SiteInfo.UNSATISFACTORY) {
							image_child.setImageDrawable(getResources()
									.getDrawable(R.drawable.r3));
							txt_child.setText("Unsatisfactory");
						}
						if (child == SiteInfo.POOR) {
							image_child.setImageDrawable(getResources()
									.getDrawable(R.drawable.r2));
							txt_child.setText("Poor");
						}
						if (child == SiteInfo.VERY_POOR) {
							image_child.setImageDrawable(getResources()
									.getDrawable(R.drawable.r1));
							txt_child.setText("Very poor");
						}
						if (child == SiteInfo.UNKNOWN) {
							image_child.setImageDrawable(getResources()
									.getDrawable(R.drawable.r0));
							txt_child.setText("Unknown");
						}
					} // end handle zero and four

					// start handle categories

					siteinfo.handle_categories();
					// from google Api: contain malware or phishing
					if (result.getGoogleAPI_responseCode().equals("200")) {
						TextView newtext = create_textView("- Phishing, malware, or both");
						newtext.setTextColor(Color.RED);
						layout_categories.addView(newtext);
					}
					// Positive 404 Site for kids
					if (siteinfo.categories.get_Is404()) {
						TextView newtext = create_textView("- Site for kids");
						newtext.setTextColor(Color.GREEN);
						layout_categories.addView(newtext);
					}
					// Positive 501 Good site
					if (siteinfo.categories.get_Is501()) {
						TextView newtext = create_textView("- Good site");
						newtext.setTextColor(Color.GREEN);
						layout_categories.addView(newtext);

					}
					// Questionable
					// 201 Misleading claims or unethical
					if (siteinfo.categories.get_Is201()) {
						TextView newtext = create_textView("- Misleading claims or unethical");
						newtext.setTextColor(Color.YELLOW);
						layout_categories.addView(newtext);
					}
					// 202 Privacy risks
					if (siteinfo.categories.get_Is202()) {
						TextView newtext = create_textView("- Privacy risks");
						newtext.setTextColor(Color.YELLOW);
						layout_categories.addView(newtext);
					}
					// 203 Suspicious
					if (siteinfo.categories.get_Is203()) {
						TextView newtext = create_textView("- Suspicious");
						newtext.setTextColor(Color.YELLOW);
						layout_categories.addView(newtext);
					}
					// 204 Hate, discrimination
					if (siteinfo.categories.get_Is204()) {
						TextView newtext = create_textView("- Hate, discrimination");
						newtext.setTextColor(Color.YELLOW);
						layout_categories.addView(newtext);
					}
					// 205 Spam
					if (siteinfo.categories.get_Is205()) {
						TextView newtext = create_textView("- Spam");
						newtext.setTextColor(Color.YELLOW);
						layout_categories.addView(newtext);
					}
					// 206 Potentially unwanted programs
					if (siteinfo.categories.get_Is206()) {
						TextView newtext = create_textView("- Potentially unwanted programs");
						newtext.setTextColor(Color.YELLOW);
						layout_categories.addView(newtext);
					}
					// 207 Ads / pop-ups
					if (siteinfo.categories.get_Is207()) {
						TextView newtext = create_textView("- Ads / pop-ups");
						newtext.setTextColor(Color.YELLOW);
						layout_categories.addView(newtext);
					}
					// Questionable
					// 402 Incidental nudity
					if (siteinfo.categories.get_Is402()) {
						TextView newtext = create_textView("- Incidental nudity ");
						newtext.setTextColor(Color.YELLOW);
						layout_categories.addView(newtext);
					}
					// 403 Gruesome or shocking
					if (siteinfo.categories.get_Is403()) {
						TextView newtext = create_textView("- Gruesome or shocking");
						newtext.setTextColor(Color.YELLOW);
						layout_categories.addView(newtext);
					}
					// 101Malware or viruses
					if (siteinfo.categories.get_Is101()) {
						TextView newtext = create_textView("- Malware or viruses");
						newtext.setTextColor(Color.RED);
						layout_categories.addView(newtext);
					}
					// 102 Poor customer experience
					if (siteinfo.categories.get_Is102()) {
						TextView newtext = create_textView("- Poor customer experience");
						newtext.setTextColor(Color.RED);
						layout_categories.addView(newtext);
					}
					// 103 Phishing
					if (siteinfo.categories.get_Is103()) {
						TextView newtext = create_textView("- Phishing");
						newtext.setTextColor(Color.RED);
						layout_categories.addView(newtext);
					}
					// 104 Scam
					if (siteinfo.categories.get_Is104()) {
						TextView newtext = create_textView("- Scam");
						newtext.setTextColor(Color.RED);
						layout_categories.addView(newtext);
					}
					// 105 Potentially illegal
					if (siteinfo.categories.get_Is105()) {
						TextView newtext = create_textView("- Potentially illegal");
						newtext.setTextColor(Color.RED);
						layout_categories.addView(newtext);
					}
					// 401 Adult content
					if (siteinfo.categories.get_Is401()) {
						TextView newtext = create_textView("- Adult content ");
						newtext.setTextColor(Color.RED);
						layout_categories.addView(newtext);
					}

					// Neutral
					// 301 Online tracking
					if (siteinfo.categories.get_Is301()) {
						TextView newtext = create_textView("- Online tracking ");
						newtext.setTextColor(Color.GRAY);
						layout_categories.addView(newtext);
					}
					// 302 Alternative or controversial medicine
					if (siteinfo.categories.get_Is302()) {
						TextView newtext = create_textView("- Alternative or controversial medicine ");
						newtext.setTextColor(Color.GRAY);
						layout_categories.addView(newtext);
					}
					// 303 Opinions, religion, politics
					if (siteinfo.categories.get_Is303()) {
						TextView newtext = create_textView("- Opinions, religion, politics");
						newtext.setTextColor(Color.GRAY);
						layout_categories.addView(newtext);
					}
					// 304 Other
					if (siteinfo.categories.get_Is304()) {
						TextView newtext = create_textView("- Other ");
						newtext.setTextColor(Color.GRAY);
						layout_categories.addView(newtext);
					}
				}
			}
		}

		// Initialize the AsyncTask class
		HttpGetAsyncTask httpGetAsyncTask = new HttpGetAsyncTask();
		// Parameter we pass in the execute() method is relate to the first
		// generic type of the AsyncTask
		// We are passing the connectWithHttpGet() method arguments to that
		try {
			httpGetAsyncTask.execute(wot_url, google_url);
		} catch (Exception ex) {
			Toast.makeText(getApplicationContext(),
					"ERROR when executive AsyncTask", Toast.LENGTH_LONG).show();
		}

	}

	private void handle_with_dialog() {

	}

	private String create_google_link(String url) {
		String mylink = url;
		mylink = mylink.trim();
		String baseURL = "https://sb-ssl.google.com/safebrowsing/api/lookup";
		String arguments = "";
		try {

			arguments += URLEncoder.encode("client", "UTF-8") + "="
					+ URLEncoder.encode("project", "UTF-8") + "&";
			arguments += URLEncoder.encode("key", "UTF-8")
					+ "="
					+ URLEncoder.encode(
							"AIzaSyDGTf8IsYM5_CRd5M8R_YQw2dyeMTQ_KDs", "UTF-8")
					+ "&";
			arguments += URLEncoder.encode("appver", "UTF-8") + "="
					+ URLEncoder.encode("1.5.2", "UTF-8") + "&";
			arguments += URLEncoder.encode("pver", "UTF-8") + "="
					+ URLEncoder.encode("3.1", "UTF-8") + "&";
			arguments += URLEncoder.encode("url", "UTF-8") + "="
					+ URLEncoder.encode(mylink, "UTF-8");
			baseURL += "?" + arguments;
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return baseURL;
	}

	// for google API
	private String standard_url(String url) {
		String link = url;
		try {
			link = link.trim();
			link = URLEncoder.encode(link, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return link;
	}

	// this method will get the link of website and convert to request format
	private String create_wot_link(String url) {
		String mylink = "http://api.mywot.com/0.4/public_link_json2?hosts=";
		if (url != null) {
			url = url.trim();
			String myhost = url + "/";
			String callback = "&callback=process";
			String myWotkey = "&key=c15372fe71edc45cede2548bf92685ca336abff8";
			mylink += myhost + callback + myWotkey;
		} else
			mylink = null;
		return mylink;
	}

	private TextView create_textView(String text) {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 0.0F);
		params.setMargins(30, 5, 0, 5);
		TextView tx = new TextView(getApplicationContext());
		tx.setLayoutParams(params);
		tx.setTextSize(17);
		tx.setTextColor(Color.parseColor("#000000"));
		// tx.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
		// LayoutParams.WRAP_CONTENT));
		tx.setText(text);
		// handle zero and four
		// layout.addView(tx);

		return tx;
	}

	private void create_textview_categories() {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 0.0F);
		params.setMargins(5, 5, 0, 5);
		TextView tx = new TextView(getApplicationContext());
		tx.setLayoutParams(params);
		tx.setTextSize(19);
		tx.setTextColor(Color.parseColor("#000000"));
		// tx.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
		// LayoutParams.WRAP_CONTENT));
		tx.setText("Categories");
		layout_categories.addView(tx);
	}
}
