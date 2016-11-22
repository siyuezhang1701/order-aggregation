package com.thoughtworks.ketsu.domain.user;

import com.thoughtworks.ketsu.util.Uniqueness;

import java.util.Map;
import java.util.Optional;

public interface Users extends Uniqueness{

    Optional<User> findById(long id);

    User createUser(Map<String, Object> info);

    Optional<User> findBy(String email);
}
