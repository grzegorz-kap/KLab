package interpreter.types.converters;

import interpreter.types.NumericType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ConvertsHolderImpl implements ConvertersHolder {
    private Converter[] converters = new Converter[NumericType.values().length];

    @Override
    public Converter<?> getConverter(NumericType sourceType, NumericType destType) {
        return converters[destType.getIndex()];
    }

    @Autowired
    public void setConverters(Set<Converter> converters) {
        converters.forEach(this::registerConverter);
    }

    private void registerConverter(Converter converter) {
        converters[converter.supportTo().getIndex()] = converter;
    }
}
