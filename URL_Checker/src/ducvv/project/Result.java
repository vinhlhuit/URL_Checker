package ducvv.project;

public class Result {
		private String url;
		private int responseCode;
		private String result;
		private String googleAPI_responseCode;
		
		public Result(){
			
		}
		public Result(int responseCode, String result, String google_responseCode){
			this.responseCode = responseCode;
			this.result = result;
			this.googleAPI_responseCode = google_responseCode;
		}
		public void set_responseCode(int responseCode){
			this.responseCode = responseCode;
		}
		
		public int get_responseCode(){
			return this.responseCode;
		}
		
		public void set_result(String result){
			this.result = result;
		}
		
		public String get_result(){
			return this.result;
		}

		public String getGoogleAPI_responseCode() {
			return googleAPI_responseCode;
		}

		public void setGoogleAPI_responseCode(String googleAPI_responseCode) {
			this.googleAPI_responseCode = googleAPI_responseCode;
		}
}
