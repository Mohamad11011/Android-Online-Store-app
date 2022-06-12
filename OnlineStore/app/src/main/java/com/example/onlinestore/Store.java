package com.example.onlinestore;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Store extends Fragment {

Button Show;
    public Store() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View viewer = inflater.inflate(R.layout.fragment_store, container, false);
        Show=viewer.findViewById(R.id.Show);

        Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i1 = new Intent(getActivity(), StoreList.class);
                startActivity(i1);
            }
        });

        return viewer;
    }

}
