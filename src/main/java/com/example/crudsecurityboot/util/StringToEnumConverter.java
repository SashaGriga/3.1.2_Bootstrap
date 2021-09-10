package com.example.crudsecurityboot.util;

import com.example.crudsecurityboot.model.Role;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class StringToEnumConverter implements Converter<String, Role> {
    @Override
    public Role convert(String name) {
        return null;
    }
}
