package com.project.utils;

import java.time.LocalDate;
import java.time.YearMonth;

public class DateUtils {

  public static LocalDate startOfMonth(int year, int month) {
    return LocalDate.of(year, month, 1);
  }

  public static LocalDate endOfMonth(int year, int month) {
    return YearMonth.of(year, month).atEndOfMonth();
  }


  private DateUtils() {}
}
