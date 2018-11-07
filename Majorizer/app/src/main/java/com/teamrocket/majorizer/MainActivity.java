package com.teamrocket.majorizer;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.teamrocket.majorizer.AppUtility.Utility;
import com.teamrocket.majorizer.UserGroups.Account;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private String clarksonId;
    Account.AccountType accountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clarksonId = getIntent().getStringExtra(getText(R.string.ClarksonId).toString());

        String strAccountType = getIntent().getStringExtra(getText(R.string.Type).toString());
        accountType = Utility.getAccountType(strAccountType);


        mTextMessage = findViewById(R.id.message);
        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = findViewById(R.id.container);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        // Make first tab, home.
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);

        // Set up tabs depending on whether user is an admin or not. Admins have only 2 tabs.
        // Admins do not have an account tab because they do not carry any personal information.

        Toast.makeText(this, strAccountType + " " + accountType.toString(), Toast.LENGTH_SHORT).show();
        if (accountType != Account.AccountType.ADMIN) {
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_dashboard_black_24dp);
            tabLayout.getTabAt(2).setIcon(R.drawable.ic_notifications_black_24dp);
        } else {
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_notifications_black_24dp);
        }

    }

    // adds fragments to SectionsPageAdapter and give titles.
    public void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        // Add title fragment.
        sectionsPageAdapter.addFragment(new AdminHomeFragment(), getResources().getString(R.string.title_home));

        // Admins will not have account tab. They do not have any personal information.
        if (accountType == Account.AccountType.UNDERGRAD || accountType == Account.AccountType.GRAD)
            sectionsPageAdapter.addFragment(new AccountFragment(), getResources().getString(R.string.title_account));

        // Add notifications fragment.
        sectionsPageAdapter.addFragment(new AdminNotificationsFragment(), getResources().getString(R.string.title_notifications));

        viewPager.setAdapter(sectionsPageAdapter);
    }

}