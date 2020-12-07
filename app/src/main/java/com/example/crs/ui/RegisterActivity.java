package com.example.crs.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crs.network.Api;
import com.example.crs.R;
import com.example.crs.model.StatusResponse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private CircularProgressButton cirRegisterButton;
    private EditText editTextName, editTextEmail, editTextPassword, editTextMobile;
    private StatusResponse userListResponseData;
    private ContentLoadingProgressBar pb_loading;
    General general;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);
        changeStatusBarColor();
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextMobile = findViewById(R.id.editTextMobile);
        cirRegisterButton = findViewById(R.id.cirRegisterButton);
        pb_loading = findViewById(R.id.pb_loading);
        pb_loading.hide();
        cirRegisterButton.setOnClickListener(this);
        general = new General(RegisterActivity.this);


    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);

    }

    @Override
    public void onClick(View v) {
        if (v == cirRegisterButton) {
            v.setEnabled(false);
            general.hideKeyboard(RegisterActivity.this);
            if (editTextName.getText().toString() != null && editTextName.getText().length() > 0) {
                if (editTextEmail.getText().toString() != null && editTextEmail.getText().length() > 0) {
                    if (editTextMobile.getText().toString() != null && editTextMobile.getText().length() > 0) {
                        if (editTextPassword.getText().toString() != null && editTextPassword.getText().length() > 0) {
                            pb_loading.show();
                            sendResigerRequest(editTextName.getText().toString(), editTextEmail.getText().toString(), editTextMobile.getText().toString(), editTextPassword.getText().toString());
                        } else {
                            Toast.makeText(RegisterActivity.this, "Please enter password!!", Toast.LENGTH_LONG).show();
                            v.setEnabled(true);
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Please enter mobile number!!", Toast.LENGTH_LONG).show();
                        v.setEnabled(true);
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Please enter email!!", Toast.LENGTH_LONG).show();
                    v.setEnabled(true);
                }
            } else {
                Toast.makeText(RegisterActivity.this, "Please enter name!!", Toast.LENGTH_LONG).show();
                v.setEnabled(true);
            }

        }
    }

    public void stopProgressbar(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pb_loading.hide();

            }
        }, 1000);
    }

    private void sendResigerRequest(String name, String email, String number, String password) {
        (Api.getClient().registration(name,email,password,number)).enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                userListResponseData = response.body();
                stopProgressbar();
                if(userListResponseData.getStatus().equals("1")) {
                    Toast.makeText(RegisterActivity.this, "Successfully Register", Toast.LENGTH_LONG).show();
                    general.nextpage(LoginActivity.class);
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                // if error occurs in network transaction then we can get the error in this method.
                Log.e("TAG", "onFailure: "+t.toString() );
                stopProgressbar();
                Toast.makeText(RegisterActivity.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


}
