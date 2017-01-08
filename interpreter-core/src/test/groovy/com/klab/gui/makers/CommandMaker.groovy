package com.klab.gui.makers

import com.klab.gui.model.Command
import com.natpryce.makeiteasy.Instantiator
import com.natpryce.makeiteasy.Property
import com.natpryce.makeiteasy.PropertyLookup

import java.time.LocalDateTime

import static com.natpryce.makeiteasy.Property.newProperty

public class CommandMaker {
    static final Property<Command, String> content = newProperty()
    static final Property<Command, LocalDateTime> createdAt = newProperty()

    static final saveCommand = new Instantiator<Command>() {
        @Override
        Command instantiate(PropertyLookup<Command> propertyLookup) {
            Command command = new Command(propertyLookup.valueOf(content, "1+3"));
            command.createdAt = propertyLookup.valueOf(createdAt, LocalDateTime.now())
            return command;
        }
    }
}
