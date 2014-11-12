package com.example.yuehu.camera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;
import android.content.ActivityNotFoundException;
import com.example.yuehu.translator.MainActivity;
import com.example.yuehu.translator.R;


public class camera_activity extends Activity{

    private String inputString;
    private String outputString;
    //captured picture uri
    private Uri picture;
    private Bitmap croppedPicture;

    //keep track of camera capture intent
    final int CAMERA_CAPTURE = 1;
    final int PIC_CROP = 2;//just a param pass in since only 2 options we don't have to check value


    static final String STATE_INPUT = "inputLanguage";
    static final String STATE_OUTPUT = "outputLanguage";
    static final String STATE_PICTURE = "picture";
    static final String STATE_CROPPED = "croppedPicture";

    public void cameraClick(View view){
        try{
            //use standard intent to capture an image
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //we will handle the returned data in onActivityResult
            startActivityForResult(captureIntent, CAMERA_CAPTURE);
        }
        //If device has no camera... catch the exception
        catch(ActivityNotFoundException e){
            String errorMessage = "Device does not support capturing images.";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    //This responds to the startActivity for result
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
       if(requestCode==1){//meaning we are coming back from camera
           try {
               picture = data.getData();           //This gets the data returned from the picture taken.
               cropPicture();
           }
           catch (NullPointerException exception) {
               String errorMessage = "Please take a picture to translate.";
               Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
               toast.show();
           }
       }
        else{
           //TODO:Cropped picture is now new picture. DO I SAVE IT TO PICTURE AGAIN? FIGURE THIS OUT
           try {
               Bundle extras = data.getExtras();
               croppedPicture = extras.getParcelable("data");   //gets the cropped picture from the data
               ImageView displayed = (ImageView) findViewById(R.id.cropped);
               displayed.setImageBitmap(croppedPicture);//Just sets old screen to new cropped. Delete stage 4
           }
           catch (NullPointerException exception) {
               String errorMessage = "Please take a picture to translate.";
               Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
               toast.show();
           }
       }
    }

    //Helper function to crop a picture
    private void cropPicture(){
        try{
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(picture, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 0);
            cropIntent.putExtra("aspectY", 0);
            //indicate output X and Y

            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);

            cropIntent.putExtra("scale", true);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        }
        catch(ActivityNotFoundException anfe){
            String errorMessage = "Device does not support cropping.";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_view);

        Intent intent = getIntent();
        String[] passedInfo = intent.getStringArrayExtra("MainActivity.MESSAGE");
        inputString = passedInfo[0];
        outputString = passedInfo[1];
        ((TextView)findViewById(R.id.translateInfo)).setText("Translate "+ inputString + " to " + outputString);
        ((ImageView)findViewById(R.id.cropped)).setImageBitmap(croppedPicture);
    }


    //Screen rotation breaks
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putString(STATE_INPUT, inputString);
        savedInstanceState.putString(STATE_OUTPUT, outputString);
        savedInstanceState.putParcelable(STATE_PICTURE, picture);
        savedInstanceState.putParcelable(STATE_CROPPED, croppedPicture);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        inputString = savedInstanceState.getString(STATE_INPUT);
        outputString = savedInstanceState.getString(STATE_OUTPUT);
        picture = savedInstanceState.getParcelable(STATE_PICTURE);
        croppedPicture = savedInstanceState.getParcelable(STATE_CROPPED);
    }

}
