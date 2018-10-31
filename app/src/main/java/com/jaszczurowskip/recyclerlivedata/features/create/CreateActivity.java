package com.jaszczurowskip.recyclerlivedata.features.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.jaszczurowskip.recyclerlivedata.R;
import com.jaszczurowskip.recyclerlivedata.util.BaseActivity;

/**
 * Created by jaszczurowskip on 12.10.2018
 */
public class CreateActivity extends BaseActivity {
    private static final String EXTRA_ITEM_ID = "EXTRA_ITEM_ID";
    private static final String CREATE_FRAG = "CREATE_FRAG";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Intent i = getIntent();
            FragmentManager manager = getSupportFragmentManager();
            CreateFragment fragment = (CreateFragment) manager.findFragmentByTag(CREATE_FRAG);
            if (fragment == null) {
                if (i.hasExtra(EXTRA_ITEM_ID)) {

                    String itemId = i.getStringExtra(EXTRA_ITEM_ID);
                    fragment = CreateFragment.newInstance(itemId);
                }
                else {
                    fragment = CreateFragment.newInstance();
                }
            }
        addFragmentToActivity(manager, fragment, R.id.root_activity_create, CREATE_FRAG);
    }
}

