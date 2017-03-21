package cs125.winter2017.uci.appetizer.Search;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import cs125.winter2017.uci.appetizer.R;
import cs125.winter2017.uci.appetizer.daily_targets.DailyTargetActivity;
import cs125.winter2017.uci.appetizer.diet.DietaryRestrictionActivity;
import cs125.winter2017.uci.appetizer.food_diary.DiaryActivity;
import cs125.winter2017.uci.appetizer.location.GetLocationFragment;

public class SearchActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GetLocationFragment.OnLocationAcquireListener, AsyncResponse {

    private GetLocationFragment locationFragment;

    private TextView locationText;

    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        actionBar.setCustomView(R.layout.layout_search_bar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        locationText = (TextView) findViewById(R.id.search_current_location_text);
        locationFragment = (GetLocationFragment) getSupportFragmentManager()
                .findFragmentById(R.id.search_current_location_map);
        locationFragment.setLocationAcquireListener(this);

        findViewById(R.id.search_bar_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String places = retrieveRestaurants();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_diary:
                Intent mainActivityIntent = new Intent(this, DiaryActivity.class);
                startActivity(mainActivityIntent);
                break;
            case R.id.nav_daily_targets:
                Intent dailyGoalsIntent = new Intent(this, DailyTargetActivity.class);
                startActivity(dailyGoalsIntent);
                break;
            case R.id.nav_dietary_restriction:
                Intent dietaryRestrictionIntent = new Intent(this, DietaryRestrictionActivity.class);
                startActivity(dietaryRestrictionIntent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLocationAcquire(GetLocationFragment locationFragment) {
        locationText.setText(locationFragment.getLocationString());
        location = locationFragment.getLocation();
    }

    private String retrieveRestaurants() {

        Search_Places searchPlaces;

        EditText searchBarText = (EditText)findViewById(R.id.search_bar_text);
        String searchQuery = searchBarText.getText().toString();

        RadioGroup radioSearchPriceGroup = (RadioGroup)findViewById(R.id.search_price);
        RadioButton radioSearchPriceButton = (RadioButton)findViewById(radioSearchPriceGroup.getCheckedRadioButtonId());

        int price = radioSearchPriceButton.length();

        String mealType = "Breakfast";

        if(location != null) {
            searchPlaces = new Search_Places(searchQuery, location.getLatitude(), location.getLongitude(), 1000, price, mealType);
            DownloadPlacesTask task = new DownloadPlacesTask();
            task.delegate = this;
            searchPlaces.Search(task);
        }

        return "";
    }

    @Override
    public void processFinish(String output) {
        AlertDialog alertDialog = new AlertDialog.Builder(SearchActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(output);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

}
