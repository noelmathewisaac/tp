package seedu.taskify.testutil;

import static seedu.taskify.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.taskify.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.taskify.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.taskify.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.taskify.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.taskify.logic.commands.AddCommand;
import seedu.taskify.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.taskify.model.tag.Tag;
import seedu.taskify.model.task.Task;

/**
 * A utility class for Task.
 */
public class TaskUtil {

    /**
     * Returns an add command string for adding the {@code task}.
     */
    public static String getAddCommand(Task task) {
        return AddCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code task}'s details.
     */
    public static String getTaskDetails(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + task.getName().fullName + " ");
        sb.append(PREFIX_DESCRIPTION + task.getDescription().value + " ");
        sb.append(PREFIX_ADDRESS + task.getAddress().value + " ");
        sb.append(PREFIX_DATE + task.getDate().value + " ");
        task.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditTaskDescriptor}'s details.
     */
    public static String getEditTaskDescriptorDetails(EditTaskDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getDescription().ifPresent(description -> sb.append(PREFIX_DESCRIPTION)
                .append(description.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(PREFIX_DATE).append(date.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
