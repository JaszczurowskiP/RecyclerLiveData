package com.jaszczurowskip.recyclerlivedata.di;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;

import com.jaszczurowskip.recyclerlivedata.datasource.ListItemDAO;
import com.jaszczurowskip.recyclerlivedata.datasource.ListItemDatabase;
import com.jaszczurowskip.recyclerlivedata.datasource.ListItemRepository;
import com.jaszczurowskip.recyclerlivedata.viewmodel.CustomViewModelFactory;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jaszczurowskip on 16.10.2018
 */
@Module
public class RoomModule {
    private final ListItemDatabase database;

    public RoomModule(Application application) {
        this.database = Room.databaseBuilder(
                application,
                ListItemDatabase.class,
                "ListItem.db"
        ).build();
    }

    @Provides
    @RecyclerLiveDataScope
    ListItemRepository provideListItemRepository(ListItemDAO listItemDao) {
        return new ListItemRepository(listItemDao);
    }

    @Provides
    @RecyclerLiveDataScope
    ListItemDAO provideListItemDao(ListItemDatabase database) {
        return database.listItemDAO();
    }

    @Provides
    @RecyclerLiveDataScope
    ListItemDatabase provideListItemDatabase(Application application) {
        return database;
    }

    @Provides
    @RecyclerLiveDataScope
    ViewModelProvider.Factory provideViewModelFactory(ListItemRepository repository) {
        return new CustomViewModelFactory(repository);
    }
}
