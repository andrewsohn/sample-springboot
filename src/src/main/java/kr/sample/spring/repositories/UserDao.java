package kr.sample.spring.repositories;

import kr.sample.spring.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by andrew on 2017. 1. 5..
 */
@Repository
@Transactional
public class UserDao {
    /* Get All Users For Dashboard */
    public List<User> getAllUserList() {
        return entityManager.createQuery("from User").getResultList();
    }
    /* Save the user in the database */
    public User create(User user) {
        entityManager.persist(user);

        return (User) entityManager.createQuery(
                "from User where email = :email AND password = :pass AND userid = :userid")
                .setParameter("email", user.getEmail()).setParameter("pass", user.getPassword()).setParameter("userid", user.getUserid())
                .getSingleResult();
    }
    /* Delete the user from the database. */
    public void delete(User user) {
        if (entityManager.contains(user))
            entityManager.remove(user);
        else
            entityManager.remove(entityManager.merge(user));
        return;
    }

    /* Find Id */
    public User getById(int id) {
        return entityManager.find(User.class, id);
    }

    /* Find userId */
    public User getByUserId(String userid) {
        return (User) entityManager.createQuery(
                "from User where userid = :userid")
                .setParameter("userid", userid)
                .getSingleResult();
    }

    public User getControlUser(String username,String password) {
        return (User) entityManager.createQuery(
                "from User where name = :username AND password = :pass")
                .setParameter("username", username).setParameter("pass", password)
                .getSingleResult();
    }


    /**
     * Update the passed user in the database.
     */
    public void update(User user) {
        entityManager.merge(user);
        return;
    }

    @PersistenceContext
    private EntityManager entityManager;
}
