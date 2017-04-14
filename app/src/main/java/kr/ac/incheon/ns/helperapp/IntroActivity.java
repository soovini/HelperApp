package kr.ac.incheon.ns.helperapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ImageView splash = (ImageView) findViewById(R.id.splash);


        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent i = new Intent(IntroActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                },3000);
            }
        });
        animation.setDuration(2500);
        Glide.with(this).load("http://isoft.incheon.ac.kr/temp.gif").into(splash);
        splash.startAnimation(animation);
    }
}
