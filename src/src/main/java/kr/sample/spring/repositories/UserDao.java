package kr.sample.spring.repositories;

import kr.sample.spring.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by andrew on 2017. 1. 5..
 */
@Repository
@Transactional
public class UserDao {
    @Autowired
    private SessionFactory _sessionFactory;

//    @Autowired
//    private PasswordEncoder _passwordEncoder;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void save(User user) {
        getSession().save(user);
        return;
    }

    public void delete(User user) {
        getSession().delete(user);
        return;
    }

    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        return getSession().createQuery("from User").list();
    }

    public User getByEmail(String email) {
        return (User) getSession().createQuery(
                "from User where email = :email")
                .setParameter("email", email)
                .uniqueResult();
    }

    public User getById(long id) {
        return (User) getSession().load(User.class, id);
    }

    public User getControlUser(String userId, String pass) {

//        _passwordEncoder = new BCryptPasswordEncoder();
//        String hashedPassword = _passwordEncoder.encode(pass);

        return (User) getSession().createQuery(
                "from User where userid = :userId and password = :password")
                .setParameter("userId", userId)
//                .setParameter("password", hashedPassword)
                .uniqueResult();
    }



    public void update(User user) {
        getSession().update(user);
        return;
    }
}
