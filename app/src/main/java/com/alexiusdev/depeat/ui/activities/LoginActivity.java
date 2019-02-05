package com.alexiusdev.depeat.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.alexiusdev.depeat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.alexiusdev.depeat.ui.Utility.*;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginBtn;
    private Button signinBtn;
    private Button forgotPasswordBtn;
    private EditText emailET;
    private EditText passwordET;
    private TextView creditsTV;
    private TextView versionTV;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    public static final int MIN_LENGTH_PSW = 6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        loginBtn = findViewById(R.id.login_btn);
        signinBtn = findViewById(R.id.signin_btn);
        forgotPasswordBtn = findViewById(R.id.forgot_password_btn);
        emailET = findViewById(R.id.email_et);
        passwordET = findViewById(R.id.password_et);
        creditsTV = findViewById(R.id.credits_tv);
        versionTV = findViewById(R.id.version_tv);

        loginBtn.setOnClickListener(this);
        signinBtn.setOnClickListener(this);
        creditsTV.setOnClickListener(this);
        versionTV.setOnClickListener(this);
        forgotPasswordBtn.setOnClickListener(this);

        emailET.addTextChangedListener(loginButtonTextWatcher);
        passwordET.addTextChangedListener(loginButtonTextWatcher);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.login_btn:
                login(emailET.getText().toString(),passwordET.getText().toString());
                break;
            case R.id.signin_btn:
                startActivity(new Intent(LoginActivity.this, SignInActivity.class).putExtra(EMAIL_KEY,emailET.getText().toString()));
                break;
            case R.id.credits_tv:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Alexius33/")));
                break;
            case R.id.version_tv:
                //TODO implement a changelog?
                break;
            case R.id.forgot_password_btn:
                mAuth.sendPasswordResetEmail(emailET.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>(){
                    @Override
                    public void onComplete(@NonNull Task<Void> task){
                        if(task.isSuccessful())
                            showToast(LoginActivity.this,getString(R.string.forgot_password_toast));
                        else
                            showToast(LoginActivity.this,getString(R.string.error));
                    }
                });
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case (R.id.quit_menu):
                finish();
                System.exit(0);
                return true;
        }

        return super.onOptionsItemSelected(item);
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

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class).putExtra(EMAIL_KEY,user.getEmail()));
                        } else {
                            // If sign in fails, display a message to the user.
                            passwordET.setText("");
                            showToast(LoginActivity.this, getString(R.string.wrong_password));
                            forgotPasswordBtn.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
}
