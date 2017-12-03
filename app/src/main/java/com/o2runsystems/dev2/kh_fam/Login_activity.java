package com.o2runsystems.dev2.kh_fam;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import WebServices.Api.MemoService;
import WebServices.Models.UrlInfo;
import WebServices.Models.logs_response;
import WebServices.Models.user_response;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Login_activity extends BaseActivity {
    EditText input_username, input_password;
    AppCompatButton btn_login;
    Realm realms;

    Typeface font1;
    TextView txt_header;

    ImageView btn_pass;
    static boolean show_pass = true;

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //region check if aleardy login
        realms = Realm.getDefaultInstance();
        long id = realms.where(user_response.class).count();
        if (id > 0) {

            if (true) {
                startActivity(new Intent(Login_activity.this, MainActivity.class));
                finish();
            }
        }
        //endregion

        setContentView(R.layout.login_activity);

        //region android permations
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        //endregion

        init();
        fonts();

        login();
        sign();
        btn_pass.setOnClickListener(view -> {
            show_hide_password();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realms.close();
    }


    void sign() {
        // link_signup.setOnClickListener(new onclksign());
    }


    Boolean reqired() {
        String u = input_username.getText().toString();
        String p = input_password.getText().toString();
        if ((u.equals(null) || u.equals("")) || (p.equals(null) || p.equals(""))) {
            return false;
        } else {
            return true;
        }

    }

    void login() {
        btn_login.setOnClickListener(new clicker1());
    }

    private class clicker1 implements View.OnClickListener {
        @Override
        public void onClick(final View view) {
            if (reqired()) {
                // from server
                //  Toast.makeText(Login_activity.this ,"ok",Toast.LENGTH_LONG).show();
                Gson gson = new GsonBuilder().create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(UrlInfo.AppUrl)
                        .addConverterFactory(GsonConverterFactory.create(gson)).build();
                MemoService service = retrofit.create(MemoService.class);
                String user = input_username.getText().toString();
                String pass = input_password.getText().toString();
                Call<user_response> log = service.login(user, pass);
                log.enqueue(new retrofit2.Callback<user_response>() {
                    @Override
                    public void onResponse(Call<user_response> call, Response<user_response> response) {

                        final user_response user = response.body();
                        int p = user.perid;
                        if (p == -1) {
                            Toast.makeText(Login_activity.this, R.string.login_worng, Toast.LENGTH_LONG).show();
                            input_username.setError(getString(R.string.login_worng));
                            input_password.setError(getString(R.string.login_worng));
                        } else {
                            //begin vierfiy to my device

                            realms.executeTransactionAsync(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    realm.copyToRealmOrUpdate(user);
                                }
                            });

                            long id = realms.where(logs_response.class).count();
                            if (id > 0) {
                                startActivity(new Intent(Login_activity.this, MainActivity.class));
                                finish();
                            } else {
                                TelephonyManager tMgr = (TelephonyManager) Login_activity.this.getSystemService(Context.TELEPHONY_SERVICE);
                                Gson gson = new GsonBuilder().create();
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(UrlInfo.AppUrl)
                                        .addConverterFactory(GsonConverterFactory.create(gson)).build();
                                MemoService service = retrofit.create(MemoService.class);

                                final Call<logs_response> logs = service.getlog(tMgr.getDeviceId(), user.id);
                                logs.enqueue(new Callback<logs_response>() {
                                    @Override
                                    public void onResponse(Call<logs_response> call, Response<logs_response> response) {
                                        final logs_response l = response.body();
                                        if (l != null) {
                                            realms.executeTransactionAsync(new Realm.Transaction() {
                                                @Override
                                                public void execute(Realm realm) {
                                                    realm.copyToRealmOrUpdate(l);
                                                }
                                            });
                                            startActivity(new Intent(Login_activity.this, MainActivity.class));
                                            finish();
                                        } else {

                                            startActivity(new Intent(Login_activity.this, MainActivity.class));
                                            finish();

                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<logs_response> call, Throwable t) {

                                    }
                                });

                            }


                        }


                    }

                    @Override
                    public void onFailure(Call<user_response> call, Throwable t) {

                    }
                });

            } else {
                String e = getResources().getString(R.string.error_field_required);
                Toast.makeText(Login_activity.this, e, Toast.LENGTH_LONG).show();
            }

        }
    }


    void fonts() {
        font1 = Typeface.createFromAsset(getAssets(), "fonts/DroidKufiRegular.ttf");
        txt_header.setTypeface(font1);
        input_username.setTypeface(font1);
        input_password.setTypeface(font1);
        btn_login.setTypeface(font1);
    }

    void init() {
        btn_login = (AppCompatButton) findViewById(R.id.btn_login);
        input_username = (EditText) findViewById(R.id.input_username);
        input_password = (EditText) findViewById(R.id.input_password);
        txt_header = (TextView) findViewById(R.id.txtfortitle);
        btn_pass = (ImageView) findViewById(R.id.btn_password);
    }

    void show_hide_password() {
        if (show_pass) {
            input_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

            show_pass = false;
            btn_pass.setImageResource(R.drawable.ic_eye_white_off);
        } else {
            input_password.setInputType(129);
            show_pass = true;
            btn_pass.setImageResource(R.drawable.ic_eye_white_on);
        }
    }


}
