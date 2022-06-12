package com.example.onlinestore;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.zip.Inflater;


public class Home extends Fragment  {

Button Shop;
Button Visit;
    public  Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        View viewer =inflater.inflate(R.layout.fragment_home, container, false);
        Shop=viewer.findViewById(R.id.Shopnow);
        Shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), StoreList.class);
                startActivity(intent);
            }
        });

        Visit=viewer.findViewById(R.id.Visit);
        Visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), VisitUs.class);
                startActivity(intent);
            }
        });



        return viewer;
    }

}
