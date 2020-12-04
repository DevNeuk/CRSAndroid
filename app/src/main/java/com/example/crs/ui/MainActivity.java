package com.example.crs.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crs.network.Api;
import com.example.crs.R;
import com.example.crs.model.UserInfo;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextEmail,editTextPassword ;
    CircularProgressButton cirLoginButton;
    List<UserInfo> userListResponseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        cirLoginButton = findViewById(R.id.cirLoginButton);
        cirLoginButton.setOnClickListener(this);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }


    }

    public void onLoginClick(View View){
        startActivity(new Intent(this,RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    @Override
    public void onClick(View v) {
        if(v == cirLoginButton){
                if((editTextEmail.getText().toString()!=null&&editTextEmail.getText().length()>0)&&(editTextPassword.getText().toString()!=null&&editTextPassword.getText().length()>0)){
                    (Api.getClient().getLogininfo(editTextEmail.getText().toString(),editTextPassword.getText().toString())).enqueue(new Callback<List<UserInfo>>() {
                        @Override
                        public void onResponse(Call<List<UserInfo>> call, Response<List<UserInfo>> response) {
                            Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();
                            userListResponseData = response.body();
                        }

                        @Override
                        public void onFailure(Call<List<UserInfo>> call, Throwable t) {
                            // if error occurs in network transaction then we can get the error in this method.
                            Log.e("TAG", "onFailure: "+t.toString() );
                            Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
        } else{
            Toast.makeText(MainActivity.this,"Email and Password Should not be empty",Toast.LENGTH_LONG).show();
        }
    }
}
