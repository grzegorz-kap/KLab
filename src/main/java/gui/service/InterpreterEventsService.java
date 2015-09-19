package gui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.stereotype.Service;

@Service
public class InterpreterEventsService {

    @Autowired
    private ApplicationEventMulticaster applicationEventMulticaster;

    public void register(ApplicationListener<?> applicationListener) {
        applicationEventMulticaster.addApplicationListener(applicationListener);
    }

    public void unregister(ApplicationListener<?> applicationListener) {
        applicationEventMulticaster.removeApplicationListener(applicationListener);
    }
}
