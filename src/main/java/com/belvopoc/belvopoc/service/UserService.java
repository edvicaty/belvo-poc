package com.belvopoc.belvopoc.service;

import com.belvopoc.belvopoc.domain.User;

public interface UserService {
    User findByEmail(String email);

    User save(User user);

}
