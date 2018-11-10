package com.teamrocket.majorizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.teamrocket.majorizer.Fragments.AccountFragment;
import com.teamrocket.majorizer.Fragments.Admin.AdminHomeFragment;
import com.teamrocket.majorizer.Fragments.Admin.AdminNotificationsFragment;
import com.teamrocket.majorizer.Fragments.Advisor.AdvisorHomeFragment;
import com.teamrocket.majorizer.Fragments.Graduate.GradHomeFragment;
import com.teamrocket.majorizer.Fragments.Undergraduate.UndergradHomeFragment;
import com.teamrocket.majorizer.UserGroups.Account;
import com.teamrocket.majorizer.UserGroups.Administrator;
import com.teamrocket.majorizer.UserGroups.Advisor;
import com.teamrocket.majorizer.UserGroups.GradStudent;
import com.teamrocket.majorizer.UserGroups.UndergradStudent;

public class MainActivity extends AppCompatActivity {

    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    public Account account = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retreive the Account object passed from the LoginManager.
        account = (Account) getIntent().getSerializableExtra("MyClass");


        // Set up the ViewPager with the sections adapter.
        viewPager = findViewById(R.id.container);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        // Make first tab, home.
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);

        // Set up tabs depending on whether user is an admin or not. Admins have only 2 tabs.
        // Admins do not have an account tab because they do not carry any personal information.
        if (!(account instanceof Administrator)) {
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_notifications_black_24dp);
            tabLayout.getTabAt(2).setIcon(R.drawable.ic_dashboard_black_24dp);
        } else {
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_notifications_black_24dp);
        }

    }

    // Adds fragments to SectionsPageAdapter and give titles.
    public void setupViewPager(ViewPager viewPager) {
        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Add title fragment depending on the type of account.
        if (account instanceof Advisor)
            sectionsPageAdapter.addFragment(new AdvisorHomeFragment(), getResources().getString(R.string.title_home));
        else if (account instanceof UndergradStudent)
            sectionsPageAdapter.addFragment(new UndergradHomeFragment(), getResources().getString(R.string.title_home));
        else if (account instanceof GradStudent)
            sectionsPageAdapter.addFragment(new GradHomeFragment(), getResources().getString(R.string.title_home));
        else
            sectionsPageAdapter.addFragment(new AdminHomeFragment(), getResources().getString(R.string.title_home));

        // Add notifications fragment.
        // TO-DO: Make notification fragments for each account if necessary.
        sectionsPageAdapter.addFragment(new AdminNotificationsFragment(), getResources().getString(R.string.title_notifications));

        // Admins will not have account tab. They do not have any personal information.
        if (!(account instanceof Administrator))
            sectionsPageAdapter.addFragment(new AccountFragment(), getResources().getString(R.string.title_account));

        // Set the adapter for the viewPager.
        viewPager.setAdapter(sectionsPageAdapter);
    }

    public void createAccount(View view) {
        Intent selectAccountActivity = new Intent(view.getContext(), SelectAccountTypeActivity.class);
        view.getContext().startActivity(selectAccountActivity);
    }

    public void unlockAccountActivity(View view) {
        Intent selectAccountActivity = new Intent(view.getContext(), UnlockAccountActivity.class);
        // Pass this Account object to the main activity.
        selectAccountActivity.putExtra("MyClass", account);
        view.getContext().startActivity(selectAccountActivity);
    }

    public void searchAccount(View view) {
    }

    public void changeCourses(View view) {
    }

    public void viewAcademicProgress(View view) {
    }
}
