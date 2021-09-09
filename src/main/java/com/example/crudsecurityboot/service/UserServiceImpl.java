package com.example.crudsecurityboot.service;

import com.example.crudsecurityboot.repository.RoleRepository;
import com.example.crudsecurityboot.repository.UserRepository;
import com.example.crudsecurityboot.model.Role;
import com.example.crudsecurityboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    @Autowired
    public void DIUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private RoleRepository roleRepository;
    @Autowired
    public void DIRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    private ConversionService conversionService;
    @Autowired
    public void DIConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    private PasswordEncoder passwordEncoder;
    @Autowired
    public void DIPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserId(Long id) {
        User user = null;
        Optional<User> findUser = userRepository.findById(id);
        return user = findUser.get();
    }


    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getByName(String name) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.name=:name" ,User.class);
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

    @Override
    public void updateUserAndRoles(User user, String[] roleList) {
        Set<Role> update = new HashSet<>();
            for (int i =0; i<roleList.length; i++) {
                if (!user.getRoles().contains(roleList[i])){

                    update.add(getByRole(roleList[i]));
                }
                user.setRoles(update);
            }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        }

    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = getByName(name);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for(Role role: user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.
                User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

}
