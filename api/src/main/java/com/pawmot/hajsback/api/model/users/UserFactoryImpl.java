package com.pawmot.hajsback.api.model.users;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
class UserFactoryImpl implements UserFactory {
    private final AutowireCapableBeanFactory beanFactory;

    @Autowired
    public UserFactoryImpl(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public User create(String email) {
        User user = new User(email);
        beanFactory.autowireBean(user);
        return user;
    }
}
