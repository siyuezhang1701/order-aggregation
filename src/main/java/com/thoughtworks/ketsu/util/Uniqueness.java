package com.thoughtworks.ketsu.util;

import java.util.Optional;

public interface Uniqueness<Entity, KEY> {
    Optional<Entity> findBy(KEY key);
}
