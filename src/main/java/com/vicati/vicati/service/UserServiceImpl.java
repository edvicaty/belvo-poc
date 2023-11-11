package com.vicati.vicati.service;

import com.vicati.vicati.model.User;
import com.vicati.vicati.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) throws Exception {
        User existingUser = findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new Exception("User already exists");
        }
        return userRepository.save(user);
    }
}
