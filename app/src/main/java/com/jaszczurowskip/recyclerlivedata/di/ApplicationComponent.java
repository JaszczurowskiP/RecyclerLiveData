package com.jaszczurowskip.recyclerlivedata.di;

import com.jaszczurowskip.recyclerlivedata.features.create.CreateFragment;
import com.jaszczurowskip.recyclerlivedata.features.detail.DetailFragment;
import com.jaszczurowskip.recyclerlivedata.features.list.ListFragment;
import com.jaszczurowskip.recyclerlivedata.features.startingscreen.StartingFragment;

import dagger.Component;

/**
 * Created by jaszczurowskip on 16.10.2018
 */
@RecyclerLiveDataScope
@Component(modules = {ApplicationModule.class, RoomModule.class})
public interface ApplicationComponent {

    void inject(ListFragment listFragment);

    void inject(CreateFragment createFragment);

    void inject(DetailFragment detailFragment);

    void inject(StartingFragment startingFragment);

}
