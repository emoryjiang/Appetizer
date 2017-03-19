package cs125.winter2017.uci.appetizer.food_diary;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by Nokura on 3/18/2017.
 */

public class FoodDiaryDBHelper extends SQLiteOpenHelper {

    public static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormat.forPattern("yyyy-MM-dd");

    public static final String DATABASE_NAME = "Appetizer.db";
    public static final String ENTRIES_TABLE_NAME = "entries";
    public static final String ENTRIES_COLUMN_ID = "id";
    public static final String ENTRIES_COLUMN_NAME = "name";
    public static final String ENTRIES_COLUMN_DATE = "date";
    public static final String ENTRIES_COLUMN_SERVINGS = "servings";
    public static final String ENTRIES_COLUMN_CALORIE = "calorie";
    public static final String ENTRIES_COLUMN_FAT = "fat";
    public static final String ENTRIES_COLUMN_PROTEIN = "protein";
    public static final String ENTRIES_COLUMN_CHOLESTEROL = "cholesterol";
    public static final String ENTRIES_COLUMN_SUGAR = "sugar";
    public static final String ENTRIES_COLUMN_CARBS = "carbs";
    public static final String ENTRIES_COLUMN_SODIUM = "sodium";
    public static final String ENTRIES_COLUMN_FIBER = "fiber";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ENTRIES_TABLE_NAME + " (" +
                    ENTRIES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    ENTRIES_COLUMN_NAME + " TEXT," +
                    ENTRIES_COLUMN_DATE + " TEXT," +
                    ENTRIES_COLUMN_SERVINGS + " FLOAT," +
                    ENTRIES_COLUMN_CALORIE + " FLOAT," +
                    ENTRIES_COLUMN_FAT + " FLOAT," +
                    ENTRIES_COLUMN_PROTEIN + " FLOAT," +
                    ENTRIES_COLUMN_CHOLESTEROL + " FLOAT," +
                    ENTRIES_COLUMN_SUGAR + " FLOAT," +
                    ENTRIES_COLUMN_CARBS + " FLOAT," +
                    ENTRIES_COLUMN_SODIUM + " FLOAT," +
                    ENTRIES_COLUMN_FIBER + " FLOAT)";

    public FoodDiaryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ENTRIES_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertEntry(FoodDiaryEntry entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", entry.getName());
        contentValues.put("date", datetimeToString(entry.getDate()));
        contentValues.put("servings", entry.getServings());
        contentValues.put("calorie", entry.getCalorie());
        contentValues.put("fat", entry.getFat());
        contentValues.put("protein", entry.getProtein());
        contentValues.put("cholesterol", entry.getCholesterol());
        contentValues.put("sugar", entry.getSugar());
        contentValues.put("carbs", entry.getCarbs());
        contentValues.put("sodium", entry.getSodium());
        contentValues.put("fiber", entry.getFiber());

        db.insert(ENTRIES_TABLE_NAME, null, contentValues);

        return true;

    }

    public Cursor getEntriesByDate(DateTime dt) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM entries where date=\"" + datetimeToString(dt) + "\"", null);
        return res;
    }

    public FoodDiaryDay getFoodDiaryDay(DateTime dt) {
        Cursor entries = getEntriesByDate(dt);

        FoodDiaryDay toReturn = new FoodDiaryDay(dt);

        while(entries.moveToNext()) {
            FoodDiaryEntry entry = new FoodDiaryEntry.Builder()
                    .setName(entries.getString(entries.getColumnIndex(ENTRIES_COLUMN_NAME)))
                    //TODO: format properly
                    .setDate(DateTime.parse(
                            entries.getString(entries.getColumnIndex(ENTRIES_COLUMN_DATE)),
                            DATE_TIME_FORMATTER))
                    .setServings(entries.getFloat(entries.getColumnIndex(ENTRIES_COLUMN_SERVINGS)))
                    .setCalorie(entries.getFloat(entries.getColumnIndex(ENTRIES_COLUMN_CALORIE)))
                    .setFat(entries.getFloat(entries.getColumnIndex(ENTRIES_COLUMN_FAT)))
                    .setProtein(entries.getFloat(entries.getColumnIndex(ENTRIES_COLUMN_PROTEIN)))
                    .setCholesterol(entries.getFloat(entries.getColumnIndex(ENTRIES_COLUMN_CHOLESTEROL)))
                    .setSugar(entries.getFloat((entries.getColumnIndex(ENTRIES_COLUMN_SUGAR))))
                    .setCarbs(entries.getFloat(entries.getColumnIndex(ENTRIES_COLUMN_CARBS)))
                    .setSodium(entries.getFloat(entries.getColumnIndex(ENTRIES_COLUMN_SODIUM)))
                    .setFiber(entries.getFloat(entries.getColumnIndex(ENTRIES_COLUMN_FIBER)))
                    .build();
            toReturn.add(entry);
        }

        return toReturn;

    }


    private String datetimeToString(DateTime dt) {
        return DATE_TIME_FORMATTER.print(dt);
    }


}
