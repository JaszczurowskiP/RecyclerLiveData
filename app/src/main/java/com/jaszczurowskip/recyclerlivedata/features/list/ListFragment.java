package com.jaszczurowskip.recyclerlivedata.features.list;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaszczurowskip.recyclerlivedata.R;
import com.jaszczurowskip.recyclerlivedata.RecyclerLiveDataApplication;
import com.jaszczurowskip.recyclerlivedata.databinding.FragmentListBinding;
import com.jaszczurowskip.recyclerlivedata.datasource.ListItem;
import com.jaszczurowskip.recyclerlivedata.features.create.CreateActivity;
import com.jaszczurowskip.recyclerlivedata.features.list.actions.OnAddButton;
import com.jaszczurowskip.recyclerlivedata.util.Utils;
import com.jaszczurowskip.recyclerlivedata.viewmodel.ListItemCollectionViewModel;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

/**
 * Created by jaszczurowskip on 12.10.2018
 */
public class ListFragment extends Fragment {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ListItemCollectionViewModel listItemCollectionViewModel;
    private List<ListItem> listOfData;
    private ListAdapter adapter;
    private FragmentListBinding fragmentListBinding;

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((RecyclerLiveDataApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listItemCollectionViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ListItemCollectionViewModel.class);
        listItemCollectionViewModel.getListItems().observe(this, new Observer<List<ListItem>>() {
            @Override
            public void onChanged(@Nullable List<ListItem> listItems) {
                if (listOfData == null) {
                    setListData(listItems);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        View v = fragmentListBinding.getRoot();
        fragmentListBinding.setAddAction(new OnAddButton() {
            @Override
            public void addListItem() {
                if (Utils.checkPermission_READ_EXTERNAL_STORAGE(getContext())) {
                    startCreateActivity();
                }
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void startCreateActivity() {
        startActivity(new Intent(getActivity(), CreateActivity.class));
    }

    public void setListData(List<ListItem> listOfData) {
        this.listOfData = listOfData;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        fragmentListBinding.recyclerListActivity.setLayoutManager(layoutManager);
        adapter = new ListAdapter(listOfData, getContext(), getActivity());
        fragmentListBinding.recyclerListActivity.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(
                fragmentListBinding.recyclerListActivity.getContext(),
                layoutManager.getOrientation()
        );
        itemDecoration.setDrawable(
                Objects.requireNonNull(ContextCompat.getDrawable(
                        Objects.requireNonNull(getActivity()),
                        R.drawable.divider_white
                ))
        );
        fragmentListBinding.recyclerListActivity.addItemDecoration(
                itemDecoration
        );

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(fragmentListBinding.recyclerListActivity);
    }

    private ItemTouchHelper.Callback createHelperCallback() {
        return new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView1, @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, @NonNull int swipeDir) {
                final int position = viewHolder.getAdapterPosition();
                listItemCollectionViewModel.deleteListItem(
                        listOfData.get(position)
                );
                listOfData.remove(position);
                adapter.notifyItemRemoved(position);
            }
        };
    }
}
