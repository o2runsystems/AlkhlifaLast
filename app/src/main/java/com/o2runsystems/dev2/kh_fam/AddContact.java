package com.o2runsystems.dev2.kh_fam;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.o2runsystems.dev2.kh_fam.databinding.ActivityAddContactBinding;

import MangWorkFeature.Presenters.AddContactPresnter;
import MangWorkFeature.Presenters.AddContactViewReqird;
import WebServices.Models.Contact;

public class AddContact extends BaseActivity implements AddContactViewReqird {
   ActivityAddContactBinding addContactBinding;
    AddContactPresnter presnter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContactBinding  = DataBindingUtil.setContentView(this , R.layout.activity_add_contact);
        presnter = new AddContactPresnter(this);
        Intent data = getIntent();
        addContactBinding.txtforname.setText(data.getStringExtra("phone"));
        addContactBinding.editText.setText(data.getStringExtra("twitter"));
        addContactBinding.editText2.setText(data.getStringExtra("email"));
        addContactBinding.editText3.setText(data.getStringExtra("whatsap"));
        addContactBinding.btnBack.setOnClickListener(view -> {
            finish();
        });

        addContactBinding.btnSignup.setOnClickListener(view -> {
            Contact contact = new Contact();
            contact.mobile =  addContactBinding.txtforname.getText().toString();
            contact.twitter = addContactBinding.editText.getText().toString();
            contact.email =  addContactBinding.editText2.getText().toString();
            contact.whatsapp =  addContactBinding.editText3.getText().toString();
            presnter.AddContact(contact);

        });

    }

    @Override
    public void CloseActivity() {
        finish();
    }

    @Override
    public void ShowToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
