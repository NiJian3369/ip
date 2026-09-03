package alice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Handles parsing of raw user input strings into structured data or task
 * objects, including validation and date/time parsing.
 */
public class Parser {
    private static final DateTimeFormatter INPUT_FORMAT =
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    /**
     * Extracts and validates the description from a "todo" command.
     *
     * @param input the full raw user input, e.g. "todo read book".
     * @return the extracted, trimmed description.
     * @throws AliceException if the description is empty.
     */
    public static String parseTodoDescription(String input) throws AliceException {
        String description = input.length() > 4 ? input.substring(5).trim() : "";
        if (description.isEmpty()) {
            throw new AliceException("The description of a todo cannot be empty.");
        }
        return description;
    }

    /**
     * Parses a "deadline" command into a Deadlines task, extracting the
     * description and the /by date/time.
     *
     * @param input the full raw user input, e.g. "deadline return book /by 2/12/2019 1800".
     * @return a new Deadlines task built from the parsed input.
     * @throws AliceException if the input is missing required parts or the
     *         date/time cannot be parsed.
     */
    public static Deadlines parseDeadline(String input) throws AliceException {
        if (!input.contains(" /by ")) {
            throw new AliceException("A deadline needs a description and a /by date.");
        }
        String rest = input.substring(9);
        String[] parts = rest.split(" /by ");
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new AliceException("A deadline needs both a description and a /by date.");
        }
        LocalDateTime by;
        try {
            by = LocalDateTime.parse(parts[1].trim(), INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new AliceException("Please use the date format d/M/yyyy HHmm, e.g. 2/12/2019 1800.");
        }
        return new Deadlines(parts[0].trim(), by);
    }

    /**
     * Parses an "event" command into an Events task, extracting the
     * description and the /from and /to date/times.
     *
     * @param input the full raw user input, e.g.
     *        "event project meeting /from 2/12/2019 1400 /to 2/12/2019 1600".
     * @return a new Events task built from the parsed input.
     * @throws AliceException if the input is missing required parts or the
     *         date/times cannot be parsed.
     */
    public static Events parseEvent(String input) throws AliceException {
        if (!input.contains(" /from ") || !input.contains(" /to ")) {
            throw new AliceException("An event needs a description, /from, and /to.");
        }
        String rest = input.substring(6);
        String[] fromSplit = rest.split(" /from ");
        String description = fromSplit[0].trim();
        String[] toSplit = fromSplit[1].split(" /to ");
        LocalDateTime from;
        LocalDateTime to;
        try {
            from = LocalDateTime.parse(toSplit[0].trim(), INPUT_FORMAT);
            to = LocalDateTime.parse(toSplit[1].trim(), INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new AliceException("Please use the date format d/M/yyyy HHmm, e.g. 2/12/2019 1800.");
        }
        return new Events(description, from, to);
    }

    /**
     * Parses a zero-based task index from a command string, given the
     * length of the command's prefix (e.g. "mark " has prefix length 5).
     *
     * @param input the full raw user input, e.g. "mark 3".
     * @param prefixLength the number of characters before the index number.
     * @return the zero-based index.
     * @throws NumberFormatException if the remaining text is not a valid number.
     */
    public static int parseIndex(String input, int prefixLength) throws NumberFormatException {
        return Integer.parseInt(input.substring(prefixLength)) - 1;
    }

    /**
     * Extracts and validates one or more keywords from a "find" command.
     * Multiple keywords are separated by whitespace, e.g.
     * "find book magazine" searches for tasks matching "book" or
     * "magazine".
     *
     * @param input the full raw user input, e.g. "find book" or
     *        "find book magazine".
     * @return the extracted keywords.
     * @throws AliceException if no keyword is given.
     */
    public static String[] parseFindKeywords(String input) throws AliceException {
        String keywordText = input.length() > 4 ? input.substring(5).trim() : "";
        if (keywordText.isEmpty()) {
            throw new AliceException("The keyword to find cannot be empty.");
        }
        return keywordText.split("\\s+");
    }
}