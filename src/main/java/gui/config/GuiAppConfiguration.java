package gui.config;

import config.ApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"gui"})
@Import({ApplicationConfiguration.class})
public class GuiAppConfiguration {
}
