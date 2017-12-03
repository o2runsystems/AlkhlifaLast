package com.o2runsystems.dev2.kh_fam;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by AdminUser on 10/14/2017.
 */

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog  dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void ShowWaitDialog () {
        dialog = ProgressDialog.show(this, "",
                "تحميل. الرجاء الانتظار", true);
    }

    public void DismissWaitDialog(){
        dialog.dismiss();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
