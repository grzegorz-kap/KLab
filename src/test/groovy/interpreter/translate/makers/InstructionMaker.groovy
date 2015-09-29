package interpreter.translate.makers

import com.natpryce.makeiteasy.Instantiator
import com.natpryce.makeiteasy.Property
import com.natpryce.makeiteasy.PropertyLookup
import interpreter.commons.ObjectData
import interpreter.translate.model.instruction.Instruction
import interpreter.translate.model.instruction.InstructionCode

import static com.natpryce.makeiteasy.Property.newProperty

class InstructionMaker {

    static final Property<Instruction, InstructionCode> code = newProperty()
    static final Property<Instruction, Integer> arguments = newProperty()
    static final Property<Instruction, List<ObjectData>> data = newProperty()

    static final saveInstruction = new Instantiator<Instruction>() {

        @Override
        Instruction instantiate(PropertyLookup<Instruction> propertyLookup) {
            Instruction instruction = new Instruction();
            instruction.instructionCode = propertyLookup.valueOf(code, InstructionCode.PUSH)
            instruction.argumentsNumber = propertyLookup.valueOf(arguments, 2)
            instruction.objectDataList = propertyLookup.valueOf(data, [])
            return instruction
        }
    }

}
