package common;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class EventBusSubscriberProcessor implements BeanPostProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventBusSubscriberProcessor.class);
    private EventBus eventBus;
    private AsyncEventBus asyncEventBus;

    @Override
    public Object postProcessBeforeInitialization(Object beanObject, String beanName) throws BeansException {
        return beanObject;
    }

    @Override
    public Object postProcessAfterInitialization(Object beanObject, String beanName) throws BeansException {
        Method[] methods = beanObject.getClass().getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(Subscribe.class)) {
                    eventBus.register(beanObject);
                    asyncEventBus.register(beanObject);
                    LOGGER.info("Bean '{}' method '{}' has been subscribed to EventBus.", beanName, method);
                    return beanObject;
                }
            }
        }
        return beanObject;
    }

    @Required
    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Required
    public void setAsyncEventBus(AsyncEventBus asyncEventBus) {
        this.asyncEventBus = asyncEventBus;
    }
}
