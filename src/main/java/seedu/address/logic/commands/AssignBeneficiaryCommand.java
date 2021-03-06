//@@author quinnzzzzz
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntaxBeneficiary.ASSIGNED_PROJECT_TITLE;
import static seedu.address.logic.parser.CliSyntaxBeneficiary.PREFIX_INDEX;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.beneficiary.Beneficiary;
import seedu.address.model.beneficiary.Name;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectTitle;

/**
 * Assigns a beneficiary to a project.
 */
public class AssignBeneficiaryCommand extends Command {

    public static final String COMMAND_WORD = "assignB";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns a beneficiary to a project, "
            + "only 1 beneficiary can be assigned to each project.\n"
            + "Parameters: "
            + ASSIGNED_PROJECT_TITLE
            + "[PROJECT_TITLE] "
            + PREFIX_INDEX
            + "[INDEX]...\n"
            + "Example: " + COMMAND_WORD + " "
            + "Project Sunshine"
            + "i/1 ";

    public static final String MESSAGE_PARAMETERS = ASSIGNED_PROJECT_TITLE + "[PROJECT_TITLE] "
        + PREFIX_INDEX + "INDEX ";

    public static final String MESSAGE_SUCCESS = "Beneficiary successfully assigned to project.";
    public static final String MESSAGE_DUPLICATE_BENEFICIARY =
        "There is already the same beneficiary that exists in the project.";

    private final ProjectTitle targetProject;
    private final Index targetBeneficiaryIndex;
    private Project editedProject;
    private Project projectToAssign;

    /**
     * Creates an AssignBeneficiaryCommand to assign beneficiary to {@code Project}
     */
    public AssignBeneficiaryCommand(ProjectTitle targetProject, Index targetBeneficiary) {
        requireNonNull(targetProject);
        requireNonNull(targetBeneficiary);

        this.targetProject = targetProject;
        this.targetBeneficiaryIndex = targetBeneficiary;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Beneficiary> lastShownList = model.getFilteredBeneficiaryList();

        if (targetBeneficiaryIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BENEFICIARY_DISPLAYED_INDEX);
        }
        Predicate<Project> equalProjectTitle = x -> x.getProjectTitle().equals(targetProject);
        if (model.getFilteredProjectList().filtered(equalProjectTitle).size() == 0) {
            throw new CommandException("Project does not exist.");
        } else {
            projectToAssign = model.getFilteredProjectList().filtered(equalProjectTitle).get(0);

            //Update previous beneficiary on the change
            updatePreBeneficiary(model);

            //match project to beneficiary
            Beneficiary beneficiary = updateBeneficiary(model);

            //match beneficiary to project
            Name beneficiaryAssigned = beneficiary.getName();
            this.editedProject = new ProjectBuilder(this.projectToAssign).withBeneficiary(beneficiaryAssigned).build();
            model.setProject(projectToAssign, editedProject);

            model.commitAddressBook();
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        }
    }

    /**
     *
     * @param model
     * @return
     */
    private Beneficiary updateBeneficiary(Model model) {
        Beneficiary beneficiary = model.getFilteredBeneficiaryList().get(targetBeneficiaryIndex.getZeroBased());
        Beneficiary updatedBeneficiary = new Beneficiary(beneficiary);
        if (!updatedBeneficiary.hasProjectTitle(targetProject)) {
            updatedBeneficiary.addAttachedProject(targetProject);
        }
        model.setBeneficiary(beneficiary, updatedBeneficiary);
        return updatedBeneficiary;
    }

    /**
     *
     * @param model
     */
    private void updatePreBeneficiary(Model model) {
        if (isValidPreAssignedBeneficiary(model)) {
            Beneficiary oldBeneficiary = model.getFilteredBeneficiaryList()
                .filtered(x -> x.getName().equals(projectToAssign.getBeneficiaryAssigned())).get(0);
            Beneficiary newBeneficiary = new Beneficiary(oldBeneficiary);
            newBeneficiary.deleteAttachedProject(projectToAssign.getProjectTitle());
            model.setBeneficiary(oldBeneficiary, newBeneficiary);
        }
    }

    private boolean isValidPreAssignedBeneficiary(Model model) {
        return projectToAssign.getBeneficiaryAssigned().toString() != "nil"
            && model.getFilteredBeneficiaryList().filtered(
                x -> x.getName().equals(projectToAssign.getBeneficiaryAssigned())).size() != 0;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof AssignBeneficiaryCommand // instanceof handles nulls
            && this.targetProject.equals(((AssignBeneficiaryCommand) other).targetProject)); // state check;
    }
}

