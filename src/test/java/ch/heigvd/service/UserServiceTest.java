package ch.heigvd.service;

import ch.heigvd.dto.UserDTO;
import ch.heigvd.entity.User;
import ch.heigvd.utils.TestHelpers;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class UserServiceTest {

    @Inject
    EntityManager em;

    @Inject
    UserService userService;

    @Test
    public void testSearchUserByIdWrongId() {
        assertThrows(NoResultException.class, () -> userService.searchUserById(-1L));
    }

    @Test
    @TestTransaction
    public void testSearchUserById() {

        User user = TestHelpers.createTestUser(em);
        UserDTO result = userService.searchUserById(user.getId());

        assertEquals(user.getId(), result.id());
        assertEquals(user.getName(), result.name());
        assertEquals(user.getEmail(), result.email());

    }


    @Test
    @TestTransaction
    public void testAddUser() {

        UserDTO result = userService.addUser("Test", "test@test.ch", "password");
        UserDTO userDTO = new UserDTO(result.id(), "Test", "test@test.ch");

        assertEquals(userDTO, result);

        //Should be persisted
        User user = em.find(User.class, result.id());
        assertNotNull(user);
        assertEquals(user.getName(), result.name());
        assertEquals(user.getEmail(), result.email());
    }

    @Test
    @TestTransaction
    public void testAddUserDefaultValues() {
        UserDTO result = userService.addUser("Test", "test@test.ch", "password");

        User user = em.find(User.class, result.id());

        assertFalse(user.getValid());
        assertFalse(user.getAdmin());
        assertEquals("", user.getRole());
    }

    @Test
    @TestTransaction
    public void testAddUserHashPassword() {
        UserDTO result = userService.addUser("Test", "test@test.ch", "password");

        User user = em.find(User.class, result.id());

        assertNotEquals("password", user.getPassword());
        assertTrue(user.getPassword().startsWith("$2"));
    }

    @Test
    public void testModifyUserWrongId() {
        assertThrows(NotFoundException.class, () -> userService.modifyUserById(-1L, "Test", "test@test.ch", "password"));
    }

    @Test
    @TestTransaction
    public void testModifyUser() {

        UserDTO firstUserDTO = userService.addUser("Test", "test@test.ch", "password");

        UserDTO result = userService.modifyUserById(
                firstUserDTO.id(),
                "newName",
                "test@test.ch",
                "newpassword"
        );

        UserDTO secondUserDTO = new UserDTO(result.id(), "newName", "test@test.ch");

        assertEquals(secondUserDTO, result);

        //Should be persisted
        User user = em.find(User.class, result.id());
        assertNotNull(user);
        assertEquals(user.getName(), result.name());
    }

    @Test
    @TestTransaction
    public void testModifyUserHashNewPassword() {
        UserDTO firstUserDTO = userService.addUser("Test", "test@test.ch", "password");

        userService.modifyUserById(
                firstUserDTO.id(),
                "Test",
                "test@test.ch",
                "newpassword"
        );

        User user = em.find(User.class, firstUserDTO.id());
        assertNotEquals("newpassword", user.getPassword());
        assertTrue(user.getPassword().startsWith("$2"));
    }
}
