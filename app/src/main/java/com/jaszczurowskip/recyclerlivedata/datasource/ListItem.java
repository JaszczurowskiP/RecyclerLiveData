package com.jaszczurowskip.recyclerlivedata.datasource;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by jaszczurowskip on 15.10.2018
 */
@Entity
public class ListItem {

    @NonNull
    @PrimaryKey
    private String itemId;
    @Nullable
    private String message;
    @Nullable
    private String imgResourcePath;

    public ListItem(@NonNull String itemId, @Nullable String message, @NonNull String imgResourcePath) {
        this.itemId = itemId;
        this.message = message;
        this.imgResourcePath = imgResourcePath;
    }

    @NonNull
    public String getItemId() {
        return itemId;
    }

    @NonNull
    public void setItemId(@NonNull String itemId) {
        this.itemId = itemId;
    }

    @Nullable
    public String getImgResourcePath() {
        return imgResourcePath;
    }

    public void setImgResourcePath(@Nullable String imgResourcePath) {
        this.imgResourcePath = imgResourcePath;
    }

    @Nullable

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
