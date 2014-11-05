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
                if(inputLanguage.equals("none")){
                    inputLanguage = "english";}                 //select
                else if(inputLanguage.equals("english")){
                    inputLanguage = "none";                     //deselect
                }
                break;
            case R.id.radioButtonFrench:
                if(inputLanguage.equals("none")){
                    inputLanguage = "french";}                  //select
                else if(inputLanguage.equals("french")){
                    inputLanguage = "none";                     //deselect
                }
                break;
            case R.id.radioButtonSpanish:
                if(inputLanguage.equals("none")){
                    inputLanguage = "spanish";}
                else if(inputLanguage.equals("spanish")){
                    inputLanguage = "none";
                }
                break;
        }

    }
    public void toSetClick(View view){
        switch(view.getId()) {
            case R.id.radioButtonEnglish:
                if(outputLanguage.equals("none")){
                    outputLanguage = "english";}                 //select
                else if(outputLanguage.equals("english")){
                    outputLanguage = "none";                     //deselect
                }
                break;
            case R.id.radioButtonFrench:
                if(outputLanguage.equals("none")){
                    outputLanguage = "french";}                  //select
                else if(outputLanguage.equals("french")){
                    outputLanguage = "none";                     //deselect
                }
                break;
            case R.id.radioButtonSpanish:
                if(outputLanguage.equals("none")){
                    outputLanguage="spanish";
                }
                else if(outputLanguage.equals("spanish")){
                    outputLanguage = "none";
                }
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
