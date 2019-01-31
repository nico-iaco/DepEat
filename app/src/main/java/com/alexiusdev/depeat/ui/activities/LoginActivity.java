package com.alexiusdev.depeat.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.alexiusdev.depeat.R;
import static com.alexiusdev.depeat.Utility.*;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginBtn;
    Button signinBtn;
    EditText emailET;
    EditText passwordET;
    TextView creditsTV;
    TextView versionTV;
    public static final int MIN_LENGTH_PSW = 6;
    public static final String MAIL_KEY = "email";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.login_btn);
        signinBtn = findViewById(R.id.signin_btn);
        emailET = findViewById(R.id.email_et);
        passwordET = findViewById(R.id.password_et);
        creditsTV = findViewById(R.id.credits_tv);
        versionTV = findViewById(R.id.version_tv);

        loginBtn.setOnClickListener(this);
        signinBtn.setOnClickListener(this);
        creditsTV.setOnClickListener(this);
        versionTV.setOnClickListener(this);

        emailET.addTextChangedListener(loginButtonTextWatcher);
        passwordET.addTextChangedListener(loginButtonTextWatcher);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.login_btn:
                //TODO login
                showToast(this, getString(R.string.login_successful));
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.signin_btn:
                startActivity(new Intent(LoginActivity.this, SignInActivity.class).putExtra(MAIL_KEY,emailET.getText().toString()));
                break;
            case R.id.credits_tv:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Alexius33/")));
                break;
            case R.id.version_tv:
                //TODO implement a changelog?
                break;
        }
    }

    private TextWatcher loginButtonTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        @Override
        public void afterTextChanged(Editable editable) {
            loginBtn.setEnabled(isValidEmail(emailET.getText().toString()) &&   //enable button only if the mail is valid and
                    passwordET.getText().toString().length() >= MIN_LENGTH_PSW);    //the password length is greater or equal to MIN_LENGTH_PSW
        }
    };


}
