package com.nagirescue;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cipher1729 on 10/10/2015.
 */
public class FoundFragment extends Fragment {
    View rootView;
    String type,sex,color,breed, height,collared,tagged,location,time,email,
            firstName,lastName,phone,other,issueType;
    final int TAKE_PICTURE=0;
    Uri imageUri;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int layoutId= R.layout.findlayout;
        rootView = inflater.inflate(layoutId, container, false);
        setOnClickListeners();
        return rootView;
    }

    private void setOnClickListeners() {
        //lost for found button clicks
        ((Button)rootView.findViewById(R.id.submitBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doPostRequest();
            }
        });

        ((Button)rootView.findViewById(R.id.cameraBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photo));
               imageUri = Uri.fromFile(photo);
                startActivityForResult(intent, TAKE_PICTURE);
            }
        });


    }

    private void doPostRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {


                /*Read values from form*/

                    type = ((EditText) (rootView.findViewById(R.id.type))).toString();
                    sex = ((EditText) (rootView.findViewById(R.id.sex))).toString();
                    color = ((EditText) (rootView.findViewById(R.id.color))).toString();
                    breed = ((EditText) (rootView.findViewById(R.id.breed))).toString();
                    height = ((EditText) (rootView.findViewById(R.id.height))).toString();
                    collared = ((EditText) (rootView.findViewById(R.id.collared))).toString();
                    tagged = ((EditText) (rootView.findViewById(R.id.tagged))).toString();
                    location = ((EditText) (rootView.findViewById(R.id.location))).toString();
                    time = ((EditText) (rootView.findViewById(R.id.time))).toString();
                    email = ((EditText) (rootView.findViewById(R.id.email))).toString();
                    firstName = ((EditText) (rootView.findViewById(R.id.firstName))).toString();
                    lastName = ((EditText) (rootView.findViewById(R.id.lastName))).toString();
                    phone = ((EditText) (rootView.findViewById(R.id.phone))).toString();
                    other = ((EditText) (rootView.findViewById(R.id.other))).toString();
                    issueType = "found";

                    String urlStr = "http://nagifound-pcqzft2why.elasticbeanstalk.com/nagi/postlostfound";
                    URL url = new URL(urlStr);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");

                    MultiPartHelper multipart = new MultiPartHelper(connection);
                    updateFields(multipart);
                    multipart.makeRequest();

                    //get other results for display
                    urlStr = "http://nagifound-pcqzft2why.elasticbeanstalk.com/nagi/getlostfound";
                    url = new URL(urlStr);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    multipart = new MultiPartHelper(connection);
                    updateFields(multipart);
                    multipart.addStringPart("found", "requestType");
                    multipart.makeRequest();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void updateFields(MultiPartHelper multipart) {
        multipart.addStringPart(type, "type");
        multipart.addStringPart(sex, "sex");
        multipart.addStringPart(color, "color");
        multipart.addStringPart(breed, "breed");
        multipart.addStringPart(height, "height");
        multipart.addStringPart(collared, "collared");
        multipart.addStringPart(tagged, "tagged");
        multipart.addStringPart(location, "location");
        multipart.addStringPart(time, "time");
        multipart.addStringPart(email, "email");
        multipart.addStringPart(firstName, "firstName");
        multipart.addStringPart(lastName, "lastName");
        multipart.addStringPart(phone, "phone");
        multipart.addStringPart(other, "other");
        multipart.addStringPart(issueType, "issueType");
        multipart.addStringPart("dummy", "fileName");
        multipart.addStringPart("dummy", "fileObject");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getActivity().getContentResolver().notifyChange(selectedImage, null);
                    ImageView imageView = (ImageView)rootView.findViewById(R.id.imageView);
                    ContentResolver cr = getActivity().getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);
                        imageView.setScaleType(ImageView.ScaleType.FIT_START);
                        imageView.setVisibility(View.VISIBLE);
                        rootView.findViewById(R.id.cameraBtn).setVisibility(View.GONE);

                        imageView.setImageBitmap(bitmap);
                        Toast.makeText(getActivity(), selectedImage.toString(),
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }

}
