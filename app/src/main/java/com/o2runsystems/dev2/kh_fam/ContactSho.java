package com.o2runsystems.dev2.kh_fam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import MangWorkFeature.Presenters.ContactShowPresnter;
import MangWorkFeature.Presenters.ContactShowViewReqird;
import WebServices.Models.Contact;


public class ContactSho extends BaseActivity implements ContactShowViewReqird {
    TextView fac;
    TextView twitter;
    TextView email;
    TextView whats;
    ContactShowPresnter showpresnter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_sho);
     showpresnter = new ContactShowPresnter(this);
        Button join = (Button) findViewById(R.id.btn_join);
         fac = (TextView) findViewById(R.id.txt_facebook);
         twitter = (TextView) findViewById(R.id.txt_twitter);
         email = (TextView) findViewById(R.id.txt_gmail);
         whats = (TextView) findViewById(R.id.txt_whatsapp);
             showpresnter.GetContact();
        join.setOnClickListener(view -> {
            Intent intend = new Intent(this , AddContact.class);
            intend.putExtra("phone" , fac.getText().toString());
            intend.putExtra("twitter" , twitter.getText().toString());
            intend.putExtra("email" , email.getText().toString());
            intend.putExtra("whatsap" , whats.getText().toString());

            startActivity(intend);
        });

    }

    @Override
    public void ShowInView(Contact contact) {
        fac.setText(contact.mobile);
        twitter.setText(contact.twitter);
        email.setText(contact.email);
        whats.setText(contact.whatsapp);
    }

    @Override
    public void ShowToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
