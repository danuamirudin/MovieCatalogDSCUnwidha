package dicoding1.moviecatalog.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Objects;

import dicoding1.moviecatalog.R;
import dicoding1.moviecatalog.database.DB;
import dicoding1.moviecatalog.fragment.FavoriteFragment;
import dicoding1.moviecatalog.fragment.PlayingFragment;
import dicoding1.moviecatalog.fragment.SearchFragment;
import dicoding1.moviecatalog.fragment.UpcomingFragment;
import dicoding1.moviecatalog.utilities.SettingsPrefActivity;
import dicoding1.moviecatalog.utilities.mAppCompatActivity;

public class MainActivity extends mAppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @SuppressLint("StaticFieldLeak")
    public static MaterialSearchView searchView;
    @SuppressLint("StaticFieldLeak")
    public static FrameLayout frameLayout;
    public static MenuItem searchItem;
    public static DB db;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        frameLayout = findViewById(R.id.fragmentContainer);
        searchView = findViewById(R.id.search_view);
        db = new DB(this);
        if (savedInstanceState == null) {
            loadFragment(new PlayingFragment());
            navigationView.setCheckedItem(R.id.nav_playing);
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.now_playing);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        searchItem = menu.findItem(R.id.action_search);
        searchView.setMenuItem(searchItem);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                loadFragment(new SearchFragment());
                Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.search);
                navigationView.setCheckedItem(R.id.nav_search);
            }

            @Override
            public void onSearchViewClosed() {

            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_setting) {
            startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_search) {
            loadFragment(new SearchFragment());
            searchView.showSearch();
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.search);
        } else if (id == R.id.nav_playing) {
            loadFragment(new PlayingFragment());
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.now_playing);
        } else if (id == R.id.nav_upcoming) {
            loadFragment(new UpcomingFragment());
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.upcoming);
        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(this, SettingsPrefActivity.class));
        } else if (id == R.id.nav_favorit) {
            loadFragment(new FavoriteFragment());
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.favorit);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }
}
