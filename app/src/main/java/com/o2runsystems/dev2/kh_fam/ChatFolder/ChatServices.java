package com.o2runsystems.dev2.kh_fam.ChatFolder;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.o2runsystems.dev2.kh_fam.MainActivity;
import com.o2runsystems.dev2.kh_fam.R;

import PublishFeature.PojoClasses.MarketModel;
import PublishFeature.PojoClasses.Publishs;
import WebServices.Models.UrlInfo;
import io.realm.Realm;
import microsoft.aspnet.signalr.client.ConnectionState;
import microsoft.aspnet.signalr.client.Platform;
import microsoft.aspnet.signalr.client.StateChangedCallback;
import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import microsoft.aspnet.signalr.client.hubs.SubscriptionHandler1;

public class ChatServices extends Service {


    android.support.v4.app.NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.klipha);

    HubConnection conction;
    HubProxy proxy;

    boolean Isconct = false;

    public ChatServices() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    NetWorkREciverForBackRound rEciver;
    @Override
    public void onCreate() {
        super.onCreate();

         rEciver = new NetWorkREciverForBackRound();

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(rEciver, filter);
        signalrWork();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(Isconct == false){
            conction.start();
            Isconct = true;
        }

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

       if(Isconct == true){
           conction.stop();
           Isconct = false;
       }
        Log.d("Services" , "Destroyed");
    }



    void signalrWork(){
        Platform.loadPlatformComponent(new AndroidPlatformComponent());
        String Url = UrlInfo.ChatHubUrls;
        conction = new HubConnection(Url);
        proxy = conction.createHubProxy("card");

        proxy.on("GetBroadcastCardSendForNews", publishs -> {
            Log.d("TageSignalR" , publishs.Body);
            final Realm realms = Realm.getDefaultInstance();
            realms.executeTransactionAsync(realm -> realm.copyToRealmOrUpdate(publishs));
            realms.close();
            Intent notfyIntent = new Intent(getBaseContext() , MainActivity.class);
            PendingIntent pen = PendingIntent.getActivity(getBaseContext() ,0 , notfyIntent , 0);
            mBuilder.setContentIntent(pen);
            mBuilder.setContentTitle("لديك خبر جديد").setContentText(publishs.Body);
            mBuilder.setAutoCancel(true);

            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            mNotifyMgr.notify(001, mBuilder.build());
            try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, Publishs.class);



        proxy.on("GetBroadcastCardSendForEvents", publishs -> {
            Log.d("TageSignalR" , publishs.Body);
            final Realm realms = Realm.getDefaultInstance();
            realms.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(publishs);
                }
            });
            realms.close();
            Intent notfyIntent = new Intent(getBaseContext() , MainActivity.class);
            PendingIntent pen = PendingIntent.getActivity(getBaseContext() ,0 , notfyIntent , 0);
            mBuilder.setContentIntent(pen);
            mBuilder.setContentTitle("لديك منسابه جديده").setContentText(publishs.Body);
            mBuilder.setAutoCancel(true);
            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


            mNotifyMgr.notify(002, mBuilder.build());

            try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, Publishs.class);



        proxy.on("GetBroadcastCardSendForMarket" , cardModel -> {
            final Realm realms = Realm.getDefaultInstance();
            realms.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(cardModel);
                }
            });
            realms.close();
        }, MarketModel.class);



        conction.stateChanged((connectionState, connectionState1) -> Log.d("Signalr" , connectionState1.name()));

    }

}
