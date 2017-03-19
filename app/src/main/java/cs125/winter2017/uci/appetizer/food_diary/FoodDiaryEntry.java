package cs125.winter2017.uci.appetizer.food_diary;

import android.support.annotation.NonNull;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.Seconds;

public class FoodDiaryEntry implements Comparable<FoodDiaryEntry>, NutrientFactHolder {

    private final @NonNull DateTime date;
	private @NonNull String Name;

    private int Servings;
	private double Calorie;
	private double Fat;
	private double Protein;
	private double Cholesterol;
	private double Sugar;
	private double Carbs;
	private double Sodium;
	private double Fiber;

    private FoodDiaryEntry(Builder builder){
        date = builder.Date;
        Name = builder.Name;

        setName(builder.Name);
        setServings(builder.Servings);
        setCalorie(builder.Calorie);
        setFat(builder.Fat);
        setProtein(builder.Protein);
        setCholesterol(builder.Cholesterol);
        setSugar(builder.Sugar);
        setCarbs(builder.Carbs);
        setSodium(builder.Sodium);
        setFiber(builder.Fiber);
    }

    @Override
    public int compareTo(@NonNull FoodDiaryEntry other) {
        int secondDiff = Seconds.secondsBetween(this.getDate(), other.getDate()).getSeconds();
        return secondDiff != 0 ? secondDiff : this.getName().compareTo(other.getName());
    }

    public static class Builder {
        private @NonNull DateTime Date;
        private @NonNull String Name;

        private int Servings;
        private double Calorie;
        private double Fat;
        private double Protein;
        private double Cholesterol;
        private double Sugar;
        private double Carbs;
        private double Sodium;
        private double Fiber;

        public Builder(){
            Date = new DateTime();
            Name = "";

            Servings = 1;
            Calorie = 0;
            Fat = 0;
            Protein = 0;
            Cholesterol = 0;
            Sugar = 0;
            Carbs = 0;
            Sodium = 0;
            Fiber = 0;
        }

        public Builder setDate(@NonNull DateTime date){
            Date = date;
            return this;
        }

        public Builder setName(@NonNull String name){
            Name = name;
            return this;
        }

        public Builder setServings(int servings) {
            Servings = servings;
            return this;
        }

        public Builder setCalorie(double calorie) {
            Calorie = calorie;
            return this;
        }

        public Builder setFat(double fat) {
            Fat = fat;
            return this;
        }

        public Builder setProtein(double protein) {
            Protein = protein;
            return this;
        }

        public Builder setCholesterol(double cholesterol) {
            Cholesterol = cholesterol;
            return this;
        }

        public Builder setSugar(double sugar) {
            Sugar = sugar;
            return this;
        }

        public Builder setCarbs(double carbs) {
            Carbs = carbs;
            return this;
        }

        public Builder setSodium(double sodium) {
            Sodium = sodium;
            return this;
        }

        public Builder setFiber(double fiber) {
            Fiber = fiber;
            return this;
        }

        public FoodDiaryEntry build(){
            return new FoodDiaryEntry(this);
        }
    }
	
	public String returnDate(){
		return Integer.toString(date.get(DateTimeFieldType.year())) + "-" +
                Integer.toString(date.get(DateTimeFieldType.monthOfYear())) + "-" +
                Integer.toString(date.get(DateTimeFieldType.dayOfMonth()));
	}

    @NonNull
    public DateTime getDate() {
        return date;
    }

    @NonNull
    public String getName() {
        return Name;
    }

    public void setName(@NonNull String name) {
        Name = name;
    }

    public int getServings() {
        return Servings;
    }

    public void setServings(int servings) {
        Servings = servings;
    }

    @Override
    public double getCalorie() {
        return Calorie;
    }

    public void setCalorie(double calorie) {
        Calorie = calorie;
    }

    @Override
    public double getFat() {
        return Fat;
    }

    public void setFat(double fat) {
        Fat = fat;
    }

    @Override
    public double getProtein() {
        return Protein;
    }

    public void setProtein(double protein) {
        Protein = protein;
    }

    @Override
    public double getCholesterol() {
        return Cholesterol;
    }

    public void setCholesterol(double cholesterol) {
        Cholesterol = cholesterol;
    }

    @Override
    public double getSugar() {
        return Sugar;
    }

    public void setSugar(double sugar) {
        Sugar = sugar;
    }

    @Override
    public double getCarbs() {
        return Carbs;
    }

    public void setCarbs(double carbs) {
        Carbs = carbs;
    }

    @Override
    public double getSodium() {
        return Sodium;
    }

    public void setSodium(double sodium) {
        Sodium = sodium;
    }

    @Override
    public double getFiber() {
        return Fiber;
    }

    public void setFiber(double fiber) {
        Fiber = fiber;
    }


}
