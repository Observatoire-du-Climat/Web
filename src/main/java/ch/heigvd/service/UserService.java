package ch.heigvd.service;

import ch.heigvd.entity.User;
import ch.heigvd.entity.UserParameters;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class UserService {

    private final EntityManager em;

    @Inject
    public UserService(EntityManager em) {
        this.em = em;
    }

    public record UserDTO(Long id, String name, String email) {}

    /**
     * Search a User by its id
     * @param userId the user id
     * @return the user with this id
     */
    public UserDTO searchUserById(Long userId) {
        var query = em.createQuery("""
            SELECT u.id, u.name, u.email
            FROM User as u
            WHERE u.id = :id
        """, UserDTO.class).setParameter("id", userId);

        return query.getSingleResult();
    }

    /**
     * Insert a new User
     * @param name the name of the User
     * @param email the email of the User
     * @param password the password, encrypted with Bcrypt, of the User
     * @return an Optional with the newly created User if the insert was successful, or an empty Optional if insertion failed
     */
    @Transactional
    public Optional<UserDTO> addUser(String name, String email, String password) {

        try {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setValid(false);
            user.setAdmin(false);
            user.setPassword(BcryptUtil.bcryptHash(password));
            user.setRole(""); //TODO to modify after admin features

            //Associate parameters with the user
            UserParameters userParameters = new UserParameters();
            userParameters.setUser(user);
            userParameters.setNotifications(true);

            em.persist(userParameters);
            em.persist(user);

            return Optional.of(new UserDTO(user.getId(), user.getName(), user.getEmail()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Update a User
     * All the attribute are updated, even if not all of them change
     * @param user the user to update
     * @param name the (new) name of the user
     * @param email the (new) email of the user
     * @param password the (new) password of the user, encrypted by Bcrypt
     * @return an Optional with the modified User if the update was successful, or an empty Optional if insertion failed
     */
    @Transactional
    public Optional<UserDTO> modifyUser(User user, String name, String email, String password) {

        try {
            User userToModify = em.find(User.class, user.getId());
            userToModify.setName(name);
            userToModify.setEmail(email);
            userToModify.setPassword(BcryptUtil.bcryptHash(password));
            return Optional.of(new UserDTO(userToModify.getId(), userToModify.getName(), userToModify.getEmail()));

        } catch (Exception e) {
            return Optional.empty();
        }
    }


}
