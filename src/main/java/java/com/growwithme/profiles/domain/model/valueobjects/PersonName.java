package java.com.growwithme.profiles.domain.model.valueobjects;

public record PersonName(String firstName, String lastName) {
    public PersonName {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name must not be null or blank");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name must not be null or blank");
        }
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }
}
