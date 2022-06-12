package com.example.onlinestore;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;



public class Account extends Fragment {
    RelativeLayout call,whatsapp;
    ImageButton sign,register;


    public Account() {// Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View viewer = inflater.inflate(R.layout.fragment_account, container, false);
        whatsapp=(RelativeLayout) viewer.findViewById(R.id.whatsapp);
        call=(RelativeLayout) viewer.findViewById(R.id.call);
        sign=(ImageButton) viewer.findViewById(R.id.sign);
        register=(ImageButton) viewer.findViewById(R.id.register);

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String contact = "+961 70307596";
                    String url = "https://api.whatsapp.com/send?phone="+contact;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+961 70307596"));
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},10);
                    return;
                }
                else
                    {
                    try{
                        Toast.makeText(getActivity(), "Calling Company", Toast.LENGTH_SHORT).show();
                        startActivity(callIntent);
                    }
                    catch (android.content.ActivityNotFoundException ex){
                        Toast.makeText(getActivity()," Activity is not available",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });


        // Inflate the layout for this fragment
        return viewer;
    }

}