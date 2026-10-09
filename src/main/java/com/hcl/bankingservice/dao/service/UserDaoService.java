package com.hcl.bankingservice.dao.service;

import com.hcl.bankingservice.model.User;
import com.hcl.bankingservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDaoService {

    private final UserRepository userRepository;

    @Autowired
    public UserDaoService(final UserRepository userRepository) {
        this.userRepository =  userRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> getOneById(long id) {
        return userRepository.findById((int) id);
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public boolean delete(long id) {
        userRepository.deleteById((int) id);
        return true;
    }
}




