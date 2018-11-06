package com.jaszczurowskip.recyclerlivedata.features.detail;


import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.jaszczurowskip.recyclerlivedata.R;
import com.jaszczurowskip.recyclerlivedata.RecyclerLiveDataApplication;
import com.jaszczurowskip.recyclerlivedata.databinding.FragmentDetailBinding;
import com.jaszczurowskip.recyclerlivedata.datasource.ListItem;
import com.jaszczurowskip.recyclerlivedata.features.create.CreateActivity;
import com.jaszczurowskip.recyclerlivedata.features.detail.actions.OnEditButton;
import com.jaszczurowskip.recyclerlivedata.util.Utils;
import com.jaszczurowskip.recyclerlivedata.viewmodel.ListItemViewModel;

import java.util.Objects;

import javax.inject.Inject;

/**
 * Created by jaszczurowskip on 12.10.2018
 */
public class DetailFragment extends Fragment {
    private static final String EXTRA_ITEM_ID = "EXTRA_ITEM_ID";
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    ListItemViewModel listItemViewModel;
    private String itemId;
    private FragmentDetailBinding fragmentDetailBinding;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(@NonNull final String itemId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_ITEM_ID, itemId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((RecyclerLiveDataApplication) Objects.requireNonNull(getActivity()).getApplication())
                .getApplicationComponent()
                .inject(this);
        Bundle args = getArguments();
        this.itemId = Objects.requireNonNull(args).getString(EXTRA_ITEM_ID);
    }

    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listItemViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ListItemViewModel.class);

        listItemViewModel.getListItemById(itemId).observe(this, new Observer<ListItem>() {
            @SuppressLint("ResourceType")
            @Override
            public void onChanged(@Nullable ListItem listItem) {
                if (listItem != null) {
                    fragmentDetailBinding.setDetail(listItem);
                    Glide.with(getActivity()).load(listItem.getImgResourcePath()).into(fragmentDetailBinding.imvColoredBackground);
                    //Utils.showToastMessage(getContext(), String.valueOf(listItem.getImgResourcePath()));
                }
            }
        });
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        fragmentDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        View v = fragmentDetailBinding.getRoot();
        fragmentDetailBinding.setEditAction(new OnEditButton() {
            @Override
            public void editListItem() {
                if (Utils.checkPermission_READ_EXTERNAL_STORAGE(getContext())) {
                    Intent i = new Intent(getActivity(), CreateActivity.class);
                    i.putExtra(EXTRA_ITEM_ID, itemId);
                    startActivity(i);
                }
            }
        });
        return v;
    }
}