package com.example.onlinestore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Map extends Fragment {

    Button btnMap;

    public Map() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewer = inflater.inflate(R.layout.map_store, container, false);
        btnMap=(Button) viewer.findViewById(R.id.btnMap);

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getActivity(), Map1.class);
                startActivity(i1);
            }
        });

        return  viewer;
    }


}
