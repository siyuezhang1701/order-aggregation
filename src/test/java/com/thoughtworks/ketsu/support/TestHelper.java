package com.thoughtworks.ketsu.support;

import com.thoughtworks.ketsu.domain.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestHelper {
    public static Map<String, Object> userMap(String email, String password) {
        return new HashMap<String, Object>() {{
            put("email", email);
            put("password", password);
        }};
    }

    public static Map<String, Object> productMap(String name) {
        return new HashMap<String, Object>() {{
            put("name", name);
            put("price", 10);
        }};
    }

    public static Map<String, Object> productPriceMap(double price) {
        return new HashMap<String, Object>(){{
            put("price", price);
        }};
    }

    public static Map<String, Object> orderMap(User owner){
        return new HashMap<String, Object>(){{
            put("items", new ArrayList<Map<String, Object>>(){{
                add(new HashMap<String, Object>(){{
                    put("products", "1");
                    put("quantity", 2);
                }});
            }});
        }};
    }

    public static Map<String, Object> refundRequest(){
        return new HashMap<String, Object>(){{
            put("items", new ArrayList<Map<String, Object>>(){{
                add(new HashMap<String, Object>(){{
                    put("products", "1");
                    put("quantity", 2);
                }});
            }});
        }};
    }
}
