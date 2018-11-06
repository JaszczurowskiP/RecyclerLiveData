package com.jaszczurowskip.recyclerlivedata.datasource;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by jaszczurowskip on 15.10.2018
 */
@Dao
public interface ListItemDAO {

    @Query("SELECT * FROM ListItem WHERE itemId = :itemId")
    LiveData<ListItem> getListItemById(final String itemId);

    @Query("SELECT * FROM ListItem ")
    LiveData<List<ListItem>> getListItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertListItem(final ListItem listItem);

    @Delete()
    void deleteListItem(final ListItem listItem);
}

