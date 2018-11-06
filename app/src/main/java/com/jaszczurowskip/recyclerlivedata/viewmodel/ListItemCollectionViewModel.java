package com.jaszczurowskip.recyclerlivedata.viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jaszczurowskip.recyclerlivedata.datasource.ListItem;
import com.jaszczurowskip.recyclerlivedata.datasource.ListItemRepository;
import com.jaszczurowskip.recyclerlivedata.util.rx.AppSchedulersProvider;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;

/**
 * Created by jaszczurowskip on 18.10.2018
 */
public class ListItemCollectionViewModel extends ViewModel {
    private final String TAG = ListItemCollectionViewModel.class.getSimpleName();
    @NonNull
    private ListItemRepository repository;

    ListItemCollectionViewModel(@Nullable ListItemRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<ListItem>> getListItems() {
        return repository.getListOfData();
    }

    @SuppressLint("CheckResult")
    public void deleteListItem(@NonNull final ListItem listItem) {
        Single.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() {
                repository.deleteListItem(listItem);
                return 0;
            }
        }).subscribeOn(AppSchedulersProvider.getInstance().io())
                .observeOn(AppSchedulersProvider.getInstance().ui())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "Error: " + throwable.getMessage());
                    }
                });
    }
}