package com.stream.cqrs.order.command.utils;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;


public class Utils {

    public static <T> T map(Object source, Class<T> destinationClass)
            throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        var obj = destinationClass.getDeclaredConstructor().newInstance();
        BeanUtils.copyProperties(source, obj);

        return obj;
    }
}
