package com.vicati.vicati.service;

import com.vicati.vicati.model.User;

public interface UserService {
    User findByEmail(String email);

    User save(User user) throws Exception;

}
