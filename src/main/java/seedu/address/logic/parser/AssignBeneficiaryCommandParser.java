package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntaxProject.*;
import static seedu.address.logic.parser.ParserUtilProject.UNSPECIFIED_FIELD;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignBeneficiaryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.project.ProjectTitle;

/**
 * Parses input arguments and creates a new AssignBeneficiaryCommand object
 */
public class AssignBeneficiaryCommandParser implements Parser<AssignBeneficiaryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AssignBeneficiaryCommand
     * and returns an AssignBeneficiaryCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignBeneficiaryCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_INDEX);

        ProjectTitle projectTitle;
        try {
            if (!argMultimap.getPreamble().isEmpty()) {
                projectTitle = ParserUtilProject.parseProjectTitle(argMultimap.getPreamble());
            } else {
                projectTitle = new ProjectTitle(UNSPECIFIED_FIELD);
            }
            Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());

            return new AssignBeneficiaryCommand(projectTitle, index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignBeneficiaryCommand.MESSAGE_USAGE), pe);
        }
    }
}
