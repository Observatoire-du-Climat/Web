package ch.heigvd.service;

import ch.heigvd.dto.UserDTO;
import ch.heigvd.entity.User;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

/**
 * Service in charge of the User Class.
 * It contains the business logic associated with the User Entity.
 */
@ApplicationScoped
public class UserService {

    private final EntityManager em;

    @Inject
    public UserService(EntityManager em) {
        this.em = em;
    }

    /**
     * Search a User by its id
     * @param userId the user id
     * @return a DTO with this user displayable information
     * @throws NotFoundException if no user possess this id
     */
    public UserDTO searchUserById(Long userId) {

        User user = em.find(User.class, userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        var query = em.createQuery("""
            SELECT u.id, u.name, u.email, u.isValid
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
     * @return a DTO with the newly created User if the insert was successful
     */
    @Transactional
    public UserDTO addUser(String name, String email, String password) {

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setValid(false);
        user.setPassword(BcryptUtil.bcryptHash(password));
        user.setRole("user");

        em.persist(user);

        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getValid());
    }

    /**
     * Update a User
     * All the attribute are updated, even if not all of them are changed
     * @param userId the id of the user to update
     * @param name the (new) name of the user
     * @param email the (new) email of the user
     * @param password the (new) password of the user, encrypted with the Bcrypt algorithm
     * @return a DTO with the modified User if the update was successful
     * @throws NotFoundException if no user possess this id
     */
    @Transactional
    public UserDTO modifyUserById(Long userId, String name, String email, String password) {

        User userToModify = em.find(User.class, userId);
        if (userToModify == null) {
            throw new NotFoundException("User not found");
        }
        userToModify.setName(name);
        userToModify.setEmail(email);
        userToModify.setPassword(BcryptUtil.bcryptHash(password));
        return new UserDTO(userToModify.getId(), userToModify.getName(), userToModify.getEmail(), userToModify.getValid());

    }


    /**
     * Get a User from his email
     * This method is used for the LoginResource
     * @param userEmail the email of the User
     * @return the User
     */
    public User searchUserByEmail(String userEmail) {

        var query = em.createQuery("""
            SELECT u
            FROM User as u
            WHERE u.email = :email
        """, User.class).setParameter("email", userEmail);

        return query.getSingleResult();
    }

    /**
     * Get all existing User
     * @param search get all the users whose name contains this string
     * @return a list of all the user in a DTO.
     */
    public List<UserDTO> getAllUser(String search) {

        if (search == null || search.isBlank()) {
            return em.createQuery("""
            SELECT u.id, u.name, u.email, u.isValid
            FROM User as u
            ORDER BY u.id
        """, UserDTO.class).getResultList();
        }

        var query = em.createQuery("""
            SELECT u.id, u.name, u.email, u.isValid
            FROM User as u
            WHERE LOWER(u.name) LIKE LOWER(:search)
        """, UserDTO.class).setParameter("search", "%"+search+"%");

        return query.getResultList();
    }

    /**
     * Validate a user by changing his isValid field
     * @param userId the id of the user to validate
     * @return a DTO of the newly validated user
     * @throws NotFoundException if no user possess this id
     */
    @Transactional
    public UserDTO validateUserById(long userId) {
        User user = em.find(User.class, userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        user.setValid(true);
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getValid());
    }

}
