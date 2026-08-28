package alice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    private static final DateTimeFormatter INPUT_FORMAT =
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    public static String parseTodoDescription(String input) throws AliceException {
        String description = input.length() > 4 ? input.substring(5).trim() : "";
        if (description.isEmpty()) {
            throw new AliceException("The description of a todo cannot be empty.");
        }
        return description;
    }

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

    public static int parseIndex(String input, int prefixLength) throws NumberFormatException {
        return Integer.parseInt(input.substring(prefixLength)) - 1;
    }

    /**
     * Extracts and validates the keyword from a "find" command.
     *
     * @param input the full raw user input, e.g. "find book".
     * @return the extracted, trimmed keyword.
     * @throws AliceException if the keyword is empty.
     */
    public static String parseFindKeyword(String input) throws AliceException {
        String keyword = input.length() > 4 ? input.substring(5).trim() : "";
        if (keyword.isEmpty()) {
            throw new AliceException("The keyword to find cannot be empty.");
        }
        return keyword;
    }
}