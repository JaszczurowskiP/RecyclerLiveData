package com.jaszczurowskip.recyclerlivedata.di;

import android.app.Application;
import android.content.Context;

import com.jaszczurowskip.recyclerlivedata.RecyclerLiveDataApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jaszczurowskip on 16.10.2018
 */
@Module
public class ApplicationModule {
    private final RecyclerLiveDataApplication application;

    public ApplicationModule(RecyclerLiveDataApplication application) {
        this.application = application;
    }

    @RecyclerLiveDataScope
    @Provides
    RecyclerLiveDataApplication provideRecyclerLiveDataApplication() {
        return application;
    }

    @RecyclerLiveDataScope
    @Provides
    Application provideApplication() {
        return application;
    }

}