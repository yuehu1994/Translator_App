package com.example.yuehu.camera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ActivityNotFoundException;
import com.example.yuehu.translator.R;

//import com.google.api.translate.Language;
//import com.google.api.translate.Translate;
//import com.google.api.GoogleAPI;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;


public class camera_activity extends Activity{

    private String outputString;
    private String spokenString;
    private String translatedString;
    static final String STATE_OUTPUT = "outputLanguage";
    static final String STATE_TEXT = "spokenString";
    static final String STATE_TRANS="translatedString";


    //WE NEED TO GET A KEY FOR THIS TO WORK
    public void translate(){

        try {
            /*
            THIS IS CURRENTLY BROKEN-EXCEPTION IS CAN"T GET TRANSLATED INFORMATION
            I THINK THE GOOGLE API KEY I SET UP DOESN"T WORK RIGHT-WILL TRY TO FIX LATER.
            I"M ON A 60 DAY FREE TRIAL FOR IT AND I THINK IM MISSING SOMETHING
            ALSO I DONT KNOW WHAT SET HTTP REFERRER DOES BUT LOOKS FROM MY EXAMPLES YOU CAN PUT ANYTHING...
             */

  //          GoogleAPI.setHttpReferrer("http://www.google.com");
  //          GoogleAPI.setKey("AIzaSyBPhcr6T60YYvDlRJIZQ5xDRMo4UrwkBsU");
            Translate.setClientId("TranslatorYueHu");
            Translate.setClientSecret("YlRnOgykvJljxs2oleRyp6wVW0M8HSV/goQXdYICI0s=");
            translatedString=Translate.execute(spokenString, Language.ENGLISH, Language.FRENCH);
            ((TextView)findViewById(R.id.displayOutput)).setText(translatedString);
        }
        catch(Exception e){
            e.printStackTrace();
            String errorMessage = "Translation Error";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();

        }

    }

    public Language getNewLanguage(){
        Language newLanguage;
        if(outputString.equals("german")){
            newLanguage = Language.GERMAN;
        }
        else if(outputString.equals("french")){
            newLanguage = Language.FRENCH;
        }
        else if(outputString.equals("spanish")){
            newLanguage = Language.SPANISH;
        }
        else if(outputString.equals("chinese")){
            newLanguage = Language.CHINESE_TRADITIONAL;
        }
        else if(outputString.equals("greek")){
            newLanguage = Language.GREEK;
        }
        else {
            String errorMessage = "Language Selection Error";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
            return null;
        }
        return newLanguage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_view);

        Intent intent = getIntent();
        String[] passedInfo = intent.getStringArrayExtra("MainActivity.MESSAGE");

        outputString = passedInfo[0];
        spokenString = passedInfo[1];

        String upperOutput = outputString.substring(0,1).toUpperCase()+outputString.substring(1);
        ((TextView)findViewById(R.id.translateTo)).setText("To " + upperOutput +":");
        ((TextView)findViewById(R.id.displayInput)).setText(spokenString);
        translate();
    }


    //Screen rotation breaks
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putString(STATE_OUTPUT, outputString);
        savedInstanceState.putString(STATE_TEXT,spokenString);
        savedInstanceState.putString(STATE_TRANS,translatedString);

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
        spokenString = savedInstanceState.getString(STATE_TRANS);
    }

}
