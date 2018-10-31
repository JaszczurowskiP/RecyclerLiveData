package com.jaszczurowskip.recyclerlivedata.viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jaszczurowskip.recyclerlivedata.datasource.ListItem;
import com.jaszczurowskip.recyclerlivedata.datasource.ListItemRepository;
import com.jaszczurowskip.recyclerlivedata.util.rx.AppSchedulersProvider;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jaszczurowskip on 18.10.2018
 */
public class CreateListItemViewModel extends ViewModel {
    private final String TAG = CreateListItemViewModel.class.getSimpleName();
    @NonNull
    private ListItemRepository repository;

    CreateListItemViewModel(@NonNull ListItemRepository repository) {
        this.repository = repository;
    }

    @SuppressLint("CheckResult")
    public void addNewItemToDatabase(@NonNull final ListItem listItem) {
        Single.fromCallable(new Callable<Long>() {
            @Override
            public Long call() {
                return repository.createNewListItem(listItem);
            }
        }).subscribeOn(AppSchedulersProvider.getInstance().io())
                .subscribeOn(AppSchedulersProvider.getInstance().ui())
                .subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) {
                // no-op
            }
        }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "Error: " + throwable.getMessage());
                    }
                });
    }

    public LiveData<ListItem> getListItemById(@NonNull final String itemId) {
        return repository.getListItem(itemId);
    }
}