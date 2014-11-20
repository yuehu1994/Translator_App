package com.example.yuehu.camera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;
import android.content.ActivityNotFoundException;
import com.example.yuehu.translator.R;

public class camera_activity extends Activity{

    private String outputString;
    private String spokenString;

    static final String STATE_OUTPUT = "outputLanguage";
    static final String STATE_TEXT = "spokenString";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_view);

        Intent intent = getIntent();
        String[] passedInfo = intent.getStringArrayExtra("MainActivity.MESSAGE");

        outputString = passedInfo[0];
        spokenString = passedInfo[1];

        String upperOutput = outputString.substring(0,1).toUpperCase()+outputString.substring(1);
        ((TextView)findViewById(R.id.translateInfo)).setText("Translate English" + " to " + upperOutput);
        ((TextView)findViewById(R.id.displayInput)).setText(spokenString);
    }


    //Screen rotation breaks
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putString(STATE_OUTPUT, outputString);
        savedInstanceState.putString(STATE_TEXT,spokenString);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        outputString = savedInstanceState.getString(STATE_OUTPUT);
        spokenString = savedInstanceState.getString(STATE_TEXT);
    }

}
