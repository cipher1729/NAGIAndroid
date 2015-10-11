package com.nagirescue;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cipher1729 on 10/10/2015.
 */
public class LostFragment extends Fragment {
    View rootView;
    String type,sex,color,breed, height,collared,tagged,location,time,email,
            firstName,lastName,phone,other,issueType;
    final int TAKE_PICTURE=0;
    Uri imageUri;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int layoutId= R.layout.lostlayout;
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
    }

    private void doPostRequest()
    {
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
                    issueType = "lost";

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
                    multipart.addStringPart("lost", "requestType");
                    multipart.makeRequest();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void updateFields(MultiPartHelper multipart) {
       /* multipart.addStringPart(type, "type");
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
        multipart.addStringPart("dummy", "fileObject");*/


        multipart.addStringPart("Cat", "type");
        multipart.addStringPart("male", "sex");
        multipart.addStringPart("red", "color");
        multipart.addStringPart("gumpy", "breed");
        multipart.addStringPart("1.3", "height");
        multipart.addStringPart("true", "collared");
        multipart.addStringPart("true", "tagged");
        multipart.addStringPart("mesa", "location");
        multipart.addStringPart("15051520", "time");
        multipart.addStringPart("cipher1729@gmail.com", "email");
        multipart.addStringPart("Joe", "firstName");
        multipart.addStringPart("Doe", "lastName");
        multipart.addStringPart("11111111", "phone");
        multipart.addStringPart("none", "other");
        multipart.addStringPart(issueType, "issueType");
        multipart.addStringPart("dummy", "fileName");
        multipart.addStringPart("dummy", "fileObject");
    }

}
