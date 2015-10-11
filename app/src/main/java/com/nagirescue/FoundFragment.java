package com.nagirescue;

import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
    }

    private void doPostRequest()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url= "http://nagifound-pcqzft2why.elasticbeanstalk.com/nagi/postlostfound";
                    URL myUrl = new URL(url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)myUrl.openConnection();

                    /*build query*/
                    Uri.Builder builder = new Uri.Builder();

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



                    if(type!=null) builder.appendQueryParameter("type", "dog");
                    if(sex!=null) builder.appendQueryParameter("sex", "male");
                    if(color!=null) builder.appendQueryParameter("color", "black");
                    if(breed!=null)  builder.appendQueryParameter("breed", "dalmatian");
                    if(height!=null) builder.appendQueryParameter("height", "1.3");
                    if(collared!=null) builder.appendQueryParameter("collared", "yes");
                    if(tagged!=null) builder.appendQueryParameter("tagged", "yes");
                    if(location!=null) builder.appendQueryParameter("location","gilbert");
                    if(time!=null) builder.appendQueryParameter("time", "8pm");
                    if(email!=null) builder.appendQueryParameter("email", "abc");
                    if(firstName!=null) builder.appendQueryParameter("firstName", "jo");
                    if(lastName!=null) builder.appendQueryParameter("lastName", "do");
                    if(phone!=null) builder.appendQueryParameter("phone", "4040404");
                    if(other!=null)  builder.appendQueryParameter("other", "other");
                    if(issueType!=null) builder.appendQueryParameter("issueType", issueType);


                    String query = builder.build().getEncodedQuery();

                    // httpURLConnection.connect();
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setRequestMethod("POST");
                    OutputStream os = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));


                    bufferedWriter.write(query);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    httpURLConnection.connect();

                    InputStream is = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(is,"UTF-8"));
                    String line;
                    StringBuilder sb = new StringBuilder();
                    line= bufferedReader.readLine();
                    while(line!=null)
                    {
                        sb.append(line);
                        line= bufferedReader.readLine();
                    }

                    is.close();
                    os.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        }
}
