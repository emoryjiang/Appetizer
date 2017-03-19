package cs125.winter2017.uci.appetizer.food_diary;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeMap;

public class FoodDiary {

    private static class Loader {
        private static final FoodDiary INSTANCE = new FoodDiary();
    }

    public static FoodDiary getInstance(){
        return Loader.INSTANCE;
    }

    public static Comparator<DateTime> SAME_DAY_COMPARATOR = new Comparator<DateTime>() {
        @Override
        public int compare(DateTime first, DateTime second) {
            return Days.daysBetween(first.toLocalDate(), second.toLocalDate()).getDays();
        }
    };

    public TreeMap<DateTime, FoodDiaryDay> entries;

    private FoodDiary(){
        entries = new TreeMap<>(SAME_DAY_COMPARATOR);
    }

    public int numEntries(){
        int entryCount = 0;
        for (FoodDiaryDay day : entries.values())
            entryCount += day.size();
        return entryCount;
    }

	@NonNull
	public FoodDiaryDay getTodaysEntries(FoodDiaryDBHelper dbHelper){
        return getDay(dbHelper, new DateTime());
    }

    public void addEntry(FoodDiaryDBHelper dbHelper, FoodDiaryEntry entry){
        getDay(dbHelper, entry.getDate()).add(entry);
        dbHelper.insertEntry(entry);
    }

    public void addDay(FoodDiaryDay day){
        entries.put(day.getDate(), day);
    }

//    public void removeEntry(FoodDiaryEntry entry){
//        FoodDiaryDay entryDay = getDay(dbHelper, entry.getDate());
//        entryDay.remove(entry);
//    }

    @NonNull
    public FoodDiaryDay getDay(FoodDiaryDBHelper dbHelper, DateTime date){
        FoodDiaryDay day = entries.get(date);
        if (day == null){
            day = loadDayFromDisk(dbHelper, date);
            if (day == null)
                day = new FoodDiaryDay(date);
            entries.put(date, day);
        }

        return day;
    }

    public List<FoodDiaryDay> getDaysBeforeNow(FoodDiaryDBHelper dbHelper, int days){
        List<FoodDiaryDay> foodDiaryDays = new ArrayList<>();
        DateTime today = new DateTime();

        for (int i = 0; i < days; ++i){
            DateTime toFetch = today.minusDays(i);
            foodDiaryDays.add(getDay(dbHelper, toFetch));
        }

        return foodDiaryDays;
    }

    // TODO: load the thing from disk
    @Nullable
    private FoodDiaryDay loadDayFromDisk(FoodDiaryDBHelper dbHelper, DateTime date){
        return dbHelper.getFoodDiaryDay(date);
    }

}
