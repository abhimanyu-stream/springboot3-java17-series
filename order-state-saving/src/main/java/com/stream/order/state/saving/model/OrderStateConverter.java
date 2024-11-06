package com.stream.order.state.saving.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Converter
public class OrderStateConverter implements AttributeConverter<LinkedHashSet<String>, String> {

    // Separator for states in the database column
    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(LinkedHashSet<String> orderStates) {
        return String.join(SEPARATOR, orderStates);
    }

    @Override
    public LinkedHashSet<String> convertToEntityAttribute(String dbData) {
        return Arrays.stream(dbData.split(SEPARATOR))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}