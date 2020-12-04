package com.example.crs.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crs.model.ResponseList;
import com.example.crs.network.Api;
import com.example.crs.R;
import com.example.crs.model.UserInfo;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextEmail,editTextPassword ;
    CircularProgressButton cirLoginButton;
    List<UserInfo> userListResponseData;
    ResponseList response_list;
    General general;
    ContentLoadingProgressBar pb_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        cirLoginButton = findViewById(R.id.cirLoginButton);
        pb_loading = findViewById(R.id.pb_loading);
        pb_loading.hide();
        cirLoginButton.setOnClickListener(this);
        general = new General(this);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }


    }

    public void onLoginClick(View View){
        startActivity(new Intent(this,RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    @Override
    public void onClick(final View v) {
        if(v == cirLoginButton){
            general.hideKeyboard(MainActivity.this);
            v.setEnabled(false);
            pb_loading.show();
                if((editTextEmail.getText().toString()!=null&&editTextEmail.getText().length()>0)&&(editTextPassword.getText().toString()!=null&&editTextPassword.getText().length()>0)){
                    (Api.getClient().getLogininfo(editTextEmail.getText().toString(),editTextPassword.getText().toString())).
                            enqueue(new Callback<ResponseList>() {
                        @Override
                        public void onResponse(Call<ResponseList> call, Response<ResponseList> response) {
                          //  Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();
                            response_list = response.body();
                            if(response_list.getStatus().equals("1")&&response_list.getData()!=null) {
                                stopProgressbar();
                                userListResponseData = response_list.getData();
                                general.nextpage(Reservation.class);
                            }else{
                                v.setEnabled(true);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseList> call, Throwable t) {
                            // if error occurs in network transaction then we can get the error in this method.
                            Log.e("TAG", "onFailure: "+t.toString() );
                            v.setEnabled(true);
                            stopProgressbar();
                            Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
        } else{
            v.setEnabled(true);
            Toast.makeText(MainActivity.this,"Email and Password Should not be empty",Toast.LENGTH_LONG).show();
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
}
