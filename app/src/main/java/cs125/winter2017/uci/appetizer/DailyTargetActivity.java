package cs125.winter2017.uci.appetizer;

import android.content.Context;
import android.content.Intent;
import android.database.DefaultDatabaseErrorHandler;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

import cs125.winter2017.uci.appetizer.daily_targets.DailyTargets;

public class DailyTargetActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText caloriesField;
    EditText fatField;
    EditText proteinField;
    EditText cholesterolField;
    EditText sugarField;
    EditText carbohydratesField;
    EditText sodiumField;
    EditText fiberField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_target);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        caloriesField = (EditText) findViewById(R.id.daily_target_calorie);
        caloriesField.addTextChangedListener(new FieldWatcher(caloriesField));

        fatField = (EditText) findViewById(R.id.daily_target_fat);
        fatField.addTextChangedListener(new FieldWatcher(fatField));

        proteinField = (EditText) findViewById(R.id.daily_target_protein);
        proteinField.addTextChangedListener(new FieldWatcher(proteinField));

        cholesterolField = (EditText) findViewById(R.id.daily_target_cholesterol);
        cholesterolField.addTextChangedListener(new FieldWatcher(cholesterolField));

        sugarField = (EditText) findViewById(R.id.daily_target_sugar);
        sugarField.addTextChangedListener(new FieldWatcher(sugarField));

        carbohydratesField = (EditText) findViewById(R.id.daily_target_carbohydrates);
        carbohydratesField.addTextChangedListener(new FieldWatcher(carbohydratesField));

        sodiumField = (EditText) findViewById(R.id.daily_target_sodium);
        sodiumField.addTextChangedListener(new FieldWatcher(sodiumField));

        fiberField = (EditText) findViewById(R.id.daily_target_fiber);
        fiberField.addTextChangedListener(new FieldWatcher(fiberField));
    }

    @Override
    public void onResume(){
        super.onResume();

        updateFields();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()){
            case R.id.nav_home:
                Intent homeIntent = new Intent(this, MainActivity.class);
                startActivity(homeIntent);
                break;
            case R.id.nav_search:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateFields(){
        caloriesField.setText(String.format(Locale.getDefault(), "%.1f",
                DailyTargets.getCalorie(this)));
        fatField.setText(String.format(Locale.getDefault(), "%.1f",
                DailyTargets.getFat(this)));
        proteinField.setText(String.format(Locale.getDefault(), "%.1f",
                DailyTargets.getProtein(this)));
        cholesterolField.setText(String.format(Locale.getDefault(), "%.1f",
                DailyTargets.getCholesterol(this)));
        sugarField.setText(String.format(Locale.getDefault(), "%.1f",
                DailyTargets.getSugar(this)));
        carbohydratesField.setText(String.format(Locale.getDefault(), "%.1f",
                DailyTargets.getCarbs(this)));
        sodiumField.setText(String.format(Locale.getDefault(), "%.1f",
                DailyTargets.getSodium(this)));
        fiberField.setText(String.format(Locale.getDefault(), "%.1f",
                DailyTargets.getFiber(this)));
    }

    private static class FieldWatcher implements TextWatcher {

        private final EditText view;
        private FieldWatcher(EditText view){
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable editable) {
            Context viewContext = view.getContext();
            String valueString = editable.toString();
            if (valueString.isEmpty())
                editable.append("0");
            double value = Double.parseDouble(editable.toString());

            switch (view.getId()){
                case R.id.daily_target_calorie:
                    DailyTargets.setCalorie(viewContext, value);
                    break;
                case R.id.daily_target_fat:
                    DailyTargets.setFat(viewContext, value);
                    break;
                case R.id.daily_target_protein:
                    DailyTargets.setProtein(viewContext, value);
                    break;
                case R.id.daily_target_cholesterol:
                    DailyTargets.setCholesterol(viewContext, value);
                    break;
                case R.id.daily_target_sugar:
                    DailyTargets.setSugar(viewContext, value);
                    break;
                case R.id.daily_target_carbohydrates:
                    DailyTargets.setCarbs(viewContext, value);
                    break;
                case R.id.daily_target_sodium:
                    DailyTargets.setSodium(viewContext, value);
                    break;
                case R.id.daily_target_fiber:
                    DailyTargets.setFat(viewContext, value);
                    break;
            }


        }
    }

}
