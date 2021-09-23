package com.example.crudsecurityboot.service;

import com.example.crudsecurityboot.dao.UserDao;
import com.example.crudsecurityboot.repository.UserRepository;
import com.example.crudsecurityboot.model.Role;
import com.example.crudsecurityboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public void DIUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    private UserRepository userRepository;

    @Autowired
    public void DIUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void DIPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

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
    public void updateUserAndRoles(User user, String[] roleList) {
        Set<Role> update = new HashSet<>();
        for (int i = 0; i < roleList.length; i++) {
            if (!user.getRoles().contains(roleList[i])) {
                update.add(getByRole(roleList[i]));
            }
            user.setRoles(update);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = getByName(name);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.
                User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

    @Override
    @Transactional(readOnly = true)
    public User getByName(String name) {
        return userDao.getByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Role getByRole(String name) {
        return userDao.getByRole(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return userDao.getAllRoles();
    }

}
