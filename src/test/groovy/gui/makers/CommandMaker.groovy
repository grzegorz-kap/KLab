package gui.makers

import com.natpryce.makeiteasy.Instantiator
import com.natpryce.makeiteasy.Property
import com.natpryce.makeiteasy.PropertyLookup
import gui.model.Command

import java.time.LocalDateTime

import static com.natpryce.makeiteasy.Property.newProperty

class CommandMaker {

    static final Property<Command, String> content = newProperty()
    static final Property<Command, LocalDateTime> createdAt = newProperty()

    static final saveCommand = new Instantiator<Command>() {

        @Override
        Command instantiate(PropertyLookup<Command> propertyLookup) {
            Command command = new Command();
            command.content = propertyLookup.valueOf(content, "1+3")
            command.createdAt = propertyLookup.valueOf(createdAt, LocalDateTime.now())
            return command;
        }
    }
}
