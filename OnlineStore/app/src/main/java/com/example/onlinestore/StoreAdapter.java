package com.example.onlinestore;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {
    private List<Storeitem> listStore;
    private View.OnClickListener listener;
    public SharedPreferences sharedpreferences;
    public static final String myPreference = "myPref";
    public static  String Name = "nameKey";
    public static  String Price = "priceKey";
    public TextView textViewName;
    public TextView textViewPrice;

    Context context;

    public interface OnItemClickListener {
        void onItemClick(Storeitem item);

    }

    public StoreAdapter(List<Storeitem> listStore) {
        this.listStore = listStore;
        this.listener = listener;

    }

    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_item_recycler, parent, false);

        return new StoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StoreViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textViewName.setText(listStore.get(position).getName());
        holder.textViewPrice.setText(listStore.get(position).getPrice());
        holder.Image.setImageBitmap(listStore.get(position).getImage());
    }

    @Override
    public int getItemCount()
    {
        Log.v(StoreAdapter.class.getSimpleName(), "" + listStore.size());
        return listStore.size();
    }

    public class StoreViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewPrice;
        public ImageView Image;

        public StoreViewHolder(View view)
        {
            super(view);

            textViewName = view.findViewById(R.id.textViewName);
            textViewPrice = view.findViewById(R.id.textViewPrice);
            Image=view.findViewById(R.id.Image);

            Image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    SharedPreferences.Editor editor = view.getContext()
                            .getSharedPreferences(myPreference,Context.MODE_PRIVATE).edit();


                    String Name1=textViewName.getText().toString().trim();
                    String Price1=textViewPrice.getText().toString().trim();
                    editor.putString("nameKey", Name1);
                    editor.putString("priceKey", Price1);
                    editor.apply();
                    Toast.makeText(view.getContext(), "Added to Cart", Toast.LENGTH_SHORT).show();
                }
            });

            view.setTag(this);
            view.setOnClickListener(listener);
        }

        public void setOnItemClickListener(View.OnClickListener l) {
            listener = l;

        }

    }
}

