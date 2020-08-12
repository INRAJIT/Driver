package com.mottainai.driver;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class BaseActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private  TextView toolbarTitle;
    protected FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    protected void setup(String title){
        mFragmentManager = getSupportFragmentManager();
        mToolbar = findViewById(R.id.toolbar);
        toolbarTitle = mToolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(mToolbar);
        setToolbarTitle(title);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setToolbarTitle(String title) {
        toolbarTitle.setText(title);
    }

    public void hideToolBar() {
        mToolbar.setVisibility(View.GONE);
    }

    public void addFragment(Fragment fragment, String tag) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.container, fragment, tag);
        mFragmentTransaction.commit();
    }

    public void addFragmentToStack(Fragment fragment, String tag) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.container, fragment, tag);
        mFragmentTransaction.addToBackStack(tag);
        mFragmentTransaction.commit();
    }

    public void replaceFragment(Fragment fragment, String tag) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.container, fragment, tag);
        mFragmentTransaction.commit();
    }
}
