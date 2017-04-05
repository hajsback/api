package com.pawmot.hajsback.api.model.users;

import com.pawmot.hajsback.api.config.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SecurityConfig.class)
public class UserTests {
    @Autowired
    ApplicationContext ctx;

    @Test
    public void userShouldAcknowledgeMatchingPassword() {
        User user = getWiredUser();

        user.setPassword("Hello123");
        assertTrue(user.checkPassword("Hello123"));
    }

    @Test
    public void userShouldRefuseWrongPassword() {
        User user = getWiredUser();

        user.setPassword("Hello123");
        assertFalse(user.checkPassword("What?"));
    }

    private User getWiredUser() {
        User user = new User("pm@pm.pl");
        ctx.getAutowireCapableBeanFactory().autowireBean(user);
        return user;
    }
}
