package kr.ac.incheon.ns.helperapp;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import kr.ac.incheon.ns.helperapp.utility.Preference;


public class MainActivity extends AppCompatActivity {
    NfcAdapter mNfcAdapter; // NFC 어댑터

    private Handler pointHandler = new Handler();
    private Handler barHandler = new Handler();
    private ProgressBar progress_bar;
    private TextView MyPoint;
    private TextView btn_mypoint;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_name = (TextView)findViewById(R.id.tv_name);
        String name = Preference.getValue(getApplicationContext(),"name","");
        tv_name.setText(name);

        // NFC 어댑터를 구한다
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {  // NFC 미지원단말
            Toast.makeText(getApplicationContext(), "NFC를 지원하지 않는 단말기입니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        progress_bar = (ProgressBar)findViewById (R.id.progress_bar);
        MyPoint = (TextView) findViewById(R.id.textView1);
        btn_mypoint = (TextView) findViewById(R.id.btn_mypoint);

        progress_bar.setProgress(30);

        progress_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(){
                    public void run() {
                        count=0;
                        while ( count <= 100 ) {
                            try {Thread.sleep(50);}catch(Exception e){}
                            barHandler.post(new Runnable() {
                                public void run(){
                                    progress_bar.setProgress(count);
                                }
                            });
                            count++;
                        }

                        pointHandler.post(new Runnable() {
                            public void run(){
                                ValueAnimator animator = new ValueAnimator();
                                animator.setObjectValues(2750, 2850);
                                animator.setDuration(5000);
                                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    public void onAnimationUpdate(ValueAnimator animation) {
                                        MyPoint.setText("" + (int) animation.getAnimatedValue());
                                    }
                                });
                                animator.start();
                            }
                        });

                    }
                }.start();


            }
        });

        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(1000, 2750);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                MyPoint.setText("" + (int) animation.getAnimatedValue());
            }
        });
        animator.start();

        btn_mypoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MyPointActivity.class);
                startActivity(i);
            }
        });





    }

}
