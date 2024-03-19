package com.migzus.api.student_overview.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
    public static String getCurrentDate() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
    }
}
