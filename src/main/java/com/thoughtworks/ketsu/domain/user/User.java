package com.thoughtworks.ketsu.domain.user;

import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import java.util.HashMap;
import java.util.Map;

public class User implements Record {
    public long getId() {
        return id;
    }

    private long id;
    private String email;

    public User(long id, String email) {
        this.id = id;
        this.email = email;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return toJson(routes);
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return new HashMap<String, Object>(){{
            put("uri", routes.userUrl(User.this));
            put("id", id);
            put("email", email);
        }};
    }
}
