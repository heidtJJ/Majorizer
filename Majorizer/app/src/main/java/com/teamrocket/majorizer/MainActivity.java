package com.teamrocket.majorizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.teamrocket.majorizer.Adapters.SectionsPageAdapter;
import com.teamrocket.majorizer.Admin.ChangeCurriculumActivity;
import com.teamrocket.majorizer.Admin.SelectAccountTypeActivity;
import com.teamrocket.majorizer.Admin.UnlockAccountActivity;
import com.teamrocket.majorizer.Advisor.AdvisorHomeFragment;
import com.teamrocket.majorizer.SharedFragments.AccountFragment;
import com.teamrocket.majorizer.Admin.AdminHomeFragment;
import com.teamrocket.majorizer.SharedFragments.NotificationsFragment;
import com.teamrocket.majorizer.Student.GradHomeFragment;
import com.teamrocket.majorizer.Student.UndergradHomeFragment;
import com.teamrocket.majorizer.Admin.Administrator;
import com.teamrocket.majorizer.Advisor.Advisor;
import com.teamrocket.majorizer.Student.GradStudent;
import com.teamrocket.majorizer.Student.UndergradStudent;

public class MainActivity extends AppCompatActivity {

    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    public Account account = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retreive the Account object passed from the LoginManager.
        account = (Account) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());

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
    public void setupViewPager(final ViewPager viewPager) {
        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Add title fragment depending on the type of account.
        if (account instanceof Advisor)
            sectionsPageAdapter.addFragment(new AdvisorHomeFragment(), getResources().getString(R.string.TitleHome));
        else if (account instanceof UndergradStudent)
            sectionsPageAdapter.addFragment(new UndergradHomeFragment(), getResources().getString(R.string.TitleHome));
        else if (account instanceof GradStudent)
            sectionsPageAdapter.addFragment(new GradHomeFragment(), getResources().getString(R.string.TitleHome));
        else
            sectionsPageAdapter.addFragment(new AdminHomeFragment(), getResources().getString(R.string.TitleHome));

        // Add notifications fragment.
        // TO-DO: Make notification fragments for each account if necessary.
        sectionsPageAdapter.addFragment(new NotificationsFragment(), getResources().getString(R.string.TitleNotifications));

        // Admins will not have account tab. They do not have any personal information.
        if (!(account instanceof Administrator))
            sectionsPageAdapter.addFragment(new AccountFragment(), getResources().getString(R.string.TitleAccount));

        // Set the adapter for the viewPager.
        viewPager.setAdapter(sectionsPageAdapter);
    }

    public void createAccount(final View view) {
        Intent selectAccountActivity = new Intent(view.getContext(), SelectAccountTypeActivity.class);
        // Pass this Account object to the main activity.
        selectAccountActivity.putExtra(getText(R.string.AccountObject).toString(), account);
        view.getContext().startActivity(selectAccountActivity);
    }

    public void unlockAccountActivity(final View view) {
        Intent selectAccountActivity = new Intent(view.getContext(), UnlockAccountActivity.class);
        // Pass this Account object to the main activity.
        selectAccountActivity.putExtra(getText(R.string.AccountObject).toString(), account);
        view.getContext().startActivity(selectAccountActivity);
    }

    public void changeCurriculum(final View view) {
        Intent selectAccountActivity = new Intent(view.getContext(), ChangeCurriculumActivity.class);
        // Pass this Account object to the main activity.
        selectAccountActivity.putExtra(getText(R.string.AccountObject).toString(), account);
        view.getContext().startActivity(selectAccountActivity);
    }

    public void viewAcademicProgress(final View view) {

    }
}
