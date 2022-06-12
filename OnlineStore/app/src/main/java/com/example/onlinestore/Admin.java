package com.example.onlinestore;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.util.List;

public class Admin extends AppCompatActivity {

    public UserDatabaseHelper databaseHelper;
    public StoreDatabaseHelper storeDatabaseHelper;
    ImageView PreviewImage;
    Button Btn_AddStore,Btn_RemoveStore,Remove_User,Btn_UploadImageStore,btnallusers,btnallitems;
    EditText StorePriceText,StoreNameText,UserNameText;
    Storeitem item;
    int SELECT_PICTURE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        item = new Storeitem();
        databaseHelper = new UserDatabaseHelper(Admin.this);
        storeDatabaseHelper = new StoreDatabaseHelper(Admin.this);
         PreviewImage=(ImageView)findViewById(R.id.PreviewImage);
        Btn_AddStore=(Button)findViewById(R.id.Btn_AddStore) ;
        Btn_RemoveStore=(Button)findViewById(R.id.Btn_RemoveStore) ;
        Remove_User=(Button)findViewById(R.id.Btn_RemoveUser) ;
        UserNameText=(EditText)findViewById(R.id.UserNameText);
        StoreNameText=(EditText)findViewById(R.id.StoreNameText);
        StorePriceText=(EditText)findViewById(R.id.StorePriceText);
        Btn_UploadImageStore=(Button)findViewById(R.id.Btn_UploadImageStore);
        btnallitems=(Button)findViewById(R.id.btn_alltem) ;
        btnallusers=(Button)findViewById(R.id.btn_allUser);


        Toast.makeText(Admin.this, "Welcome Admin!", Toast.LENGTH_SHORT).show();
        Btn_AddStore.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String nm=StoreNameText.getText().toString().trim();
                String price =StorePriceText.getText().toString().trim();
                Bitmap bm=((BitmapDrawable)PreviewImage.getDrawable()).getBitmap();
                    item.setName(nm);
                    item.setPrice(price);
                    item.setImage(bm);
                    storeDatabaseHelper.addStore(item);
                    Toast.makeText(Admin.this, "Added Item", Toast.LENGTH_SHORT).show();
                    Intent i1 = new Intent(Admin.this, StoreList.class);
                    i1.putExtra("EMAIL", StoreNameText.getText().toString().trim());
                    startActivity(i1);
            }
        });

        Btn_RemoveStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Storeitem item = new Storeitem();
                if(StoreNameText.getText().toString().matches("")){
                    Toast.makeText(Admin.this, "Something is Wrong!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                item.setName(StoreNameText.getText().toString().trim());
                    Toast.makeText(Admin.this, "Item Deleted", Toast.LENGTH_SHORT).show();
                storeDatabaseHelper.deleteStore(item);
                    Intent i1 = new Intent(Admin.this, StoreList.class);
                    i1.putExtra("EMAIL", StoreNameText.getText().toString().trim());
                    startActivity(i1);
                }
            }
        });

        btnallitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(Admin.this, StoreList.class);
                startActivity(i1);
                Toast.makeText(Admin.this, "All Items List", Toast.LENGTH_SHORT).show();
            }
        });

        btnallusers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(Admin.this, UsersList.class);
                startActivity(i1);
                Toast.makeText(Admin.this, "All Users List", Toast.LENGTH_SHORT).show();
            }
        });

        Remove_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                if(UserNameText.getText().toString().matches("")){
                    Toast.makeText(Admin.this, "Something is Wrong!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    user.setName(UserNameText.getText().toString().trim());
                    Toast.makeText(Admin.this, "User Removed!", Toast.LENGTH_SHORT).show();
                    databaseHelper.deleteUser(user);
                    Intent i1 = new Intent(Admin.this, UsersList.class);
                    i1.putExtra("EMAIL", UserNameText.getText().toString().trim());
                    startActivity(i1);
                }
            }
        });



        Btn_UploadImageStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

    }

    void imageChooser() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE)
            {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    PreviewImage.setImageURI(selectedImageUri);
                }
            }
        }
    }


}