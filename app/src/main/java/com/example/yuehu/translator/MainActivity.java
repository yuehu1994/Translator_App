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
 * Yue Hu Wrote This File
 */
public class MainActivity extends Activity {

    private String inputLanguage = "none";                               //these are global! watch out when changing
    private String outputLanguage = "none";
    public final static String EXTRA_MESSAGE = "MainActivity.MESSAGE";                   //pass this

    public String getInputLanguage(){
        return inputLanguage;
    }               //getters

    public String getOutputLanguage(){
        return outputLanguage;
    }


    public void translateSetClick(View view){
        switch(view.getId()){
            case R.id.radioButtonEnglish:
               inputLanguage = "english";                //select
                break;
            case R.id.radioButtonFrench:
                    inputLanguage = "french";                  //select
                break;
            case R.id.radioButtonSpanish:
                    inputLanguage = "spanish";
                break;
        }

    }
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


    public void toCamera(View view){
        if(getInputLanguage().equals("none") || getOutputLanguage().equals("none")){
            Context context = getApplicationContext();
            CharSequence toastText = "Please select languages";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context,toastText,duration);
            toast.show();
        }
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
