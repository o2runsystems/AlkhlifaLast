package com.o2runsystems.dev2.kh_fam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.o2runsystems.dev2.kh_fam.ChatFolder.Messages;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Adabters.ChatAdabter;
import Utiles.Compress;
import WebServices.Api.UploadServices;
import WebServices.Models.TextChatMessage;
import WebServices.Models.UrlInfo;
import WebServices.Models.ViewsNames;
import WebServices.Models.user_response;
import io.realm.Realm;
import microsoft.aspnet.signalr.client.Platform;
import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import microsoft.aspnet.signalr.client.hubs.SubscriptionHandler1;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class chatScrren extends BaseActivity {
    String isman ;
    String Username ;
    RecyclerView mRecyclerView;
    List<TextChatMessage> msg;
    ViewsNames viewsNames;
    ChatAdabter adabter;
    HubConnection con;
    HubProxy proxy;
    EditText ed = null;
     String [] s;
    Realm realms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_scrren);
        realms = Realm.getDefaultInstance();
      s  =  getResources().getStringArray(R.array.gender_arrays);
        getintent();
        Button sendbtn = (Button) findViewById(R.id.button1);
        Toolbar bar = (Toolbar) findViewById(R.id.chattoolbar);
        setSupportActionBar(bar);
        getSupportActionBar().setTitle("الدردشات");
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerchat);
        viewsNames = new ViewsNames();
         ed = (EditText) findViewById(R.id.chatedit);
        LinearLayoutManager manger = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manger);
        msg = new ArrayList<>();
        manger.setReverseLayout(true);
        manger.setStackFromEnd(true);
        adabter = new ChatAdabter(this,msg,mRecyclerView);
        mRecyclerView.setAdapter(adabter);
        SignalRWork();

        sendbtn.setOnClickListener(view -> {

          if(isman == s[0]){
              String data = ed.getText().toString();
              ed.getText().clear();
              proxy.invoke("SendBroadcAstForMan" , new Messages(data, Username , "" , false));
              java.util.Date date = new java.util.Date();
              SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
              msg.add(0, new TextChatMessage(data , sdf.format(date), viewsNames.RghitTextRow , "انا يقول : " ));
              adabter.notifyItemInserted(0);
              mRecyclerView.smoothScrollToPosition(0);

          }else {
              String data = ed.getText().toString();
              ed.getText().clear();
              proxy.invoke("SendBroadcAstForWomaen" , new Messages(data, Username , "" , false));
              java.util.Date date = new java.util.Date();
              SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
              msg.add(0, new TextChatMessage(data , sdf.format(date), viewsNames.RghitTextRow , "انا يقول : " ));
              adabter.notifyItemInserted(0);
              mRecyclerView.smoothScrollToPosition(0);

          }


        });

    }

    void  getintent(){
        Username = realms.where(user_response.class).findFirst().userName;
        isman = Boolean.toString(realms.where(user_response.class).findFirst().gender);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.attchedmenu , menu);
        return  true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.attachedimgchat){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(intent
                    , 1);

            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            Compress compress = new Compress();
            final String[] NameAndUUid;
            try {
                NameAndUUid = compress.compressImage(data.getData() , this);

                Gson gson = new GsonBuilder()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(UrlInfo.MeduaServerUrl)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                UploadServices services = retrofit.create(UploadServices.class);
                File file = new File(NameAndUUid[1]);
                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("upload" , file.getName() , reqFile);
                Call<ResponseBody> body = services.UploadFile(part);
                body.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                       if(isman == s[0]){
                           proxy.invoke("SendBroadcAstForMan" , new Messages(NameAndUUid[0], Username , "" , true));
                           java.util.Date date = new java.util.Date();
                           SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                           msg.add(0, new TextChatMessage(NameAndUUid[0] , sdf.format(date), viewsNames.RghitImgRow , "انا يقول : " ));
                           adabter.notifyItemInserted(0);
                           mRecyclerView.smoothScrollToPosition(0);
                       }else{
                           proxy.invoke("SendBroadcAstForWomaen" , new Messages(NameAndUUid[0], Username , "" , true));
                           java.util.Date date = new java.util.Date();
                           SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                           msg.add(0, new TextChatMessage(NameAndUUid[0] , sdf.format(date), viewsNames.RghitImgRow , "انا يقول : " ));
                           adabter.notifyItemInserted(0);
                           mRecyclerView.smoothScrollToPosition(0);
                       }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(chatScrren.this , "Error To Upload Image" , Toast.LENGTH_LONG).show();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    void SignalRWork(){

            Platform.loadPlatformComponent(new AndroidPlatformComponent());
            String Url = UrlInfo.ChatHubUrls;
            con = new HubConnection(Url);

            proxy = con.createHubProxy("chat");
            if(isman == s[0]){
                proxy.on("GetBroadCastMessgeFromMan", messages -> runOnUiThread(() -> {
                    if(messages.IsM){
                        msg.add(0,new TextChatMessage(messages.B, messages.D ,viewsNames.LeftImgRow , messages.U));
                        adabter.notifyItemInserted(0);
                        mRecyclerView.smoothScrollToPosition(0);
                    }else {

                        msg.add(0,new TextChatMessage(messages.B, messages.D ,viewsNames.LeftTextRow , messages.U));
                        adabter.notifyItemInserted(0);
                        mRecyclerView.smoothScrollToPosition(0
                        );

                    }
                }), Messages.class);

                proxy.on("GetBroadCastMessgeFromManOffline", messagesof -> {
                   for (Messages m : messagesof){

                       if(m.IsM){
                           if(m.U.equals(Username)){
                               msg.add(0,new TextChatMessage(m.B , m.D , viewsNames.RghitImgRow , "انا يقول : "));

                           }else {
                               msg.add(0,new TextChatMessage(m.B , m.D , viewsNames.LeftImgRow , m.U));
                           }

                       }else {

                           if(m.U.equals(Username)){
                               msg.add(0,new TextChatMessage(m.B , m.D , viewsNames.RghitTextRow , "انا يقول : "));

                           }else
                           {
                               msg.add(0,new TextChatMessage(m.B , m.D , viewsNames.LeftTextRow , m.U));

                           }

                       }
                   }
                      runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              adabter.notifyDataSetChanged();
                              mRecyclerView.scrollToPosition(0);
                          }
                      });

                   },Messages [].class);
                con.start();


            }else  {

                proxy.on("GetBroadCastMessgeFromWomen", messages -> runOnUiThread(() -> {
                    if(messages.IsM){
                        msg.add(0,new TextChatMessage(messages.B, messages.D ,viewsNames.LeftImgRow , messages.U));
                        adabter.notifyItemInserted(0);
                        mRecyclerView.smoothScrollToPosition(0);
                    }else {

                        msg.add(0,new TextChatMessage(messages.B, messages.D ,viewsNames.LeftTextRow , messages.U));
                        adabter.notifyItemInserted(0);
                        mRecyclerView.smoothScrollToPosition(0);

                    }
                }), Messages.class);

                proxy.on("GetBroadCastMessgeFromWomenOflline", new SubscriptionHandler1<Messages []>() {
                    Collection<TextChatMessage> msgs = new ArrayList<TextChatMessage>();

                    @Override
                    public void run(Messages[] messagesof) {
                        for (Messages m : messagesof){

                            if(m.IsM){
                                if(m.U.equals(Username)){
                                    msg.add(0,new TextChatMessage(m.B , m.D , viewsNames.RghitImgRow , "انا يقول : "));

                                }else {
                                    msg.add(0,new TextChatMessage(m.B , m.D , viewsNames.LeftImgRow , m.U));

                                }

                            }else
                            {

                                if(m.U.equals(Username)){
                                    msg.add(0,new TextChatMessage(m.B , m.D , viewsNames.RghitTextRow ,"انا يقول : "));

                                }else
                                {
                                    msg.add(0,new TextChatMessage(m.B , m.D , viewsNames.LeftTextRow , m.U));

                                }

                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adabter.notifyDataSetChanged();
                                    mRecyclerView.scrollToPosition(0);
                                }
                            });
                        }

                    }
                },Messages [].class);

                con.start();
            }


    }


}

