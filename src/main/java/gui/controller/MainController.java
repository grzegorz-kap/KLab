package gui.controller;

import common.EventService;
import gui.events.ProfilingModeSwitchEvent;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckMenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MainController {
    private EventService eventService;

    public void profilingAction(ActionEvent event) {
        CheckMenuItem s = (CheckMenuItem) event.getSource();
        eventService.publish(new ProfilingModeSwitchEvent(s.isSelected(), this));
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}
