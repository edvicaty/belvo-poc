package com.belvopoc.belvopoc.service;

import com.belvopoc.belvopoc.exception.BelvoException;
import com.belvopoc.belvopoc.model.User;
import com.belvopoc.belvopoc.repository.UserRepository;
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
    public User save(User user) {
        User existingUser = findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new BelvoException("User already exists");
        }
        return userRepository.save(user);
    }
}
