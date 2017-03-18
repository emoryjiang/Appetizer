package cs125.winter2017.uci.appetizer.daily_targets;

import android.content.Context;
import android.content.SharedPreferences;

public final class DailyTargets {
    private DailyTargets(){}

    private static final String PREF_FILE = "cs125.winter2017.uci.appetizer.DAILY_TARGETS";

    private static SharedPreferences getDailyTargetsPrefs(Context context){
        return context.getSharedPreferences(PREF_FILE, 0);
    }

    public static double getCalorie(Context context) {
        return Double.longBitsToDouble(getDailyTargetsPrefs(context).getLong("calorie", 0));
    }

    public static void setCalorie(Context context, double calorie) {
        getDailyTargetsPrefs(context).edit()
                .putLong("calorie", Double.doubleToRawLongBits(calorie)).apply();
    }

    public static double getFat(Context context) {
        return Double.longBitsToDouble(getDailyTargetsPrefs(context).getLong("fat", 0));
    }

    public static void setFat(Context context, double fat) {
        getDailyTargetsPrefs(context).edit().putLong("fat", Double.doubleToRawLongBits(fat))
            .apply();
    }

    public static double getProtein(Context context) {
        return Double.longBitsToDouble(getDailyTargetsPrefs(context).getLong("protein", 0));
    }

    public static void setProtein(Context context, double protein) {
        getDailyTargetsPrefs(context).edit().putLong("protein", Double.doubleToRawLongBits(protein))
            .apply();
    }

    public static double getCholesterol(Context context) {
        return Double.longBitsToDouble(getDailyTargetsPrefs(context).getLong("cholesterol", 0));
    }

    public static void setCholesterol(Context context, double cholesterol) {
        getDailyTargetsPrefs(context).edit().putLong("cholesterol",
                Double.doubleToRawLongBits(cholesterol)).apply();
    }

    public static double getSugar(Context context) {
        return Double.longBitsToDouble(getDailyTargetsPrefs(context).getLong("sugar", 0));
    }

    public static void setSugar(Context context, double sugar) {
        getDailyTargetsPrefs(context).edit().putLong("sugar", Double.doubleToRawLongBits(sugar))
                .apply();
    }

    public static double getCarbs(Context context) {
        return Double.longBitsToDouble(getDailyTargetsPrefs(context).getLong("carbohydrates", 0));
    }

    public static void setCarbs(Context context, double carbs) {
        getDailyTargetsPrefs(context).edit().putLong("carbohydrates",
                Double.doubleToRawLongBits(carbs)).apply();
    }

    public static double getSodium(Context context) {
        return Double.longBitsToDouble(getDailyTargetsPrefs(context).getLong("sodium", 0));
    }

    public static void setSodium(Context context, double sodium) {
        getDailyTargetsPrefs(context).edit().putLong("sodium", Double.doubleToRawLongBits(sodium))
                .apply();
    }

    public static double getFiber(Context context) {
        return Double.longBitsToDouble(getDailyTargetsPrefs(context).getLong("fiber", 0));
    }

    public static void setFiber(Context context, double fiber) {
        getDailyTargetsPrefs(context).edit().putLong("fiber", Double.doubleToRawLongBits(fiber))
                .apply();
    }
}
