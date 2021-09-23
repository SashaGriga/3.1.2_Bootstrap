package com.example.crudsecurityboot.dao;

import com.example.crudsecurityboot.model.Role;
import com.example.crudsecurityboot.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public User getByName(String name) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.name=:name", User.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findAny().orElse(null);
    }

    @Override
    public Role getByRole(String name) {
        TypedQuery<Role> query = entityManager.createQuery("select r from Role r where r.name=:name", Role.class);
        query.setParameter("name", name);
        return query.getResultList().get(0);
    }

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("select r from Role r", Role.class).getResultList();
    }
}
