package com.example.yuehu.translator;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.content.pm.PackageManager;
import android.speech.RecognizerIntent;
import android.content.pm.ResolveInfo;
import java.util.ArrayList;
import java.util.List;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.yuehu.camera.camera_activity;


/**
This file contains all logic for the main view where languages are first selected.
 */
public class MainActivity extends Activity {
    /* Global variables - be careful of changing*/

    /* the language used in translating the image text */
    private String outputLanguage = "none";
    private String textMessage;
    /* global String used to pass param to other view */
    public final static String EXTRA_MESSAGE = "MainActivity.MESSAGE";
    private ListView listOfText;
    private Dialog select_result;
    /* string for saving state - identifies the class variable being saved */
    static final String STATE_OUTPUT = "outputLanguage";
    static final int VOICE_CODE = 1;

    public String getOutputLanguage(){
        return outputLanguage;
    }

    /*
        @param  view
        @return void
        @info   setter function used to update global outputLanguage variable
    */
    public void toSetClick(View view){
        switch(view.getId()) {
            case R.id.radioButtonToGerman:
                outputLanguage = "german";
                break;
            case R.id.radioButtonToFrench:
                outputLanguage = "french";
                break;
            case R.id.radioButtonToSpanish:
                outputLanguage="spanish";
                break;
            case R.id.radioButtonToChinese:
                outputLanguage="chinese";
                break;
            case R.id.radioButtonToAfrikaans:
                outputLanguage="afrikaans";
                break;
        }
    }


    public boolean internetConnection(){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        }
        else
            return false;
    }
    /*
        @param  view
        @return void
        @info   function called with Go! button pressed, checks input and launches camera activity
    */
    public void toRecorder(View view){
        /* Error checking - user hasn't selected either or just one input language */
        if(getOutputLanguage().equals("none")){
            Context context = getApplicationContext();
            CharSequence toastText = "Please select a language";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context,toastText,duration);
            toast.show();
        }
        /* a language selected, launch camera view */
        else{
            startVoice();
        }

    }

    /*
    This function starts voice command recognition
     */
    private void startVoice(){
        Intent voiceIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(voiceIntent, VOICE_CODE);
    }

    /*
    This function handles the return from voice
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==1){             //we are coming back from voice command
            try {
                final ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                //Dialog courtesy of http://www.learn2crack.com/2013/12/android-speech-recognition-example.html
                select_result = new Dialog(MainActivity.this);  //creating new dialog
                select_result.setContentView(R.layout.dialog_box); //set up dialog box
                select_result.setTitle("Select Text");
                listOfText = (ListView)select_result.findViewById(R.id.dialogBox);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results);
                listOfText.setAdapter(adapter);
                final Intent intent = new Intent(this, camera_activity.class);
                listOfText.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id ) {
                        textMessage = results.get(position);
                        select_result.hide();
                        String[] message = new String[]{outputLanguage, textMessage};
                        intent.putExtra(EXTRA_MESSAGE, message);
                        startActivity(intent);
                    }
                });
                select_result.show();

            }
            catch(NullPointerException exception){
                String errorMessage = "Please try again";
                Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }
        }



    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outputLanguage = "none";
        /*Check to see if recognition is present in device
        * If not present, display toast and disable the button*/
        PackageManager manager = getPackageManager();
        List<ResolveInfo> allActivities = manager.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH),0);
        if(allActivities.size()==0){
            (findViewById(R.id.go_button)).setEnabled(false);
            Context context = getApplicationContext();
            CharSequence toastText = "Voice recognition not present";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context,toastText,duration);
            toast.show();
        }
        //make sure internet connection is available
        if(!internetConnection()){
            (findViewById(R.id.go_button)).setEnabled(false);
            Context context = getApplicationContext();
            CharSequence toastText = "No internet access";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context,toastText,duration);
            toast.show();
        }

    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        /*inputLanguage = savedInstanceState.getString(STATE_INPUT);*/
        outputLanguage = savedInstanceState.getString(STATE_OUTPUT);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        /*savedInstanceState.putString(STATE_INPUT, inputLanguage);*/
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
