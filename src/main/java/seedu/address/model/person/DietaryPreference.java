package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Dietary preference of volunteer
 */
public class DietaryPreference {
    //field
    public static final String MESSAGE_CONSTRAINTS =
        "Dietary Preference should not contain numbers";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public final String restriction;

    /**
     * Constructs a {@code DietaryPreference}.
     */
    public DietaryPreference(String dietaryPreference) {
        requireNonNull(dietaryPreference);
        checkArgument(isValidDietary_preference(dietaryPreference), MESSAGE_CONSTRAINTS);
        restriction = dietaryPreference;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidDietary_preference(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return restriction;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DietaryPreference // instanceof handles nulls
            && restriction.equals(((DietaryPreference) other).restriction)); // state check
    }

    @Override
    public int hashCode() {
        return restriction.hashCode();
    }
}
