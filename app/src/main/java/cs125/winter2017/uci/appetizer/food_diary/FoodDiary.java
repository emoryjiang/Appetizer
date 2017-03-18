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

        DateTime today = new DateTime();
        FoodDiaryDay day1 = new FoodDiaryDay(today);

        day1.addAll(Arrays.asList(
                new FoodDiaryEntry.Builder()
                        .setDate(today.withHourOfDay(9))
                        .setName("Eggs")
                        .setServings(2)
                        .setCalorie(90)
                        .setFat(7)
                        .setCholesterol(184)
                        .setSodium(95)
                        .setCarbs(0.4)
                        .setFiber(0)
                        .setSugar(0.6)
                        .setProtein(6)
                        .build(),
                new FoodDiaryEntry.Builder()
                        .setDate(today.withHourOfDay(2))
                        .setName("Ramen")
                        .setServings(1)
                        .setCalorie(188)
                        .setFat(7)
                        .setCholesterol(0)
                        .setCarbs(27)
                        .setFiber(1)
                        .setSugar(0.7)
                        .setProtein(4.5)
                        .setSodium(875)
                        .build()
        ));

        DateTime yesterday = today.minusDays(1);
        FoodDiaryDay day2 = new FoodDiaryDay(yesterday);

        DateTime evening = yesterday.withHourOfDay(18);
        day2.addAll(Arrays.asList(
                new FoodDiaryEntry.Builder()
                        .setDate(evening)
                        .setName("Hamburger")
                        .setServings(1)
                        .setCalorie(354)
                        .setFat(17)
                        .setCholesterol(56)
                        .setSodium(497)
                        .setCarbs(29)
                        .setFiber(1.1)
                        .setSugar(5)
                        .setProtein(20)
                        .build(),
                new FoodDiaryEntry.Builder()
                        .setDate(evening)
                        .setName("French Fries")
                        .setServings(1)
                        .setFat(17)
                        .setCholesterol(0)
                        .setSodium(246)
                        .setCarbs(48)
                        .setFiber(4.4)
                        .setSugar(0.4)
                        .setProtein(4)
                        .build()
        ));

        addDay(day1);
        addDay(day2);
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
