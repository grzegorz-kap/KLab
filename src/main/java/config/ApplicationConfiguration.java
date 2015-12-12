package config;

import com.google.common.eventbus.EventBus;
import common.EventBusSubscriberProcessor;
import common.GuavaEventService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"interpreter"})
public class ApplicationConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public EventBus eventBus() {
        return new EventBus();
    }

    @Bean
    GuavaEventService guavaEventService() {
        GuavaEventService guavaEventService = new GuavaEventService();
        guavaEventService.setEventBus(eventBus());
        return guavaEventService;
    }

    @Bean
    public EventBusSubscriberProcessor eventBusSubscriberProcessor() {
        EventBusSubscriberProcessor eventBusSubscriberProcessor = new EventBusSubscriberProcessor();
        eventBusSubscriberProcessor.setEventBus(eventBus());
        return eventBusSubscriberProcessor;
    }
}
