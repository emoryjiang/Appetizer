package cs125.winter2017.uci.appetizer.food_diary;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.Arrays;
import java.util.Comparator;
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
	public FoodDiaryDay getTodaysEntries(){
        return getDay(new DateTime());
    }

    public void addEntry(FoodDiaryEntry entry){
        getDay(entry.getDate()).add(entry);
    }

    public void addDay(FoodDiaryDay day){
        entries.put(day.getDate(), day);
    }

    public void removeEntry(FoodDiaryEntry entry){
        FoodDiaryDay entryDay = getDay(entry.getDate());
        entryDay.remove(entry);
    }

    @NonNull
    public FoodDiaryDay getDay(DateTime date){
        FoodDiaryDay day = entries.get(date);
        if (day == null){
            day = loadDayFromDisk(date);
            if (day == null)
                day = new FoodDiaryDay(date);
            entries.put(date, day);
        }

        return day;
    }

    // TODO: load the thing from disk
    @Nullable
    private FoodDiaryDay loadDayFromDisk(DateTime date){
        return null;
    }

}
