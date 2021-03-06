package seedu.taskify.logic.commands.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskify.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskify.logic.commands.util.DeleteUtil.MESSAGE_DELETE_BY_STATUS_USAGE;
import static seedu.taskify.logic.commands.util.DeleteUtil.MESSAGE_INVALID_INDEX_RANGE;
import static seedu.taskify.logic.commands.util.DeleteUtil.extractStringArgumentsIntoIndexes;
import static seedu.taskify.logic.commands.util.DeleteUtil.hasMultipleValidIndex;
import static seedu.taskify.logic.commands.util.DeleteUtil.isDeletingTasksByStatus;
import static seedu.taskify.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import seedu.taskify.logic.commands.DeleteCommand;
import seedu.taskify.logic.parser.exceptions.ParseException;

public class DeleteMultipleCommandUtilTest {

    @ParameterizedTest
    @ValueSource(strings = {"1 2", "   4  10 12   20", "5-8"})
    public void hasMultipleValidIndex_validArgs_returnsTrue(String input) throws ParseException {
        assertTrue(hasMultipleValidIndex(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {" 3 kappa", " 1.0 2 3.0", " 1, 2, 3", "3-4.0"})
    public void hasMultipleValidIndex_invalidArgs_throwsParseException(String input) {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), (
                ) -> hasMultipleValidIndex(input));
    }

    @Test
    public void hasMultipleValidIndex_oneIndexAndValid_returnsFalse() throws ParseException {
        assertFalse(hasMultipleValidIndex(" 2"));
    }

    @Test
    public void hasMultipleValidIndex_rangeWithSameEnds_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX_RANGE, () -> hasMultipleValidIndex("3-3"));
    }


    @ParameterizedTest
    @ValueSource(strings = {"uncompleted -all", "completed -all ", " expired -all "})
    public void isDeletingTasksByStatus_validArgs_returnsTrue(String input) throws ParseException {
        assertTrue(isDeletingTasksByStatus(input));
    }



    @ParameterizedTest
    @ValueSource(strings = {" 1 2 3", " 1-3 "})
    public void isDeletingTasksByStatus_notDeletingByStatus_returnsFalse(String input) throws ParseException {
        assertFalse(isDeletingTasksByStatus(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"uncompleted all", "Expired -all ", "Completed -all", " completed ---all"})
    public void isDeletingTasksByStatus_triesToDeleteByStatusButInvalidArgs_throwsParseException(String input) {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_DELETE_BY_STATUS_USAGE), () -> isDeletingTasksByStatus(input));
    }




    @Test
    public void extractStringArguments_validIndexRange_returnsStringArray() throws ParseException {
        assertArrayEquals(new String[]{"1", "2"}, extractStringArgumentsIntoIndexes(" 1-2"));
        assertArrayEquals(new String[]{"100", "101", "102", "103", "104", "105"},
                extractStringArgumentsIntoIndexes("100-105"));
        assertArrayEquals(new String[]{"9", "10", "11", "12"}, extractStringArgumentsIntoIndexes("  9-12   "));
        assertArrayEquals(new String[]{"4", "10", "6"}, extractStringArgumentsIntoIndexes(" 4 10 6"));
    }


    @ParameterizedTest
    @ValueSource(strings = {"10-10", "  40-39  "})
    public void extractStringArguments_invalidIndexRange_throwsParseException(String input) {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX_RANGE, () -> extractStringArgumentsIntoIndexes(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"  1-", "10-wtf ", "9to10", "000100-   000101"})
    public void extractStringArguments_argsIsInvalidFormatForIndexRange_throwsParseException(String input) {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE), () -> extractStringArgumentsIntoIndexes(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0-1", "  01-09  ", "-5-2", "1.0-3", "3-5.0"})
    public void extractStringArguments_invalidIndexesInRange_throwsParseException(String input) {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE), () -> extractStringArgumentsIntoIndexes(input));
    }

    @Test
    public void extractStringArguments_extraInputAfterValidInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE), () -> extractStringArgumentsIntoIndexes("1-3 2-4"));

    }
}
