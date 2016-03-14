package com.klab.interpreter.execution.handlers;

import com.klab.interpreter.commons.MemorySpace;
import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.translate.model.InstructionCode;
import com.klab.interpreter.types.*;
import com.klab.interpreter.types.converters.ConvertersHolder;
import com.klab.interpreter.types.scalar.Scalar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Modify2InstructionHandler extends AbstractInstructionHandler {
    private MemorySpace memorySpace;
    private ConvertersHolder convertersHolder;

    @Override
    public void handle(InstructionPointer instructionPointer) {
        // TODO type check
        IdentifierObject target = (IdentifierObject) executionContext.executionStackPop();
        AddressIterator col = ((Addressable) executionContext.executionStackPop()).getAddressIterator();
        AddressIterator row = ((Addressable) executionContext.executionStackPop()).getAddressIterator();
        NumericObject source = (NumericObject) executionContext.executionStackPop();
        NumericObject dest = (NumericObject) memorySpace.get(target.getAddress());

        NumericType numericType = dest.getNumericType();
        if (dest instanceof Scalar && (row.max() > 1 || col.max() > 1)) {
            numericType = convertersHolder.scalarToMatrix(numericType);
        }

        // TODO add scalar version
        numericType = convertersHolder.promote(numericType, source.getNumericType());
        dest = convertersHolder.getConverter(dest.getNumericType(), numericType).convert(dest);
        source = convertersHolder.getConverter(source.getNumericType(), numericType).convert(source);

        Editable editable = (Editable) dest;
        EditSupportable supplier = (EditSupportable) source;
        editable.edit(row, col, supplier.getEditSupplier());

        memorySpace.set(target.getAddress(), dest);

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
}
