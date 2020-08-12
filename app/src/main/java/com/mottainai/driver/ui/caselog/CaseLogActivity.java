package com.mottainai.driver.ui.caselog;

import android.os.Bundle;

import com.mottainai.driver.BaseActivity;
import com.mottainai.driver.R;

public class CaseLogActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_log);
        init();
    }

    private void init() {
        Bundle bundle = getIntent().getExtras();
        String page = bundle.getString("page");
        if(page.equals("list")) {
            setup(getString(R.string.case_log_list));
            replaceFragment(new CaseLogListFragment(), CaseLogListFragment.class.getName());
        } else {
            setup(getString(R.string.case_log));
            hideToolBar();
            replaceFragment(new CaseLogSuccessFragment(), CaseLogSuccessFragment.class.getName());
        }
    }
}
