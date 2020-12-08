package com.example.crs.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextEmail,editTextPassword ;
    CircularProgressButton cirLoginButton;
    List<UserInfo> userListResponseData;
    ResponseList response_list;
    General general;
    ContentLoadingProgressBar pb_loading;
    private SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeStatusBarColor();
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        cirLoginButton = findViewById(R.id.cirLoginButton);
        pb_loading = findViewById(R.id.pb_loading);
        pb_loading.hide();
        cirLoginButton.setOnClickListener(this);
        general = new General(this);
        sharedpreferences = getSharedPreferences(general.shared_name,
                Context.MODE_PRIVATE);
//        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }




    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.login_bk_color));
        }
    }
    public void onLoginClick(View View){
        startActivity(new Intent(this,RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }

    @Override
    public void onClick(final View v) {
        if(v == cirLoginButton){
                if((editTextEmail.getText().toString()!=null&&editTextEmail.getText().length()>0)&&(editTextPassword.getText().toString()!=null&&editTextPassword.getText().length()>0)){
                    general.hideKeyboard(LoginActivity.this);
                    v.setEnabled(false);
                    pb_loading.show();
                    (Api.getClient().getLogininfo(editTextEmail.getText().toString(),editTextPassword.getText().toString())).
                            enqueue(new Callback<ResponseList>() {
                        @Override
                        public void onResponse(Call<ResponseList> call, Response<ResponseList> response) {
                          //  Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();
                            response_list = response.body();
                            if(response_list!=null&&response_list.getStatus().equals("1")&&response_list.getData()!=null) {
                                stopProgressbar();
                                userListResponseData = response_list.getData();
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(General.Name, userListResponseData.get(0).getName());
                                editor.putString(General.Email, userListResponseData.get(0).getEmail());
                                editor.putString(General.phone_no, userListResponseData.get(0).getPhoneNo());
                                editor.putString(General.role, userListResponseData.get(0).getRole());
                                editor.putString(General.unique_id,userListResponseData.get(0).getUniqueId());
                                editor.commit();
                               // general.nextpage(MainDrawerActivity.class);
                                Intent in = new Intent(LoginActivity.this,MainDrawerActivity.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(in);
                                LoginActivity.this.finish();
                            }else{
                                Toast.makeText(LoginActivity.this,"Invalid UserName or Password..",Toast.LENGTH_LONG).show();
                                stopProgressbar();
                                v.setEnabled(true);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseList> call, Throwable t) {
                            // if error occurs in network transaction then we can get the error in this method.
                            Log.e("TAG", "onFailure: "+t.toString() );
                            v.setEnabled(true);
                            stopProgressbar();
                            Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this,"Email and Password Should not be empty",Toast.LENGTH_LONG).show();
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



}
