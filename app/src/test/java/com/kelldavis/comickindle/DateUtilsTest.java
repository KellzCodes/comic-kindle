package com.kelldavis.comickindle;

import com.kelldavis.comickindle.utils.DateTextUtils;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.Test;

public class DateUtilsTest {

  @Test
  public void testGetDateString_returnsCorrectString() {

    // Arrange
    String TEST_DATE_STRING = "2018-12-30";
    Calendar testDate = new GregorianCalendar(2018, Calendar.DECEMBER, 30);

    // Assert
    assertEquals("getDateString method returned incorrect string!",
        TEST_DATE_STRING,
        DateTextUtils.getDateString(testDate.getTime()));
  }
}