//@@author quinnzzzzz
package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.project.Project;

/**
 * An UI component that displays information of a {@code Project}.
 */
public class ProjectCard extends UiPart<Region> {

    private static final String FXML = "ProjectListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Project project;

    @FXML
    private HBox cardPane;
    @FXML
    private Label projectTitle;
    @FXML
    private Label id;
    @FXML
    private Label projectDate;
    @FXML
    private Label beneficiary;
    @FXML
    private Label volunteer;

    public ProjectCard(Project project, int displayedIndex) {
        super(FXML);
        this.project = project;
        id.setText(displayedIndex + ". ");
        projectTitle.setText(project.getProjectTitle().fullTitle);
        if (project.isComplete()) {
            projectTitle.setStyle("-fx-font-family: Segoe UI Semibold; -fx-font-size: 13pt; -fx-text-fill: #FF0000;");
        }
        projectDate.setText("Project Date: " + project.getProjectDate().fullDate);
        beneficiary.setText("Beneficiary Assigned: " + project.getBeneficiaryAssigned().toString());
        volunteer.setText("Number of Volunteers Assigned: " + project.getVolunteerCount());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProjectCard)) {
            return false;
        }

        // state check
        ProjectCard card = (ProjectCard) other;
        return id.getText().equals(card.id.getText())
            && project.equals(card.project);
    }
}
