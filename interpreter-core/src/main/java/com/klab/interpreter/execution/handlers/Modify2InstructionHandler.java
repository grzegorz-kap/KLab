package com.klab.interpreter.execution.handlers;

import com.klab.common.EventService;
import com.klab.interpreter.commons.memory.MemorySpace;
import com.klab.interpreter.core.events.PrintEvent;
import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.translate.model.InstructionCode;
import com.klab.interpreter.types.*;
import com.klab.interpreter.types.converters.ConvertersHolder;
import com.klab.interpreter.types.matrix.Matrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Modify2InstructionHandler extends AbstractInstructionHandler {
    private MemorySpace memorySpace;
    private ConvertersHolder convertersHolder;
    private EventService eventService;

    @Override
    public void handle(InstructionPointer instructionPointer) {
        // TODO type check
        IdentifierObject target = (IdentifierObject) executionContext.executionStackPop();
        AddressIterator col = ((Addressable) executionContext.executionStackPop()).getAddressIterator();
        AddressIterator row = ((Addressable) executionContext.executionStackPop()).getAddressIterator();
        NumericObject source = (NumericObject) executionContext.executionStackPop();
        NumericObject dest = (NumericObject) memorySpace.get(target.getAddress());
        NumericType numericType = dest.getNumericType();
        if (!(dest instanceof Matrix) && (row.max() > 1 || col.max() > 1)) {
            numericType = convertersHolder.scalarToMatrix(numericType);
        }
        // TODO add scalar version
        numericType = convertersHolder.promote(numericType, source.getNumericType());
        Editable<Number> editable = convertersHolder.convert(dest, numericType);
        EditSupportable<Number> supplier = convertersHolder.convert(source, numericType);
        editable.edit(row, col, supplier.getEditSupplier());
        editable.setTemp(true); // AVOID COPYING
        ObjectData objectData = memorySpace.set(target.getAddress(), editable, dest.getName());
        if (instructionPointer.currentInstruction().isPrint()) {
            eventService.publish(new PrintEvent(objectData, this));
        }
        instructionPointer.increment();
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.MODIFY2;
    }

    @Autowired
    public void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }

    @Autowired
    public void setConvertersHolder(ConvertersHolder convertersHolder) {
        this.convertersHolder = convertersHolder;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}
