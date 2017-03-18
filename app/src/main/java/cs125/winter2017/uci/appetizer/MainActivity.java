package cs125.winter2017.uci.appetizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

import cs125.winter2017.uci.appetizer.daily_targets.DailyTargets;
import cs125.winter2017.uci.appetizer.food_diary.FoodDiary;
import cs125.winter2017.uci.appetizer.food_diary.FoodDiaryDay;
import cs125.winter2017.uci.appetizer.food_diary.FoodDiaryEntry;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout diaryFeed;
    private TextView diaryFeedEmpty;
    private TextView diaryOverviewNutrientValue;
    private TextView diaryOverviewNutrientTarget;
    private TextView diaryOverviewNutrientUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        diaryOverviewNutrientValue = (TextView) findViewById(R.id.diary_overview_nutrient_value);
        diaryOverviewNutrientTarget = (TextView) findViewById(R.id.diary_overview_nutrient_target);
        diaryOverviewNutrientUnits = (TextView) findViewById(R.id.diary_overview_nutrient_units);

        diaryFeed = (LinearLayout) findViewById(R.id.diary_feed);
        diaryFeedEmpty = (TextView) diaryFeed.findViewById(R.id.diary_feed_empty);

        //findViewById(R.id.nav_home).setActivated(false);
    }

    @Override
    protected void onResume(){
        super.onResume();
        updateDiaryOverview();
        updateFoodDiaryList();
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
        switch (item.getItemId())
        {
            case R.id.nav_search:
                break;
            case R.id.nav_daily_targets:
                Intent dailyGoalsIntent = new Intent(this, DailyTargetActivity.class);
                startActivity(dailyGoalsIntent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // TODO: add the name of the nutrient you are tracking to the card
    private void updateDiaryOverview(){
        FoodDiaryDay todaysNutrients = FoodDiary.getInstance().getTodaysEntries();
        diaryOverviewNutrientValue.setText(
                String.format(Locale.getDefault(), "%d", (int) todaysNutrients.getCalorie()));

        diaryOverviewNutrientTarget.setText(
                String.format(Locale.getDefault(), "%d", (int) DailyTargets.getCalorie(this)));
        diaryOverviewNutrientUnits.setText(getString(R.string.calorie_units));

    }

    private void updateFoodDiaryList(){

        diaryFeed.removeAllViews();

        FoodDiary foodDiary = FoodDiary.getInstance();
        if (foodDiary.numEntries() == 0){
            diaryFeed.addView(diaryFeedEmpty);
            return;
        }

        LayoutInflater layoutInflater = getLayoutInflater();

        for (FoodDiaryDay foodDiaryDay : foodDiary.entries.values()){
            LinearLayout diaryDay = (LinearLayout) layoutInflater.inflate(
                    R.layout.layout_diary_day, null);
            ((TextView)diaryDay.findViewById(R.id.diary_day_date))
                    .setText(foodDiaryDay.getHumanReadableDate());

            for (FoodDiaryEntry entry : foodDiaryDay){
                final LinearLayout diaryDayCard = (LinearLayout) layoutInflater.inflate(
                        R.layout.layout_diary_day_card, null);
                ((TextView)diaryDayCard.findViewById(R.id.diary_day_card_name))
                        .setText(entry.getName());
                ((TextView)diaryDayCard.findViewById(R.id.diary_day_card_calories))
                        .setText(String.format("%s", (int)entry.getCalorie()));

                ((TextView)diaryDayCard.findViewById(R.id.diary_day_card_fat_value))
                        .setText(String.format(Locale.getDefault(), "%.1f", entry.getFat()));
                ((TextView)diaryDayCard.findViewById(R.id.diary_day_card_cholesterol_value))
                        .setText(String.format(Locale.getDefault(), "%.1f",  entry.getCholesterol()));
                ((TextView)diaryDayCard.findViewById(R.id.diary_day_card_sodium_value))
                        .setText(String.format(Locale.getDefault(), "%.1f",  entry.getSodium()));
                ((TextView)diaryDayCard.findViewById(R.id.diary_day_card_carbohydrates_value))
                        .setText(String.format(Locale.getDefault(), "%.1f", entry.getCarbs()));
                ((TextView)diaryDayCard.findViewById(R.id.diary_day_card_fiber_value))
                        .setText(String.format(Locale.getDefault(), "%.1f", entry.getFiber()));
                ((TextView)diaryDayCard.findViewById(R.id.diary_day_card_sugar_value))
                        .setText(String.format(Locale.getDefault(), "%.1f", entry.getSugar()));
                ((TextView)diaryDayCard.findViewById(R.id.diary_day_card_protein_value))
                        .setText(String.format(Locale.getDefault(), "%.1f", entry.getProtein()));

                final FrameLayout nutrientView =
                        (FrameLayout) diaryDayCard.findViewById(R.id.diary_day_card_nutrients);
                // TODO: go to the entry page when clicked
                diaryDayCard.setActivated(false);
                diaryDayCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (nutrientView.getVisibility() == View.GONE) {
                            nutrientView.setVisibility(View.VISIBLE);
                            diaryDayCard.setActivated(true);
                        } else
                        {
                            nutrientView.setVisibility(View.GONE);
                            diaryDayCard.setActivated(false);
                        }
                    }
                });
                diaryDay.addView(diaryDayCard);
            }
            diaryFeed.addView(diaryDay);
        }

    }
}
