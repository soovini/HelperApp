package kr.ac.incheon.ns.helperapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;

import kr.ac.incheon.ns.helperapp.model.UserInfoItem;
import kr.ac.incheon.ns.helperapp.utility.Log;
import kr.ac.incheon.ns.helperapp.utility.ServerUrl;
import kr.ac.incheon.ns.helperapp.volly.GsonPostRequest;


public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edt_userid, edt_pw1, edt_pw2, edt_usernm;
    Button btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edt_userid = (EditText)findViewById(R.id.edt_userid);
        edt_pw1 = (EditText)findViewById(R.id.edt_pw1);
        edt_pw2 = (EditText)findViewById(R.id.edt_pw2);
        edt_usernm = (EditText)findViewById(R.id.edt_usernm);
        btn_signup = (Button)findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(this);

    }
    private HashMap<String, String> mParams = new HashMap<String, String>();
    private GsonPostRequest<UserInfoItem> postRequest;
    private RequestQueue mQueue;

    @Override
    public void onClick(View view) {
        String str_userid = edt_userid.getText().toString();
        String str_pw1 = edt_pw1.getText().toString();
        String str_pw2 = edt_pw2.getText().toString();
        String str_usernm = edt_usernm.getText().toString();

        mParams.put("id", str_userid);
        mParams.put("pw", str_pw1);
        mParams.put("name", str_usernm);

        mQueue = MyApp.getInstance().getRequestQueue();
        String url = ServerUrl.urlMaker("setmember.php");

        mQueue.stop();
        postRequest = new GsonPostRequest<UserInfoItem>(Request.Method.POST,
                url,
                UserInfoItem.class,
                mParams,
                null,
                createMyReqSuccessListener(),
                createMyReqErrorListener());

        mQueue.add(postRequest);
        mQueue.start();
    }
    // 통신 후 발생하는 이벤트
    private Response.Listener<UserInfoItem> createMyReqSuccessListener() {
        return new Response.Listener<UserInfoItem>() {
            @Override
            public void onResponse(UserInfoItem response) {
                String resultCode = response.getReturn_code();

                if(resultCode.equals("200")){
                    Toast.makeText(getApplicationContext(), "성공했습니다.", Toast.LENGTH_LONG).show();

                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "존재하지 않는 회원입니다.", Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(error.getMessage());
                Toast.makeText(getApplicationContext(), "오류가 발생하였습니다.", Toast.LENGTH_LONG).show();
            }
        };
    }
}
