package kr.ac.incheon.ns.helperapp.utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ServerUrl {

    //private static final String SERVER_URL = "http://isoft.incheon.ac.kr/";
    private static final String SERVER_URL = "http://117.16.244.88/";

    // get
	public static String urlMaker(String URLcase, String URLData){
		String CompleteURL= SERVER_URL + URLcase+ "?" + URLData;
		//Log.e("CompleteURL : " + CompleteURL);
		return CompleteURL;	
	}
	
	// post
	public static String urlMaker(String URLcase){
		String CompleteURL= SERVER_URL + URLcase;
		//Log.e("CompleteURL : " + CompleteURL);
		return CompleteURL;	
	}

    // not app
    public static String urlMakerImg(String URLcase){
        String CompleteURL= SERVER_URL + URLcase;
        //Log.e("CompleteURL : " + CompleteURL);
        return CompleteURL;
    }
	
	public static String imgUrlMaker(String folder, String imgName){
		
		if(imgName == null || imgName == ""){
			imgName = "no_profile_img";
		}
		
		String CompleteURL = SERVER_URL + folder + imgName;

		//Log.e("completeURL : " + CompleteURL);
		
		return CompleteURL;
	}

    public static String appImgUrlMaker(String imgName){

        String CompleteURL = SERVER_URL + "app/img/" + imgName;

        //Log.e("completeURL : " + CompleteURL);

        return CompleteURL;
    }


    public static String ImgUrlMaker(String imgName){

        String CompleteURL = SERVER_URL + "profile_img/" + imgName;

        //Log.e("completeURL : " + CompleteURL);

        return CompleteURL;
    }

    // 전송할 파라메터 json으로 만들기
    public static JSONObject makeJson(HashMap<String, String> params) {
        Set key = params.keySet();
        JSONObject JsonObject = new JSONObject();

        for (Iterator iterator = key.iterator(); iterator.hasNext();) {
            String keyName = (String) iterator.next();
            String valueName = (String) params.get(keyName);

            try {
                //Log.e(keyName + " : " + valueName);
                JsonObject.put(keyName, valueName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Log.e("json : " + JsonObject.toString());

        return JsonObject;
    }
}