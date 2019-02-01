package com.alexiusdev.depeat.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.alexiusdev.depeat.R;
import static com.alexiusdev.depeat.Utility.*;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    EditText nameET;
    EditText surnameET;
    EditText emailET;
    EditText passwordET;
    EditText confirmPasswordET;
    Button signInBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        nameET = findViewById(R.id.name_et);
        surnameET = findViewById(R.id.surname_et);
        emailET = findViewById(R.id.email_et);
        passwordET = findViewById(R.id.password_et);
        confirmPasswordET = findViewById(R.id.confirm_password_et);
        signInBtn = findViewById(R.id.signin_btn);

        signInBtn.setOnClickListener(this);

        nameET.addTextChangedListener(signInButtonTextWatcher);
        surnameET.addTextChangedListener(signInButtonTextWatcher);
        emailET.addTextChangedListener(signInButtonTextWatcher);
        passwordET.addTextChangedListener(signInButtonTextWatcher);
        confirmPasswordET.addTextChangedListener(signInButtonTextWatcher);

        if(isValidEmail(getIntent().getStringExtra(LoginActivity.MAIL_KEY)))
            emailET.setText(getIntent().getStringExtra(LoginActivity.MAIL_KEY));
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case (R.id.signin_btn):
                //TODO add user to DB if not present
                break;
        }
    }

    private TextWatcher signInButtonTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        @Override
        public void afterTextChanged(Editable editable) {
            signInBtn.setEnabled(isValidEmail(emailET.getText().toString()) &&  //enable button only if the mail is valid and
                    !passwordET.getText().toString().isEmpty() &&   //password field isn't empty and
                    passwordET.getText().toString().equals(confirmPasswordET.getText().toString()) &&   //password and confirmpassword are equal
                    !nameET.getText().toString().isEmpty() &&   //name isn't empty and
                    !surnameET.getText().toString().isEmpty());    //surname isn't empty
        }
    };
}
