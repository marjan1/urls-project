package com.eadvocate.util;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Conversion util class for converting Model(Entity) classes to
 * appropriate Dto classes and vice versa.
 */
@Component
@AllArgsConstructor
public class ConversionUtil {

    private ModelMapper modelMapper;

    /**
     * Convesrion of one object to another
     *
     * @param object     source object
     * @param targetType class of target object
     * @param <E>        type of source object
     * @param <D>        type of target object
     * @return instance of the target object populated with data from the source object
     */
    public <E, D> D convertObjectTo(E object, Class<D> targetType) {
        return targetType.cast(modelMapper.map(object, targetType));
    }

}
