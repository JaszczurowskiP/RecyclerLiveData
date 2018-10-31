package com.jaszczurowskip.recyclerlivedata.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.jaszczurowskip.recyclerlivedata.datasource.ListItemRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by jaszczurowskip on 18.10.2018
 */
@Singleton
public class CustomViewModelFactory implements ViewModelProvider.Factory {
    private final ListItemRepository repository;

    @Inject
    public CustomViewModelFactory(@NonNull ListItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull final Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListItemCollectionViewModel.class))
            return (T) new ListItemCollectionViewModel(repository);

        else if (modelClass.isAssignableFrom(ListItemViewModel.class))
            return (T) new ListItemViewModel(repository);

        else if (modelClass.isAssignableFrom(CreateListItemViewModel.class))
            return (T) new CreateListItemViewModel(repository);
        else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }
}
