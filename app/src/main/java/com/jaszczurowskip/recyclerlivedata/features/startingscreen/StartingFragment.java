package com.jaszczurowskip.recyclerlivedata.features.startingscreen;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaszczurowskip.recyclerlivedata.R;
import com.jaszczurowskip.recyclerlivedata.RecyclerLiveDataApplication;
import com.jaszczurowskip.recyclerlivedata.databinding.FragmentStartingBinding;
import com.jaszczurowskip.recyclerlivedata.features.list.ListActivity;
import com.jaszczurowskip.recyclerlivedata.features.startingscreen.actions.OnCreatePhoto;
import com.jaszczurowskip.recyclerlivedata.features.startingscreen.actions.OnGalleryBrowse;
import com.jaszczurowskip.recyclerlivedata.util.Utils;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartingFragment extends Fragment {


    public StartingFragment() {
        // Required empty public constructor
    }

    public static StartingFragment newInstance() {
        return new StartingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((RecyclerLiveDataApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentStartingBinding fragmentStartingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_starting, container, false);
        View v = fragmentStartingBinding.getRoot();
        fragmentStartingBinding.setCreatePhotoAction(new OnCreatePhoto() {
            @Override
            public void createPhoto() {
                Intent cIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cIntent, 0);
                Utils.showToastMessage(getContext(), "create");
                // TODO: Capture images and save to sdcard
            }
        });
        fragmentStartingBinding.setBrowseGalleryAction(new OnGalleryBrowse() {
            @Override
            public void browseGallery() {
                Intent i = new Intent(getActivity(), ListActivity.class);
                startActivity(i);
            }
        });
        return v;
    }
}
