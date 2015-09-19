package gui.config;

import config.ApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

@Configuration
@ComponentScan({"gui"})
@Import({ApplicationConfiguration.class})
public class GuiAppConfiguration {

    @Bean
    public SimpleApplicationEventMulticaster applicationEventMulticaster() {
        return new SimpleApplicationEventMulticaster();
    }
}
