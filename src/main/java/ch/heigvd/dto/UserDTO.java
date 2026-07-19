package ch.heigvd.dto;

/**
 * Data Transfer Object representing a User, showing only displayable value.
 * @param id the id of the user.
 * @param name the name of the user.
 * @param email the email of the user.
 * @param isValid whether the user has been validated or not
 */
public record UserDTO(Long id, String name, String email, boolean isValid) {
}
