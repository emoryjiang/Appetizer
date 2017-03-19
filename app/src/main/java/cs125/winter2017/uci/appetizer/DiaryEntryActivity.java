package cs125.winter2017.uci.appetizer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.Locale;

import cs125.winter2017.uci.appetizer.food_diary.FoodDiaryDay;
import cs125.winter2017.uci.appetizer.food_diary.FoodDiaryEntry;

public class DiaryEntryActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener {

    public static final String EDITING_ENTRY =
            "cs125.winter2017.uci.appetizer.DiaryEntryActivity.EDITING_ENTRY";

    private EditText nameField;
    private TextView dateField;
    private EditText calorieField;
    private EditText fatField;
    private EditText proteinField;
    private EditText cholesterolField;
    private EditText sugarField;
    private EditText carbohydrateField;
    private EditText sodiumField;
    private EditText fiberField;

    private DateTime date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nameField = (EditText) findViewById(R.id.diary_entry_edit_name);
        dateField = (TextView) findViewById(R.id.diary_entry_edit_date);
        calorieField = (EditText) findViewById(R.id.diary_entry_edit_calorie);
        fatField = (EditText) findViewById(R.id.diary_entry_edit_fat);
        proteinField = (EditText) findViewById(R.id.diary_entry_edit_protein);
        cholesterolField = (EditText) findViewById(R.id.diary_entry_edit_cholesterol);
        sugarField = (EditText) findViewById(R.id.diary_entry_edit_sugar);
        carbohydrateField = (EditText) findViewById(R.id.diary_entry_edit_carbohydrates);
        sodiumField = (EditText) findViewById(R.id.diary_entry_edit_sodium);
        fiberField = (EditText) findViewById(R.id.diary_entry_edit_fiber);

        if (getIntent().getBooleanExtra(EDITING_ENTRY, false)){
            getSupportActionBar().setTitle(getString(R.string.title_activity_diary_entry_edit));

            Bundle data = getIntent().getBundleExtra("DATA");

            nameField.setText(data.getString("NAME"));

            date = (DateTime) data.getSerializable("DATE");
            dateField.setText(FoodDiaryDay.HUMAN_READABLE_FORMATTER.print(date));
            dateField.setBackground(null);

            calorieField.setText(
                    String.format(Locale.getDefault(), "%.2f", data.getDouble("CALORIE")));
            fatField.setText(String.format(Locale.getDefault(), "%.2f", data.getDouble("FAT")));
            proteinField.setText(
                    String.format(Locale.getDefault(), "%.2f", data.getDouble("PROTEIN")));
            cholesterolField.setText(
                    String.format(Locale.getDefault(), "%.2f", data.getDouble("CHOLESTEROL")));
            sugarField.setText(
                    String.format(Locale.getDefault(), "%.2f", data.getDouble("SUGAR")));
            carbohydrateField.setText(
                    String.format(Locale.getDefault(), "%.2f", data.getDouble("CARBOHYDRATE")));
            sodiumField.setText(
                    String.format(Locale.getDefault(), "%.2f", data.getDouble("SODIUM")));
            fiberField.setText(
                    String.format(Locale.getDefault(), "%.2f", data.getDouble("FIBER")));
        } else {
            getSupportActionBar().setTitle(getString(R.string.title_activity_diary_entry_new));

            date = new DateTime();
            dateField.setText(FoodDiaryDay.HUMAN_READABLE_FORMATTER.print(date));
            dateField.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View currentFocus = getCurrentFocus();
                    if (currentFocus != null)
                        currentFocus.clearFocus();
                    DatePickerDialog datePickerDialog = new DatePickerDialog( DiaryEntryActivity.this,
                            DiaryEntryActivity.this, date.getYear(), date.getMonthOfYear() - 1,
                            date.getDayOfMonth());
                    datePickerDialog.show();

                }
            });

            calorieField.setText(
                    String.format(Locale.getDefault(), "%.2f", 0f));
            fatField.setText(String.format(Locale.getDefault(), "%.2f", 0f));
            proteinField.setText(
                    String.format(Locale.getDefault(), "%.2f", 0f));
            cholesterolField.setText(
                    String.format(Locale.getDefault(), "%.2f", 0f));
            sugarField.setText(
                    String.format(Locale.getDefault(), "%.2f", 0f));
            carbohydrateField.setText(
                    String.format(Locale.getDefault(), "%.2f", 0f));
            sodiumField.setText(
                    String.format(Locale.getDefault(), "%.2f", 0f));
            fiberField.setText(
                    String.format(Locale.getDefault(), "%.2f", 0f));
        }

        findViewById(R.id.diary_entry_edit_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryEntryActivity.this.onSubmit();
            }
        });
    }

    @Override
    public void onBackPressed(){
        new ExitAlertDialogFragment().show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (getIntent().getBooleanExtra(EDITING_ENTRY, false))
            getMenuInflater().inflate(R.menu.menu_diary_entry_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
                new ExitAlertDialogFragment().show(getSupportFragmentManager(), "dialog");
                return true;
            case R.id.diary_entry_edit_delete:
                new DeleteAlertDialogFragment().show(getSupportFragmentManager(), "dialog");
                return true;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        date = date.withYear(year).withMonthOfYear(month + 1).withDayOfMonth(dayOfMonth);
        dateField.setText(FoodDiaryDay.HUMAN_READABLE_FORMATTER.print(date));
    }

    private void onCancel(){
        setResult(RESULT_CANCELED);
        finish();
    }

    private void onDelete(){
        Intent result = new Intent();
        result.putExtra("DELETE", true);

        setResult(RESULT_OK, result);
        finish();
    }

    private void onSubmit(){
        Bundle data = new Bundle();

        data.putString("NAME", nameField.getText().toString());
        data.putSerializable("DATE", date);
        data.putDouble("CALORIE", Double.parseDouble(calorieField.getText().toString()));
        data.putDouble("FAT", Double.parseDouble(fatField.getText().toString()));
        data.putDouble("PROTEIN", Double.parseDouble(proteinField.getText().toString()));
        data.putDouble("CHOLESTEROL", Double.parseDouble(cholesterolField.getText().toString()));
        data.putDouble("SUGAR", Double.parseDouble(sugarField.getText().toString()));
        data.putDouble("SODIUM", Double.parseDouble(sodiumField.getText().toString()));
        data.putDouble("CARBOHYDRATES", Double.parseDouble(carbohydrateField.getText().toString()));
        data.putDouble("FIBER", Double.parseDouble(fiberField.getText().toString()));

        Intent result = new Intent();
        result.putExtra("DATA", data);

        setResult(RESULT_OK, result);
        finish();
    }

    public static class ExitAlertDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            return new AlertDialog.Builder(getActivity())
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((DiaryEntryActivity) getActivity()).onCancel();
                    }
                })
                .setNegativeButton(R.string.alert_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .setMessage(R.string.diary_entry_unsaved)
                .create();
        }
    }

    public static class DeleteAlertDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            return new AlertDialog.Builder(getActivity())
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((DiaryEntryActivity) getActivity()).onDelete();
                        }
                    })
                    .setNegativeButton(R.string.alert_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    })
                    .setMessage(R.string.diary_entry_delete_warning)
                    .create();
        }
    }

}
