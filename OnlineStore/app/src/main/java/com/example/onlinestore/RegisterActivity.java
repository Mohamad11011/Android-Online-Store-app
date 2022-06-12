package com.example.onlinestore;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


public class RegisterActivity extends AppCompatActivity{

    private final AppCompatActivity activity = RegisterActivity.this;

    private ConstraintLayout nestedScrollView;

    public TextView textName;
    public EditText textInputEditTextName ;
    public EditText textInputEditTextEmail;
    public EditText textInputEditTextPassword;
    public EditText textInputEditTextConfirmPassword;
    public Button ButtonRegister;

    private InputValidation inputValidation;
    private UserDatabaseHelper databaseHelper;
    public User user;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inputValidation = new InputValidation(activity);
        databaseHelper = new UserDatabaseHelper(activity);
        user = new User();

        textName = (TextView) findViewById(R.id.textInputLayoutName);
        textInputEditTextName = (EditText) findViewById(R.id.textInputEditTextName);
        textInputEditTextEmail = (EditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (EditText) findViewById(R.id.textInputLayoutPassword);
        textInputEditTextConfirmPassword = (EditText) findViewById(R.id.textInputEditTextConfirmPassword);
        ButtonRegister = (Button) findViewById(R.id.ButtonRegister);


        ButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postDataToSQLite();
            }
        });
    }

    private void postDataToSQLite() {
        String name =textInputEditTextName.getText().toString().trim();
        String mail =textInputEditTextEmail.getText().toString().trim();
        String pass = textInputEditTextPassword.getText().toString().trim();
        String confirm = textInputEditTextConfirmPassword.getText().toString().trim();
        if(name.matches("")){
            Toast.makeText(activity, "Name field is Empty!", Toast.LENGTH_SHORT).show();
        }
        else if(mail.matches("")){
            Toast.makeText(activity, "Email field is Empty!", Toast.LENGTH_SHORT).show();
        }

        else if(pass.matches("")){
            Toast.makeText(activity, "Password field is Empty!", Toast.LENGTH_SHORT).show();
        }
        else if(!pass.matches(confirm)){
            Toast.makeText(activity, "Password Confirm and Password do not match!", Toast.LENGTH_SHORT).show();
            Toast.makeText(activity, "Check and Try Again!", Toast.LENGTH_SHORT).show();
        }

        else if (!databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()))
        {
            String nm=textInputEditTextName.getText().toString().trim();
            String ml=textInputEditTextEmail.getText().toString().trim();
            String ps=textInputEditTextPassword.getText().toString().trim();
            user.setName(nm);
            user.setEmail(ml);
            user.setPassword(ps);
            databaseHelper.addUser(user);
            emptyInputEditText();
            Toast.makeText(activity, "Registered Successfully!", Toast.LENGTH_SHORT).show();

            //go Log in now
            Intent intent = new Intent(RegisterActivity.this,Login.class);
            Toast.makeText(activity, "Proceed to Log in!", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
        else
            {

            Toast.makeText(activity, "Already Exists!", Toast.LENGTH_SHORT).show();
            }

 }

    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }
}
