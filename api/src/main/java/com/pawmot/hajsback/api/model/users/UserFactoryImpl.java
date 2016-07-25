package com.pawmot.hajsback.api.model.users;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
class UserFactoryImpl implements UserFactory, ApplicationContextAware {
    private ApplicationContext ctx;

    @Override
    public User create() {
        User user = new User();
        ctx.getAutowireCapableBeanFactory().autowireBean(user);
        return user;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }
}
