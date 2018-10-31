package com.jaszczurowskip.recyclerlivedata;

import android.app.Application;
import android.support.annotation.NonNull;

import com.jaszczurowskip.recyclerlivedata.di.ApplicationComponent;
import com.jaszczurowskip.recyclerlivedata.di.ApplicationModule;
import com.jaszczurowskip.recyclerlivedata.di.DaggerApplicationComponent;
import com.jaszczurowskip.recyclerlivedata.di.RoomModule;

/**
 * Created by jaszczurowskip on 16.10.2018
 */
public class RecyclerLiveDataApplication extends Application{
    @NonNull
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .roomModule(new RoomModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
