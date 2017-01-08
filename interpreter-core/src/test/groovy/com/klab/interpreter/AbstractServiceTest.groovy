package com.klab.interpreter

import com.klab.config.ApplicationConfiguration
import org.kubek2k.springockito.annotations.SpringockitoAnnotatedContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = [ApplicationConfiguration.class], loader = SpringockitoAnnotatedContextLoader)
abstract public class AbstractServiceTest extends Specification {
}
