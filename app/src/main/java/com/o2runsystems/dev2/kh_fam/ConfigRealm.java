package com.o2runsystems.dev2.kh_fam;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import com.crashlytics.android.Crashlytics;

import java.util.Locale;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by dev2 on 10/23/2016.
 */

public class ConfigRealm extends Application {
        @Override
        public void onCreate() {
            super.onCreate();
            Fabric.with(this, new Crashlytics());
            Realm.init(this);
            RealmConfiguration configuration = new RealmConfiguration.Builder()
                    .name("databaseV3.Realm")
                    .schemaVersion(1)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            Realm.setDefaultConfiguration(configuration);

            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath("fonts/cocon.ttf")
                    .setFontAttrId(R.attr.fontPath)
                    .build()
            );


        }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ChangeAppConfig();

    }

    private void  ChangeAppConfig(){
        Resources res = getResources();
        Configuration newConfig = new Configuration( res.getConfiguration() );
        Locale locale = new Locale("ar");
        newConfig.locale = locale;
        newConfig.fontScale = 1.05f;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            newConfig.setLayoutDirection( locale );
        }
        res.updateConfiguration( newConfig, null );
    }
}
