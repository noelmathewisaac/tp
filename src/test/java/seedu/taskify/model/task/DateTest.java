package seedu.taskify.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskify.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;



class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date((String) null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void overloadedConstructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date((LocalDateTime) null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"20-04-13 10:30", "2020-4-13 10:30", "2020-04-32 10:30", "2020-04-13 25:30",
            "2020-04-13 9:30", "2020-04-13 9.30", "2020-04-13 22:30:30", "2020-04-13 09:70", "2020-02-31 10:30"})
    void isValidDate_invalidDate_failure(String input) {
        assertFalse(Date.isValidDate(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"2020-04-13 00:30", "1999-10-31 23:59", "1950-12-05 00:00"})
    void isValidDate_validDate_success(String input) {
        assertTrue(Date.isValidDate(input));
    }

    @Test
    void testEquals() {
        assertEquals(new Date("1999-04-13 23:59"), new Date("1999-04-13 23:59"));
        assertEquals(new Date("2021-01-09 09:30"), new Date("2021-01-09 09:30"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"2021-04-13 10:30", "2020-04-13 10:30", "2020-04-32 10:30", "2020-04-13 25:30",
            "2020-04-13 29:30", "2020-04-13 19:30", "2020-04-13 22:30", "2020-04-13 09:70", "2020-02-31 10:30"})
    public void isCorrectInputFormat_correctFormat_success(String input) {
        assertTrue(Date.isCorrectInputFormat(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0001-02-29 10:30", "2021-02-29 10:30", "9999-02-29 23:59"})
    public void isInvalidLeapYearDate_invalidLeapYearDate_success(String input) {
        assertTrue(Date.isInvalidLeapYearDate(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0000-02-29 10:30", "2020-02-29 10:30", "2024-02-29 23:59"})
    public void isInvalidLeapYearDate_validLeapYearDate_fail(String input) {
        assertFalse(Date.isInvalidLeapYearDate(input));
    }

    @Test
    public void endOfToday_returnsEndOfTodayDate_success() {
        String todayDateString = LocalDate.now().toString();
        String todayDateTimeString = todayDateString + " " + "23:59";
        Date expectedDate = new Date(todayDateTimeString);
        assertEquals(Date.endOfToday(), expectedDate);
    }
}
