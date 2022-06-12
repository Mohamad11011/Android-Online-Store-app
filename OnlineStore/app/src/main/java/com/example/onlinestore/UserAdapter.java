package com.example.onlinestore;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> listUsers;
    public UserAdapter(List<User> listUsers) {
        this.listUsers = listUsers;
    }
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_recycler, parent, false);
        return new UserViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.textViewName.setText(listUsers.get(position).getName());
        holder.textViewEmail.setText(listUsers.get(position).getEmail());
        holder.textViewPassword.setText(listUsers.get(position).getPassword());
    }
    @Override
    public int getItemCount() {
        Log.v(UserAdapter.class.getSimpleName(),""+listUsers.size());
        return listUsers.size();
    }


    public class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewEmail;
        public TextView textViewPassword;
        public UserViewHolder(View view) {
            super(view);
            textViewName = (TextView) view.findViewById(R.id.textViewName);
            textViewEmail = (TextView) view.findViewById(R.id.textViewEmail);
            textViewPassword = (TextView) view.findViewById(R.id.textViewPassword);
        }
    }
}
