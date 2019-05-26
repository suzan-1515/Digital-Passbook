package np.com.krishna.sahakaari.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.evolve.backdroplibrary.BackdropContainer;

import np.com.krishna.R;
import np.com.krishna.sahakaari.fragments.HomeFragment;
import np.com.krishna.sahakaari.fragments.LoanFragment;
import np.com.krishna.sahakaari.fragments.ProfileFragment;
import np.com.krishna.sahakaari.fragments.StatementFragment;
import np.com.krishna.sahakaari.helpers.Session;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayMap<String, String> userCredintials;

    private HomeFragment mHomeFragment;
    private StatementFragment mTransactionFragment;
    private LoanFragment mLoanFragment;
    private ProfileFragment mProfileFragment;

    private Fragment mRootFragment;

    private BackdropContainer mBackdropContainer;
    private FrameLayout mBackContainer;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Session.Response authenticationResponse = Session.isUserAuthenticated(getIntent().getExtras());
        if (!authenticationResponse.status) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        userCredintials = authenticationResponse.credintals;

        initViews();

        mHomeFragment = HomeFragment.newInstance(userCredintials);
        mTransactionFragment = StatementFragment.newInstance(userCredintials);
        mLoanFragment = LoanFragment.newInstance(userCredintials);
        mProfileFragment = ProfileFragment.newInstance(userCredintials);

        if (savedInstanceState == null) {
            pushFragment(mHomeFragment, "home");

        }

    }

    private void initViews() {
        mBackdropContainer = findViewById(R.id.backdrop_container);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mNavigationView = findViewById(R.id.navigationView);
        mNavigationView.setNavigationItemSelectedListener(this);
        mBackContainer = findViewById(R.id.back_container);
        mBackdropContainer.attachToolbar(mToolbar)
                .dropInterpolator(new LinearInterpolator())
                .build();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menuHome:
                pushFragment(mHomeFragment, "home");
                return true;
            case R.id.menuTransaction:
                pushFragment(mTransactionFragment, "transaction");
                return true;
            case R.id.menuLoan:
                pushFragment(mLoanFragment, "loan");
                return true;
            case R.id.menuProfile:
                pushFragment(mProfileFragment, "profile");
                return true;
        }
        pushFragment(mHomeFragment, "home");
        return true;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        ViewTreeObserver vto = mBackdropContainer.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mBackdropContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int conHeight = mBackdropContainer.getMeasuredHeight();

                ViewTreeObserver vto = mNavigationView.getViewTreeObserver();
                vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mBackContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        int height = mBackContainer.getMeasuredHeight();
                        mBackdropContainer.dropHeight(conHeight - (height - 120)).build();
                    }
                });

            }
        });
    }

    private void pushFragment(Fragment fragment, String tag) {

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragmentByTag = supportFragmentManager.findFragmentByTag(tag);
        if (fragmentByTag == null) {
            if (tag.equals("home") && mRootFragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.front_container, mRootFragment).commit();
            } else {
                FragmentTransaction fragmentTransaction = supportFragmentManager
                        .beginTransaction();
                fragmentTransaction.add(R.id.front_container, fragment, tag);
                fragmentTransaction.commit();
            }
        } else {
            if (tag.equals("home"))
                mRootFragment = fragmentByTag;
            supportFragmentManager.beginTransaction().replace(R.id.front_container, fragmentByTag).commit();

        }


        mBackdropContainer.closeBackview();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signoutMenu:
                userCredintials.clear();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}