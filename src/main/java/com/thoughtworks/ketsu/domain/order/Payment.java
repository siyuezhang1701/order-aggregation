package com.thoughtworks.ketsu.domain.order;

import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Payment implements Record{
    double totalPrice;
    Date date;
    Order order;

    public Payment(Order order) {
        this.order = order;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return toJson(routes);
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return new HashMap<String, Object>(){{
            put("uri", routes.paymentUrl(order));
            put("total",totalPrice);
            put("time", date);
        }};
    }
}
