package com.pawmot.hajsback.common;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration()
@ComponentScan(basePackageClasses = {
    CommonsConfiguration.class
})
public class CommonsConfiguration {
}
