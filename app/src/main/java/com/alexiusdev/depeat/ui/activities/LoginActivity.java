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
import com.alexiusdev.depeat.datamodels.Restaurant;
import com.alexiusdev.depeat.services.RestController;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.alexiusdev.depeat.ui.Utility.*;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<String>, Response.ErrorListener {

    private Button loginBtn, signinBtn, forgotPasswordBtn;
    private EditText emailET, passwordET;
    private TextView creditsTV, versionTV;
    public static final int MIN_LENGTH_PSW = 6;
    RestController restController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        restController = new RestController(this);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.login_btn:
                login(emailET.getText().toString(), passwordET.getText().toString());
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
            loginBtn.setTextColor(isValidEmail(emailET.getText().toString()) &&   //enable button only if the mail is valid and
                    passwordET.getText().toString().length() >= MIN_LENGTH_PSW ?
                    getResources().getColor(R.color.primary_text) : getResources().getColor(R.color.secondary_text));
        }
    };

    private void login(String email, String password) {
        String loginEndpoint = "auth/local/";
        Map<String,String> body = new HashMap<>();
        body.put("identifier", email);
        body.put("password", password);
        restController.postRequest(loginEndpoint, body,this,this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        showToast(this, "Wops! Something went wrong!");
        //Log.d("postResponse",error.getMessage());
    }

    @Override
    public void onResponse(String response) {
        String session ="";
        String email = "";
        String id = "";
        try {
            JSONObject jsonResponse = new JSONObject(response);
            session = jsonResponse.getString("jwt");
            email = jsonResponse.getJSONObject("user").getString("email");
            id = jsonResponse.getJSONObject("user").getString("_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("postResponseSession",session);
        Log.d("postResponseEmail",email);
        Log.d("postResponseId",id);
        //startActivity(new Intent(LoginActivity.this,MainActivity.class).putExtra(EMAIL_KEY,emailET.getText().toString()));

        setResult(RESULT_OK);
        finish();
    }
}
