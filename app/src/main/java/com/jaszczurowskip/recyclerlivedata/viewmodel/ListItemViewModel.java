package com.jaszczurowskip.recyclerlivedata.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.jaszczurowskip.recyclerlivedata.datasource.ListItem;
import com.jaszczurowskip.recyclerlivedata.datasource.ListItemRepository;

/**
 * Created by jaszczurowskip on 18.10.2018
 */
public class ListItemViewModel extends ViewModel {
    @NonNull
    private ListItemRepository repository;

    ListItemViewModel(@NonNull ListItemRepository repository) {
        this.repository = repository;
    }

    public LiveData<ListItem> getListItemById(@NonNull final String itemId) {
        return repository.getListItem(itemId);
    }
}
