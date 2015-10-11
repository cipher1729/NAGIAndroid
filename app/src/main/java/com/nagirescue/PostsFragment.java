package com.nagirescue;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by cipher1729 on 10/11/2015.
 */
public class PostsFragment extends Fragment {
    View rootView;
    ListView list;
    String[] itemname ={
            "Safari",
            "Camera",
            "Global",
            "FireFox",
            "UC Browser",
            "Android Folder",
            "VLC Player",
            "Cold War"
    };

    Integer[] imgid={
            R.drawable.logo,
            R.drawable.logo,
            R.drawable.logo,
            R.drawable.logo,
            R.drawable.logo,
            R.drawable.logo,
            R.drawable.logo,
            R.drawable.logo,
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int layoutId= R.layout.postslayout;
        rootView = inflater.inflate(layoutId, container, false);
        //setOnClickListeners();
        CustomListAdapter adapter=new CustomListAdapter(getActivity(), itemname, imgid);
        list=(ListView)rootView.findViewById(R.id.postsList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem= itemname[+position];
                Toast.makeText(getActivity(), Slecteditem, Toast.LENGTH_SHORT).show();

            }
        });
        return rootView;
    }


}
