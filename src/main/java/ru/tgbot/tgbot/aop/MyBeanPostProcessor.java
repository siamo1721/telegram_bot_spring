package ru.tgbot.tgbot.aop;

import io.micrometer.common.lang.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Nullable
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.info("Creating bean: {}", bean);
        return bean;
    }

    @Nullable
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("After creating: {}", bean);
        return bean;
    }

}
