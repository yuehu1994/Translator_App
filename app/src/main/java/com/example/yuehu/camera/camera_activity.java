package com.example.yuehu.camera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    //YUE WROTE THIS--RACHEL I WILL EXPLAIN EVERYTHING
    //TODO: After taking a picture, forces you to save it image. Get rid of that feature?? Also backbutton don't work
    //TODO: Easy fix-disable back button  with camera or cropping?

    private String inputString;
    private String outputString;
    //keep track of camera capture intent
    final int CAMERA_CAPTURE = 1;
    final int PIC_CROP = 2;//just a param pass in since only 2 options we don't have to check value
    //captured picture uri
    private Uri picture;
    public void cameraClick(View view){
        try{
            //use standard intent to capture an image
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //we will handle the returned data in onActivityResult
            startActivityForResult(captureIntent, CAMERA_CAPTURE);
        }
        //If device has no camera... catch the exception
        catch(ActivityNotFoundException anfe){
            String errorMessage = "Device does not support capturing images.";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    //This responds to the startActivity for result
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
       if(requestCode==1){//meaning we are coming back from camera
            picture = data.getData();           //This gets the data returned from the picture taken.
            cropPicture();}
        else{
           //TODO:Cropped picture is now new picture. DO I SAVE IT TO PICTURE AGAIN? FIGURE THIS OUT
            Bundle extras = data.getExtras();
            Bitmap croppedPicture = extras.getParcelable("data");   //gets the cropped picture from the data
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
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
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
    //overides
    //testing comments
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_view);

        Intent intent = getIntent();
        String[] passedInfo = intent.getStringArrayExtra("MainActivity.MESSAGE");
        inputString = passedInfo[0];
        outputString = passedInfo[1];
        ((TextView)findViewById(R.id.translateInfo)).setText("Translate "+ inputString + " to " + outputString);
    }





}
