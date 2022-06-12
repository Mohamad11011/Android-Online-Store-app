package com.example.onlinestore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StoreList extends AppCompatActivity {

    private AppCompatActivity activity = StoreList.this;
    private List<Storeitem> listStore;
    private StoreAdapter storeRecyclerAdapter;
    private StoreDatabaseHelper SdatabaseHelper;
    private RecyclerView recyclerViewStore;
    private TextView textViewName;
    ImageView Image;
    LinearLayout item_recycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_listing);
        textViewName = (TextView) findViewById(R.id.textViewName);
        recyclerViewStore = (RecyclerView) findViewById(R.id.recyclerViewStore);
        item_recycler = (LinearLayout) findViewById(R.id.item_recycler);
        Image=(ImageView) findViewById(R.id.Image);

        listStore = new ArrayList<>();
        storeRecyclerAdapter = new StoreAdapter(listStore);
        SdatabaseHelper=new StoreDatabaseHelper(StoreList.this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewStore.setLayoutManager(mLayoutManager);
        recyclerViewStore.setItemAnimator(new DefaultItemAnimator());
        recyclerViewStore.setHasFixedSize(true);
        recyclerViewStore.setAdapter(storeRecyclerAdapter);
        getDataFromSQLite();
    }


    private void getDataFromSQLite() {

        new  AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listStore.clear();
                listStore.addAll(SdatabaseHelper.getAllStore());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                storeRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}