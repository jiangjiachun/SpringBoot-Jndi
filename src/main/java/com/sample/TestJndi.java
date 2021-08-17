package com.sample;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TestJndi {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TestJndi(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        test();
    }
    
    public void test() {
        List<Map<String, Object>> users = jdbcTemplate.queryForList("select * from t_user");
        users.forEach(user -> {
            System.out.println(user.get("name") + " | " + user.get("create_datetime"));
        });
    }
}
