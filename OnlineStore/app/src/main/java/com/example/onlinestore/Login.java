package com.example.onlinestore;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;



public class Login extends AppCompatActivity  {
    private final AppCompatActivity activity = Login.this;

    private InputValidation inputValidation;
    private UserDatabaseHelper databaseHelper;
    Button Login;
    EditText textInputEditTextName,textInputEditTextPassword;
    TextView textPass,textEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        databaseHelper = new UserDatabaseHelper(activity);
        inputValidation = new InputValidation(activity);

        textInputEditTextName = (EditText) findViewById(R.id.textInputEditTextName);
        textInputEditTextPassword = (EditText) findViewById(R.id.textInputEditTextPassword);
        Login = (Button) findViewById(R.id.appCompatButtonLogin);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFromSQLite();
            }
        });

    }



    private void checkFromSQLite() {
        String name=textInputEditTextName.getText().toString().trim();
        String pass =textInputEditTextPassword.getText().toString().trim();
        if (name.matches("") || pass.matches("") ){
            Toast.makeText(activity, "Empty Field!", Toast.LENGTH_SHORT).show();
        }
       else  if(name.matches("Admin") && pass.matches("111"))
        {
            Intent i1 = new Intent(activity, Admin.class);
            emptyEditText();
            startActivity(i1);
        }

        else if (databaseHelper.checkUser(textInputEditTextName.getText().toString().trim(),textInputEditTextPassword.getText().toString().trim()) )
        {
          Toast.makeText(activity,"Logged in Successfully!",Toast.LENGTH_SHORT).show();
            Intent i2 = new Intent(activity, StoreList.class);
            emptyEditText();
            startActivity(i2);
        }
        else
        {
            Toast.makeText(Login.this, "Log in error!", Toast.LENGTH_SHORT).show();

        }

    }


    private void emptyEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextPassword.setText(null);
    }
}
