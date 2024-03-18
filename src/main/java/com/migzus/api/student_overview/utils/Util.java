package com.migzus.api.student_overview.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
    public static String getCurrentDate() {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDateTime.now());
    }
}
