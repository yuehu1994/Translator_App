package com.example.yuehu.translator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.yuehu.camera.camera_activity;

/**
This file contains all logic for the main view where languages are first selected.
 */
public class MainActivity extends Activity {

    RelativeLayout mainLayout;

    /* Global variables - be careful of changing*/

    /* the language of the image*/
    private String inputLanguage = "none";
    /* the language used in translating the image text */
    private String outputLanguage = "none";
    /* Rachel-Just a global String I used to pass param to other view. Not that important*/
    public final static String EXTRA_MESSAGE = "MainActivity.MESSAGE";

    static final String STATE_INPUT = "inputLanguage";
    static final String STATE_OUTPUT = "outputLanguage";



    /* getters for the languages */
    public String getInputLanguage(){
        return inputLanguage;
    }

    public String getOutputLanguage(){
        return outputLanguage;
    }

    /*
        @param  view
        @return void
        @info   setter function used to update global inputLanguage variable
     */
    public void fromSetClick(View view){
        switch(view.getId()){
            case R.id.radioButtonFromEnglish:
               inputLanguage = "english";
                break;
            case R.id.radioButtonFromFrench:
                inputLanguage = "french";
                break;
            case R.id.radioButtonFromSpanish:
                inputLanguage = "spanish";
                break;
        }
    }

    /*
        @param  view
        @return void
        @info   setter function used to update global outputLanguage variable
    */
    public void toSetClick(View view){
        switch(view.getId()) {
            case R.id.radioButtonToEnglish:
                outputLanguage = "english";
                break;
            case R.id.radioButtonToFrench:
                outputLanguage = "french";
                break;
            case R.id.radioButtonToSpanish:
                outputLanguage="spanish";
                break;
        }
    }

    /*
        @param  view
        @return void
        @info   function called with Go! button pressed, checks input and launches camera activity
    */
    public void toCamera(View view){
        /* Error checking - user hasn't selected either or just one input language */
        if(getInputLanguage().equals("none") || getOutputLanguage().equals("none")){
            Context context = getApplicationContext();
            CharSequence toastText = "Please select two languages";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context,toastText,duration);
            toast.show();
        }
        /* both languages selected, launch camera view */
        else{
            Intent intent = new Intent(this, camera_activity.class);
            String[] message = new String[]{inputLanguage,outputLanguage};      //simple parse in next view
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputLanguage = "none";
        outputLanguage = "none";
        /*mainLayout=(RelativeLayout)findViewById(R.id.myLayout);
        mainLayout.setBackgroundResource(R.drawable.wallpaper);*/

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        inputLanguage = savedInstanceState.getString(STATE_INPUT);
        outputLanguage = savedInstanceState.getString(STATE_OUTPUT);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putString(STATE_INPUT, inputLanguage);
        savedInstanceState.putString(STATE_OUTPUT, outputLanguage);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
