package com.jaszczurowskip.recyclerlivedata.datasource;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by jaszczurowskip on 15.10.2018
 */
@Database(entities = {ListItem.class}, version = 1)
public abstract class ListItemDatabase extends RoomDatabase {
    public abstract ListItemDAO listItemDAO();
}