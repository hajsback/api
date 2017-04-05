package com.pawmot.hajsback.api.model.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {
    private final AutowireCapableBeanFactory beanFactory;

    @Autowired
    public UserFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public User create(String email) {
        User user = new User(email);
        beanFactory.autowireBean(user);
        return user;
    }
}
