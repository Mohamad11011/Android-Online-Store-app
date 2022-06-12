package com.example.onlinestore;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;


public class Cart extends Fragment {
    SharedPreferences sharedpreferences;
    public static final String myPreference = "myPref";
    TextView name,price;
    public static final String Name = "nameKey";
    public static final String Price = "priceKey";
    private Context mContext;
    Button Pay;
    ProgressDialog progress;

    public Cart() {  }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewer = inflater.inflate(R.layout.fragment_cart, container, false);

        name = (TextView) viewer.findViewById(R.id.textViewName);
        price = (TextView) viewer.findViewById(R.id.textViewPrice);
        Pay=(Button)viewer.findViewById(R.id.Pay);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());


        Pay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                progress = new ProgressDialog(getActivity());
                progress.setTitle("Sending Payments!");
                progress.setMessage("processing");
                progress.setCancelable(true);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.show();
                @SuppressLint("HandlerLeak") final Handler h = new Handler() {
                    @Override
                    public void handleMessage(Message message) {
                        Toast.makeText(getActivity(), "Enjoy your Item", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                };
                h.sendMessageDelayed(new Message(), 6000);

                Toast.makeText(getActivity(), "Returning to Store", Toast.LENGTH_SHORT).show();

                @SuppressLint("HandlerLeak") final Handler h1 = new Handler() {
                    @Override
                    public void handleMessage(Message message) {

                        Intent intent = new Intent(getActivity(), StoreList.class);
                        startActivity(intent);
                    }
                };
                h1.sendMessageDelayed(new Message(), 5000);
            }
        });
        String s1 = prefs.getString("nameKey","Lenovo Y2");
        String a = prefs.getString("priceKey", "880");
        name.setText(s1);
        price.setText(a);
        return viewer;
    }

}
