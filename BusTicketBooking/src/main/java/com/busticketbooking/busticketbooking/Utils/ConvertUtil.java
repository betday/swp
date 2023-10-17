package com.busticketbooking.busticketbooking.Utils;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class ConvertUtil {
    public static int convert(String str) throws NumberFormatException {
        return Integer.parseInt(str);
    }

    public static String formatTime(Time sqlTime) {
        // Convert java.sql.Time to LocalTime
        LocalTime localTime = sqlTime.toLocalTime();

        // Format LocalTime to display only hours and minutes
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return localTime.format(formatter);
    }

    public static String formatDate(Date sqlDate) {
        // Convert java.sql.Date to LocalDate
        LocalDate localDate = sqlDate.toLocalDate();

        // Format LocalDate to display in 'DD-MM-YYYY'
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return localDate.format(formatter);
    }

    public static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
