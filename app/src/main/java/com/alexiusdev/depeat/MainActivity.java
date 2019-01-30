package com.alexiusdev.depeat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginButton;
    Button signinButton;
    EditText emailEt;
    EditText passwordEt;
    TextView creditsTv;
    TextView versionTv;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.login_button);
        signinButton = findViewById(R.id.signin_button);
        emailEt = findViewById(R.id.email_et);
        passwordEt = findViewById(R.id.password_et);
        creditsTv = findViewById(R.id.credits_tv);
        versionTv = findViewById(R.id.version_tv);

        loginButton.setOnClickListener(this);
        signinButton.setOnClickListener(this);
        creditsTv.setOnClickListener(this);
        versionTv.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.login_button:
                //TODO login
                break;
            case R.id.signin_button:
                //TODO implement signin activity
                break;
            case R.id.credits_tv:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Alexius33/")));
                break;
            case R.id.version_tv:
                //TODO implement a changelog?
                break;
        }
    }




    public void showToast(int resId){
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }
}
