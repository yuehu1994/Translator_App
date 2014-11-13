package com.example.yuehu.splash;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import com.example.yuehu.translator.MainActivity;
import com.example.yuehu.translator.R;

/**
 * Created by yuehu on 11/12/14.
 */
public class splash_activity extends Activity {
    private static int time_out = 2500;                         //splash activity lasts 4 seconds.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        new Handler().postDelayed(new Runnable() {              //will run after time out of 4 seconds
            @Override
            public void run() {
                Intent intent = new Intent(splash_activity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },time_out);
    }
}