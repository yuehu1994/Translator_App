package com.example.yuehu.camera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.example.yuehu.translator.R;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class camera_activity extends Activity{
    private String apiBrowserKey = "AIzaSyBZ4Ma9zIaBjTueqRD_pr9of3gsPwdyJ6I"; //api key
    private String outputString;
    private String spokenString;
    private String translatedString = "";
    static final String STATE_OUTPUT = "outputLanguage";
    static final String STATE_TEXT = "spokenString";
    static final String STATE_TRANS="translatedString";


    public void translate(){
        new Thread(new Runnable() { // start a new thread to run url query
            @Override
            public void run() {
                try {

                    StringBuilder result = new StringBuilder();
                    InputStream myStream;
                    String newLanguage = getNewLanguage();
                    String encodedText = URLEncoder.encode(spokenString, "UTF-8");
                    String urlString = "https://www.googleapis.com/language/translate/v2?key=" + apiBrowserKey + "&source=en" + "&target=" + newLanguage + "&q=" + encodedText;

                    URL url = new URL(urlString);
                    HttpsURLConnection connect = (HttpsURLConnection) url.openConnection(); //connect
                    myStream = connect.getInputStream(); //get the input from the connection
                    BufferedReader reader = new BufferedReader(new InputStreamReader(myStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }//at this point, result is now holding a json
                    JsonParser myParser = new JsonParser();//using gson to parse json
                    JsonElement myElement = myParser.parse(result.toString());
                    JsonObject myObject = myElement.getAsJsonObject();
                    translatedString = myObject.get("data").getAsJsonObject().get("translations").getAsJsonArray().get(0).getAsJsonObject().get("translatedText").getAsString();//parses the  json
                    findViewById(R.id.displayOutput).post(new Runnable() {
                        @Override
                        public void run() {
                            ((TextView)findViewById(R.id.displayOutput)).setText(translatedString);//post result to here
                        }
                    });

                }
                catch(Exception e){


                }
            }
        }).start();


    }
/*
This function is a selector for the new language. Purpose of this is to format the string correctly to queue the url
 */
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
