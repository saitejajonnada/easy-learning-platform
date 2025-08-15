package com.easylearning.service;

import com.easylearning.model.User;
import com.easylearning.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    public User createUser(User user) {
        return userDao.save(user);
    }

    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return userDao.findByEmail(email) != null;
    }

    public User findById(Long id) {
        return userDao.findById(id);
    }
}
