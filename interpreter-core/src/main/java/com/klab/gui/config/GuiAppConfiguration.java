package com.klab.gui.config;

import com.klab.config.ApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"com.klab.gui"})
@Import({ApplicationConfiguration.class})
public class GuiAppConfiguration {
}
