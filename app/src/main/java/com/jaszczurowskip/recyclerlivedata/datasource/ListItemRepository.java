package com.jaszczurowskip.recyclerlivedata.datasource;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by jaszczurowskip on 17.10.2018
 */
public class ListItemRepository {
    private final ListItemDAO listItemDao;

    @Inject
    public ListItemRepository(final ListItemDAO listItemDao) {
        this.listItemDao = listItemDao;
    }

    @Nullable
    public LiveData<List<ListItem>> getListOfData() {
        return listItemDao.getListItems();
    }

    @NonNull
    public LiveData<ListItem> getListItem(final String itemId) {
        return listItemDao.getListItemById(itemId);
    }

    @NonNull
    public Long createNewListItem(final ListItem listItem) {
        return listItemDao.insertListItem(listItem);
    }

    public void deleteListItem(final ListItem listItem) {
        listItemDao.deleteListItem(listItem);
    }
}
