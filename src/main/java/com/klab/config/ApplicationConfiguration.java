package com.klab.config;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.klab.common.EventBusSubscriberProcessor;
import com.klab.common.GuavaEventService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
@EnableScheduling
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"com.klab.interpreter"})
public class ApplicationConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    @Bean
    public EventBus eventBus() {
        return new EventBus();
    }

    @Bean
    public AsyncEventBus asyncEventBus() {
        return new AsyncEventBus(threadPoolTaskExecutor());
    }

    @Bean
    GuavaEventService guavaEventService() {
        GuavaEventService guavaEventService = new GuavaEventService();
        guavaEventService.setEventBus(eventBus());
        guavaEventService.setAsyncEventBus(asyncEventBus());
        return guavaEventService;
    }

    @Bean
    public EventBusSubscriberProcessor eventBusSubscriberProcessor() {
        EventBusSubscriberProcessor eventBusSubscriberProcessor = new EventBusSubscriberProcessor();
        eventBusSubscriberProcessor.setEventBus(eventBus());
        eventBusSubscriberProcessor.setAsyncEventBus(asyncEventBus());
        return eventBusSubscriberProcessor;
    }
}
