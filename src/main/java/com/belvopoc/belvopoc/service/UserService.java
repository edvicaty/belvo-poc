package com.belvopoc.belvopoc.service;

import com.belvopoc.belvopoc.model.User;

public interface UserService {
    User findByEmail(String email);

    User save(User user);

}
