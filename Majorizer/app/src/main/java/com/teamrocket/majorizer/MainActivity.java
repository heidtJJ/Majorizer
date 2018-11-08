package com.teamrocket.majorizer;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.teamrocket.majorizer.AppUtility.Utility;
import com.teamrocket.majorizer.UserGroups.Account;

public class MainActivity extends AppCompatActivity {

    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private Account account = null;
    Account.AccountType accountType;
    private String clarksonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clarksonId = getIntent().getStringExtra(getText(R.string.ClarksonUsername).toString());
        String strAccountType = getIntent().getStringExtra(getText(R.string.Type).toString());

        // Put all of the user's data into the intent. This may save need for bandwidth use later.
        String advisor1 = getIntent().getStringExtra("Advisor1").toString();
        String advisor2 = getIntent().getStringExtra("Advisor2").toString();
        String firstName = getIntent().getStringExtra("FirstName").toString();
        String lastName = getIntent().getStringExtra("LastName").toString();
        String major1 = getIntent().getStringExtra("Major1").toString();
        String major2 = getIntent().getStringExtra("Major2").toString();
        String minor1 = getIntent().getStringExtra("Minor1").toString();
        String minor2 = getIntent().getStringExtra("Minor2").toString();
        String username = getIntent().getStringExtra("Username").toString();
        accountType = Utility.getAccountType(getIntent().getStringExtra("Type").toString());
        account = (Account) getIntent().getSerializableExtra("MyClass");


        Toast.makeText(this, "ID: " + account.getId(), Toast.LENGTH_SHORT).show();

        System.out.println("afdsasfdsfdadsad" + advisor1 + " " + advisor2 + " " + firstName + " " + lastName + " " + major1 + " " + username);
        System.out.println("afdsasfdsfdadsad2" + major2 + " " + minor1 + " " + minor2 + " " + accountType);

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