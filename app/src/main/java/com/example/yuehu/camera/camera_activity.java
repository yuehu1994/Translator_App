package com.example.yuehu.camera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.example.yuehu.translator.R;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.StringTokenizer;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
//import com.google.api.translate.Language;
//import com.google.api.translate.Translate;
//import com.google.api.GoogleAPI;

//import com.memetix.mst.language.Language;
//import com.memetix.mst.translate.Translate;


public class camera_activity extends Activity{
    private String apiBrowserKey = "AIzaSyBZ4Ma9zIaBjTueqRD_pr9of3gsPwdyJ6I";
    private String apiAndroidKey = "AIzaSyBPhcr6T60YYvDlRJIZQ5xDRMo4UrwkBsU";
    private String outputString;
    private String spokenString;
    private String translatedString;
    static final String STATE_OUTPUT = "outputLanguage";
    static final String STATE_TEXT = "spokenString";
    static final String STATE_TRANS="translatedString";


    public void translate(){
        StringBuilder result = new StringBuilder();
        InputStream myStream;
        try {
/*
            //GOOGLE TRANSLATE STUFF
            GoogleAPI.setHttpReferrer("http://www.google.com");
            GoogleAPI.setKey("AIzaSyBPhcr6T60YYvDlRJIZQ5xDRMo4UrwkBsU");
            translatedString=Translate.DEFAULT.execute(spokenString, Language.ENGLISH, Language.FRENCH);//replace with getNewLanguage()
            ((TextView)findViewById(R.id.displayOutput)).setText(translatedString);
*/

            //BING TRANSLATE STUFF

        /*
            Translate.setClientId("YueTranslator");
            Translate.setClientSecret("wgBD1d+s+in4cQyJGyZM+KVhr8E4o2I3Z8xY9+o0mQE=");
            translatedString=Translate.execute(spokenString, Language.ENGLISH, Language.FRENCH);//replace with getNewLanguage()
            ((TextView)findViewById(R.id.displayOutput)).setText(translatedString);

        */

        String newLanguage = getNewLanguage();
        String encodedText = URLEncoder.encode(spokenString,"UTF-8");
        String urlString = "https://www.googleapis.com/language/translate/v2?key=" +apiBrowserKey + "&source=en"+"&target=" + newLanguage+ "&q=" + encodedText;
        //((TextView)findViewById(R.id.displayOutput)).setText(urlString);

        URL url = new URL(urlString);
        HttpsURLConnection connect = (HttpsURLConnection) url.openConnection();
        //((TextView)findViewById(R.id.displayInput)).setText(Integer.toString(connect.getResponseCode()));
        if(connect.getResponseCode() != 200) {    //Failed
            String errorMessage = "Https Connection Failed";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        myStream = connect.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(myStream));
        String line;
        while((line = reader.readLine())!= null){
            result.append(line);
        }
        JsonParser myParser = new JsonParser();
        JsonElement myElement = myParser.parse(result.toString());
        JsonObject myObject= myElement.getAsJsonObject();
        translatedString = myObject.get("data").getAsJsonObject().get("translations").getAsJsonArray().get(0).getAsJsonObject().get("translatedText").getAsString();//parses the  json
        ((TextView)findViewById(R.id.displayOutput)).setText(translatedString);


        }
        catch(Exception e){
            e.getMessage();//get rid of this line when done debugging
            String errorMessage = "Translation Error";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();

        }

    }

    public String getNewLanguage(){
        String newLanguage;
        if(outputString.equals("german")){
            newLanguage = "de";
        }
        else if(outputString.equals("french")){
            newLanguage = "fr";
        }
        else if(outputString.equals("spanish")){
            newLanguage = "es";
        }
        else if(outputString.equals("chinese")){
            newLanguage = "zh_TW";
        }
        else if(outputString.equals("greek")){
            newLanguage = "el";
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
