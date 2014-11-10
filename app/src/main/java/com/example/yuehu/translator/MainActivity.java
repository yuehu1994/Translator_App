package com.example.yuehu.translator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.yuehu.CameraActivity.Camera;

/**
This file contains all logic for the main view where languages are first selected.
 */
public class MainActivity extends Activity {

    /* Global variables - be careful of changing*/

    /* the language of the image*/
    private String inputLanguage = "none";
    /* the language used in translating the image text */
    private String outputLanguage = "none";
    /* TODO: find out what this is*/
    public final static String EXTRA_MESSAGE = "MainActivity.MESSAGE";

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
            Intent intent = new Intent(this, Camera.class);
            String[] message = new String[]{inputLanguage,outputLanguage};      //simple parse in next view
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);                                     //
        setContentView(R.layout.activity_main);
        inputLanguage = "none";
        outputLanguage = "none";
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
