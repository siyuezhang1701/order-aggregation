package com.thoughtworks.ketsu.util;

import java.util.Optional;

public interface Uniqueness<Entity> {
    Optional<Entity> findBy(String key);
}
