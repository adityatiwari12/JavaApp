package com.example.registrationapp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateUtils {
    private static final SimpleDateFormat DB_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static final SimpleDateFormat DISPLAY_FORMAT = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

    public static String getTodayDb() {
        return DB_FORMAT.format(Calendar.getInstance().getTime());
    }

    public static String formatForDisplay(String dbDate) {
        try {
            return DISPLAY_FORMAT.format(DB_FORMAT.parse(dbDate));
        } catch (Exception e) {
            return dbDate;
        }
    }

    public static int getCurrentDayOfWeek() {
        // Returns 1=Monday...7=Sunday (ISO)
        int cal = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        // Calendar: 1=Sun,2=Mon...7=Sat → convert to 1=Mon...7=Sun
        return cal == Calendar.SUNDAY ? 7 : cal - 1;
    }

    public static String getDayName(int day) {
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        return (day >= 1 && day <= 7) ? days[day - 1] : "";
    }
}
