package com.nagirescue;

import android.app.Fragment;
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
                    String type,sex,color,breed, height,collared,tagged,location,time,email,
                            firstName,lastName,phone,other,issueType;
                    type= ((EditText)(rootView.findViewById(R.id.type))).toString();
                    sex= ((EditText)(rootView.findViewById(R.id.sex))).toString();
                    color= ((EditText)(rootView.findViewById(R.id.color))).toString();
                    breed= ((EditText)(rootView.findViewById(R.id.breed))).toString();
                    height= ((EditText)(rootView.findViewById(R.id.height))).toString();
                    collared= ((EditText)(rootView.findViewById(R.id.collared))).toString();
                    tagged= ((EditText)(rootView.findViewById(R.id.tagged))).toString();
                    location= ((EditText)(rootView.findViewById(R.id.location))).toString();
                    time= ((EditText)(rootView.findViewById(R.id.time))).toString();
                    email= ((EditText)(rootView.findViewById(R.id.email))).toString();
                    firstName= ((EditText)(rootView.findViewById(R.id.firstName))).toString();
                    lastName= ((EditText)(rootView.findViewById(R.id.lastName))).toString();
                    phone= ((EditText)(rootView.findViewById(R.id.phone))).toString();
                    other= ((EditText)(rootView.findViewById(R.id.other))).toString();
                    issueType= "found";

                    final String urlStr = "http://nagifound-pcqzft2why.elasticbeanstalk.com/nagi/postlostfound";
                    URL url = new URL(urlStr);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");

                    MultiPartHelper multipart = new MultiPartHelper(connection);
                    multipart.addStringPart("dog","type");
                    multipart.addStringPart("dog","sex");
                    multipart.addStringPart("dog","color");
                    multipart.addStringPart("dog","breed");
                    multipart.addStringPart("dog","height");
                    multipart.addStringPart("dog","collared");
                    multipart.addStringPart("dog","tagged");
                    multipart.addStringPart("dog","location");
                    multipart.addStringPart("12","time");
                    multipart.addStringPart("dog","email");
                    multipart.addStringPart("dog","firstName");
                    multipart.addStringPart("dog","lastName");
                    multipart.addStringPart("dog","phone");
                    multipart.addStringPart("dog","other");
                    multipart.addStringPart("dog","issueType");
                    multipart.addStringPart("dummy","fileName");
                    multipart.addStringPart("dummy","fileObject");

                    multipart.makeRequest();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }



}
