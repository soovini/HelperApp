package kr.ac.incheon.ns.helperapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;

import kr.ac.incheon.ns.helperapp.model.UserInfoItem;
import kr.ac.incheon.ns.helperapp.utility.Log;
import kr.ac.incheon.ns.helperapp.utility.ServerUrl;
import kr.ac.incheon.ns.helperapp.utility.Preference;
import kr.ac.incheon.ns.helperapp.volly.GsonPostRequest;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edt_id, edt_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        TextView tv_signup = (TextView)findViewById(R.id.tv_signup);
        tv_signup.setOnClickListener(this);

        edt_id = (EditText)findViewById(R.id.edt_userid);
        edt_pw = (EditText)findViewById(R.id.edt_userpw);

    }

    private HashMap<String, String> mParams = new HashMap<String, String>();
    private GsonPostRequest<UserInfoItem> postRequest;
    private RequestQueue mQueue;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login :

                String str_id = edt_id.getText().toString();
                String str_pw = edt_pw.getText().toString();

                mParams.put("id", str_id);
                mParams.put("pw", str_pw);

                mQueue = MyApp.getInstance().getRequestQueue();
                String url = ServerUrl.urlMaker("login.php");

                mQueue.stop();
                postRequest = new GsonPostRequest<UserInfoItem>(
                        Request.Method.POST,
                        url,
                        UserInfoItem.class,
                        mParams,
                        null,
                        createMyReqSuccessListener(),
                        createMyReqErrorListener());

                mQueue.add(postRequest);
                mQueue.start();

                //Intent i = new Intent(LoginActivity.this, MainActivity.class);
                //startActivity(i);

                break;

            case R.id.tv_signup :
                //Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                //startActivity(intent);
            break;

        }
    }

    // 통신 후 발생하는 이벤트
    private Response.Listener<UserInfoItem> createMyReqSuccessListener() {
        return new Response.Listener<UserInfoItem>() {
            @Override
            public void onResponse(UserInfoItem response) {
                String resultCode = response.getReturn_code();

                if(resultCode.equals("200")){
                    Toast.makeText(getApplicationContext(), "성공했습니다.", Toast.LENGTH_LONG).show();
                    String str_id = response.getId();
                    String str_pw = response.getPw();
                    String str_name = response.getName();
                    String str_uid = response.getUid();

                    Preference.put(getApplicationContext(),"id",str_id);
                    Preference.put(getApplicationContext(),"pw",str_pw);
                    Preference.put(getApplicationContext(),"name",str_name);
                    Preference.put(getApplicationContext(),"uid",str_uid);

                    Intent i = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i);
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
