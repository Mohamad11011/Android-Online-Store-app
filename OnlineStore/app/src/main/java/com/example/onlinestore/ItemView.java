package com.example.onlinestore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ItemView  extends  AppCompatActivity {

    StoreDatabaseHelper databaseHelper;

    private final AppCompatActivity activity = ItemView.this;

    LinearLayout item_recycler;
    TextView textViewName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.store_item_recycler);

        item_recycler = (LinearLayout) findViewById(R.id.item_recycler);
        textViewName = (TextView) findViewById(R.id.textViewName);

    }
}
