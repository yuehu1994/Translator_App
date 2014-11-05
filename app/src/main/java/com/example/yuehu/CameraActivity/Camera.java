package com.example.yuehu.CameraActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.yuehu.translator.MainActivity;
import com.example.yuehu.translator.R;


public class Camera extends Activity{

    //Yue wrote this-rachel-change if you want!
    private String inputString;
    private String outputString;

    public void backButton(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //overides

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_view);

        //Yue also wrote this-should be ok:
        Intent intent = getIntent();
        String[] passedInfo = intent.getStringArrayExtra("MainActivity.MESSAGE");
        inputString = passedInfo[0];
        outputString = passedInfo[1];
        ((TextView)findViewById(R.id.inputText)).setText("Translate: "+ inputString);
        ((TextView)findViewById(R.id.outputText)).setText("To: "+ outputString);
    }





}
