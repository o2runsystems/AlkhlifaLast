package com.o2runsystems.dev2.kh_fam.ChatFolder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import com.o2runsystems.dev2.kh_fam.ChatFolder.ChatServices;

/**
 * Created by dev on 11/17/2016.
 */

public class NetWorkREciverForBackRound extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isConnected() || mobile.isConnected()) {

            context.startService(new Intent(context , ChatServices.class));
            Log.d("NetWork" , "Is concted");

        }else {

      context.stopService(new Intent(context , ChatServices.class));
            Log.d("NetWork" , "Is Not concted");

        }


    }
}
