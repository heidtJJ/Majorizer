package com.teamrocket.majorizer;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AdminMainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private String clarksonId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clarksonId = getIntent().getStringExtra(getText(R.string.ClarksonId).toString());

        mTextMessage = findViewById(R.id.message);
        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = findViewById(R.id.container);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_dashboard_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_notifications_black_24dp);
    }

    // adds fragments to SectionsPageAdapter and give titles.
    public void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        sectionsPageAdapter.addFragment(new HomeFragment(), getResources().getString(R.string.title_home));
        sectionsPageAdapter.addFragment(new AccountFragment(), getResources().getString(R.string.title_account));
        sectionsPageAdapter.addFragment(new NotificationsFragment(), getResources().getString(R.string.title_notifications));
        viewPager.setAdapter(sectionsPageAdapter);
    }

}