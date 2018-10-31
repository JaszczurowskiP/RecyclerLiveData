package com.jaszczurowskip.recyclerlivedata.features.create;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ToxicBakery.viewpager.transforms.DepthPageTransformer;
import com.bumptech.glide.Glide;
import com.jaszczurowskip.recyclerlivedata.R;
import com.jaszczurowskip.recyclerlivedata.RecyclerLiveDataApplication;
import com.jaszczurowskip.recyclerlivedata.databinding.FragmentCreateBinding;
import com.jaszczurowskip.recyclerlivedata.databinding.ItemStartingPagerBinding;
import com.jaszczurowskip.recyclerlivedata.datasource.ListItem;
import com.jaszczurowskip.recyclerlivedata.features.create.actions.OnBackButton;
import com.jaszczurowskip.recyclerlivedata.features.create.actions.OnDoneButton;
import com.jaszczurowskip.recyclerlivedata.features.list.ListActivity;
import com.jaszczurowskip.recyclerlivedata.util.Utils;
import com.jaszczurowskip.recyclerlivedata.viewmodel.CreateListItemViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

/**
 * Created by jaszczurowskip on 12.10.2018
 */
public class CreateFragment extends Fragment {
    private static final String EXTRA_ITEM_ID = "EXTRA_ITEM_ID";
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private int maxSize = 15;
    private String viewPagerImg;
    private List<String> imagesPath;
    private String itemId;
    private ViewPager drawableViewPager;
    private CreateListItemViewModel createListItemViewModel;
    private FragmentCreateBinding fragmentCreateBinding;

    public CreateFragment() {
        // Required empty public constructor
    }

    public static CreateFragment newInstance(@NonNull final String itemId) {
        CreateFragment fragment = new CreateFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_ITEM_ID, itemId);
        fragment.setArguments(args);
        return fragment;
    }

    public static CreateFragment newInstance() {
        return new CreateFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((RecyclerLiveDataApplication) Objects.requireNonNull(getActivity()).getApplication())
                .getApplicationComponent()
                .inject(this);
        Bundle args = getArguments();
        imagesPath = Utils.getAllShownImagesPath(getActivity());
        if (args != null) {
            this.itemId = Objects.requireNonNull(args).getString(EXTRA_ITEM_ID);
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        createListItemViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(CreateListItemViewModel.class);
        if (itemId != null) {
            createListItemViewModel.getListItemById(itemId).observe(this, new Observer<ListItem>() {
                @Override
                public void onChanged(@Nullable ListItem listItem) {
                    fragmentCreateBinding.edtCreateMessage.setText(listItem.getMessage());
                    //Utils.showToastMessage(getContext(), String.valueOf(colorResPosition));
                }
            });
        }
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        fragmentCreateBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create, container, false);
        View v = fragmentCreateBinding.getRoot();
        fragmentCreateBinding.setBackAction(new OnBackButton() {
            @Override
            public void backToList() {
                startListActivity();
            }
        });
        fragmentCreateBinding.setDoneAction(new OnDoneButton() {
            @Override
            public void addItemToDatabase() {
                ListItem listItem;
                if (itemId != null) {
                    listItem = new ListItem(
                            itemId,
                            fragmentCreateBinding.edtCreateMessage.getText().toString(),
                            viewPagerImg
                    );
                } else {
                    listItem = new ListItem(
                            getDate(),
                            fragmentCreateBinding.edtCreateMessage.getText().toString(),
                            viewPagerImg);
                }
                createListItemViewModel.addNewItemToDatabase(listItem);
                //Utils.showToastMessage(getContext(), String.valueOf(drawableViewPager.getCurrentItem()));
                startListActivity();
            }
        });

        DrawablePagerAdapter drawablePagerAdapter = new DrawablePagerAdapter();
        drawableViewPager = v.findViewById(R.id.vp_create_drawable);
        drawableViewPager.setAdapter(drawablePagerAdapter);
        drawableViewPager.setPageTransformer(true, new DepthPageTransformer());
        fragmentCreateBinding.vpiCreateDrawable.setViewPager(drawableViewPager);
        return v;
    }

    private void startListActivity() {
        startActivity(new Intent(getActivity(), ListActivity.class));
    }

    public String getDate() {
        Date currentDate = Calendar.getInstance().getTime();
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd/kk:mm:ss");
        return format.format(currentDate);
    }

    private class DrawablePagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            ItemStartingPagerBinding itemStartingPagerBinding = DataBindingUtil.inflate(inflater, R.layout.item_starting_pager, container, false);
            View v = itemStartingPagerBinding.getRoot();
            viewPagerImg = imagesPath.get(position);
            Glide.with(getActivity()).load(viewPagerImg).into(itemStartingPagerBinding.imvPagerImage);
            container.addView(itemStartingPagerBinding.imvPagerImage);
            return v;
        }

        @Override
        public void destroyItem(@NonNull final ViewGroup collection, final int position, @NonNull final Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return imagesPath.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull final View view, @NonNull final Object object) {
            return view == object;
        }
    }
}
