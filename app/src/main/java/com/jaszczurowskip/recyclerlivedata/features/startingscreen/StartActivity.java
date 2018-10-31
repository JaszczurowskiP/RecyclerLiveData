package com.jaszczurowskip.recyclerlivedata.features.startingscreen;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.jaszczurowskip.recyclerlivedata.R;
import com.jaszczurowskip.recyclerlivedata.features.list.ListFragment;
import com.jaszczurowskip.recyclerlivedata.util.BaseActivity;

public class StartActivity extends BaseActivity {
    private static final String START_FRAG = "START_FRAG";
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        FragmentManager manager = getSupportFragmentManager();
        StartingFragment fragment = (StartingFragment) manager.findFragmentByTag(START_FRAG);
        if (fragment == null) {
            fragment = StartingFragment.newInstance();
        }
        addFragmentToActivity(manager, fragment, R.id.root_activity_start, START_FRAG);
    }
}
